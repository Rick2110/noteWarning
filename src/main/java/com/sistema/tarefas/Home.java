package com.sistema.tarefas;

import java.awt.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.swing.JFrame;

import org.json.*;

import javax.swing.JButton;
import javax.swing.*;

public class Home {
	private JButton btnNewButton = null;
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Home window = new Home();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    /**
     * Create the application.
     */
    public Home() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	frame = new JFrame();
    	frame.setTitle("Note Warning - Home");
    	ImageIcon icon = new ImageIcon("imgs/Note_warning_icon_1.png");
    	frame.setIconImage(icon.getImage());
    	ImageIcon plus_icon = new ImageIcon("imgs/plus.png");
    	
    	
    	frame.setBounds(100, 100, 500, 600);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().setLayout(null);  

    	String conteudo = null;
    	try {
    	    conteudo = new String(Files.readAllBytes(Paths.get("data.json")));
    	    JSONArray lista = new JSONArray(conteudo);
    	    
    	    if (lista.length() == 0) {
    	    	
    	        btnNewButton = new JButton(plus_icon);
    	        btnNewButton.setBounds(186, 208, 100, 100);
    	        frame.getContentPane().add(btnNewButton);                
    	    } else {
    	    	JPanel mainPanel = new JPanel();
    	    	mainPanel.setLayout(null);  
    	    	
    	    	JScrollPane scrollPane = new JScrollPane(mainPanel);
    	    	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	    	scrollPane.setBounds(0, 0, 484, 450);
    	    	frame.getContentPane().add(scrollPane);
    	    	scrollPane.getVerticalScrollBar().setUnitIncrement(20);


    	        btnNewButton = new JButton(plus_icon);
    	        btnNewButton.setBounds(353, 470, 101, 72);  
    	        frame.getContentPane().add(btnNewButton);

    	        int panelYPosition = 10;  

    	        for (int i = 0; i < lista.length(); i++) {
    	            JSONObject item = lista.getJSONObject(i);
    	            final int index = i;
    	            JPanel panel = new JPanel();
    	            panel.setBounds(10, panelYPosition, 460, 170);
    	            panel.setBackground(new Color(160,160,160));
    	            panel.setLayout(null);

    	            JLabel lblTitulo = new JLabel("Título: " + item.getString("Titulo"));
    	            lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 16));
    	            lblTitulo.setBounds(10, 10, 400, 30);
    	            panel.add(lblTitulo);

    	            JLabel lblNivel = new JLabel("Level: " + item.getString("Level"));
    	            lblNivel.setFont(new Font("Tahoma", Font.PLAIN, 16));
    	            lblNivel.setBounds(10, 90, 400, 30);
    	            panel.add(lblNivel);
    	            
    	            String des = item.getString("Descricao");
    	            if (des.length() <= 40) {    	            	
    	            	JLabel lblDescricao = new JLabel("Descrição: " + des);
    	            	lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 16));
    	            	lblDescricao.setBounds(10, 50, 400, 30);
    	            	panel.add(lblDescricao);
    	            } else {
    	            	JLabel lblDescricao = new JLabel("Descrição: " + des.substring(0,40) + "...");
    	            	lblDescricao.setFont(new Font("Tahoma", Font.PLAIN, 16));
    	            	lblDescricao.setBounds(10, 50, 400, 30);
    	            	panel.add(lblDescricao);
    	            }
    	            JSONObject finalItem = item;
    	            panel.addMouseListener(new MouseAdapter() {
    	                @Override
    	                public void mouseClicked(MouseEvent e) {
    	                	if (SwingUtilities.isLeftMouseButton(e)) {    	                		
    	                		JOptionPane.showMessageDialog(null, 
    	                			"Título: " + finalItem.getString("Titulo") + "\n" +
    	                			"Level: " + finalItem.getString("Level") + "\n" +
    	                			"Descrição:\n " + finalItem.getString("Descricao")
    	                		);
    	                	}
    	                	if (SwingUtilities.isRightMouseButton(e)) {
    	                		String[] opcoes = {"Editar", "Excluir", "Cancelar"};

    	                        int escolha = JOptionPane.showOptionDialog(
    	                            null,
    	                            "Escolha uma opção:",
    	                            "Título da Janela",
    	                            JOptionPane.DEFAULT_OPTION,
    	                            JOptionPane.QUESTION_MESSAGE,
    	                            null,
    	                            opcoes,
    	                            opcoes[2]
    	                        );
    	                        if (escolha == 2) {
    	                        	return;
    	                        } else if (escolha == 1) {
    	                        	int confirmacao = JOptionPane.showConfirmDialog(
    	                                    null,
    	                                    "Tem certeza que deseja excluir?",
    	                                    "Confirmação",
    	                                    JOptionPane.YES_NO_OPTION
    	                                );

    	                                if (confirmacao == JOptionPane.YES_OPTION) {
    	                                	try {
    	                                        String conteudo = new String(Files.readAllBytes(Paths.get("data.json")));
    	                                        JSONArray lista = new JSONArray(conteudo);

    	                                        if (index >= 0 && index < lista.length()) {
    	                                            lista.remove(index);
    	                                            Files.write(Paths.get("data.json"), lista.toString(4).getBytes());

    	                                            panel.getParent().remove(panel);
    	                                            JOptionPane.showMessageDialog(null, "Item removido com sucesso!");
    	                                            frame.dispose();
    	                                            Home.main(null);
    	                                        } else {
    	                                            JOptionPane.showMessageDialog(null, "Erro: Índice inválido.");
    	                                        }
    	                                	} catch (Exception e1) {
    	                                	    JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    	                                	}

    	                                }
    	                        }
    	                        else if (escolha == 0) {
    	                        	AdicionarTarefa.adicionar(item.getString("Titulo"),item.getString("Level"), des);
    	                        	frame.dispose();
    	                        	try {
                                        String conteudo = new String(Files.readAllBytes(Paths.get("data.json")));
                                        JSONArray lista = new JSONArray(conteudo);

                                        if (index >= 0 && index < lista.length()) {
                                            lista.remove(index);
                                            Files.write(Paths.get("data.json"), lista.toString(4).getBytes());

                                            panel.getParent().remove(panel);
                                            
                                        } else {
                                            JOptionPane.showMessageDialog(null, "Erro: Índice inválido.");
                                        }
    	                        	} catch (Exception e1) {
    	                        	    JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    	                        	}

    	                        }
    	                	}
    	                }
    	            });
    	            
    	            mainPanel.add(panel);
    	            panelYPosition += 180;  
    	        }
    	        int maxHeight = 450;
    	        int preferredHeight = panelYPosition;
    	        mainPanel.setPreferredSize(new Dimension(460, preferredHeight));

    	        int scrollHeight = Math.min(preferredHeight, maxHeight);
    	        scrollPane.setBounds(0, 0, 484, scrollHeight);  

    	        mainPanel.revalidate();
    	        mainPanel.repaint();

    	        SwingUtilities.invokeLater(() -> {
    	        	JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
    	        	verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    	        });
    	    }
    	} catch (Exception e) {
    	    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    	}

    	btnNewButton.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				AdicionarTarefa.adicionar(null,null,null);
				frame.dispose();
			}
    	});
    }
}
