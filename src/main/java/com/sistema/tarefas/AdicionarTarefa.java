package com.sistema.tarefas;

import java.awt.*;
import java.awt.EventQueue;

import javax.swing.*;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class AdicionarTarefa {
	private static String titulo;
	private static String descri;
	private static String level;
	private String nivel = "1";
	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void adicionar(String titu, String lev, String descr) {
		titulo = titu;
		level = lev;
		descri = descr;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					AdicionarTarefa window = new AdicionarTarefa();
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
	public AdicionarTarefa() {
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setTitle("Note Warning - Edição de Tarefa");
		ImageIcon icon = new ImageIcon("imgs/Note_warning_icon_1.png");
    	frame.setIconImage(icon.getImage());
    	
		frame.setBounds(100, 100, 681, 680);
		frame.getContentPane().setLayout(null);
		
		JTextArea txtrSsdf = new JTextArea();
		txtrSsdf.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtrSsdf.setBounds(30, 247, 604, 330);
		frame.getContentPane().add(txtrSsdf);
		
		JScrollPane scrollPane = new JScrollPane(txtrSsdf);
		scrollPane.setBounds(30, 247, 604, 330);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		frame.getContentPane().add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("Descrição");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(41, 213, 100, 25);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(30, 51, 331, 35);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Titulo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(41, 24, 63, 17);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton_3_1 = new JButton("Nivel De Urgência");
        btnNewButton_3_1.setBackground(new Color(255, 255, 255));
        btnNewButton_3_1.setBounds(30, 139, 153, 34);
        frame.getContentPane().add(btnNewButton_3_1);
        
        JButton btnNewButton = new JButton("Salvar");
        btnNewButton.setBounds(492, 587, 142, 46);
        frame.getContentPane().add(btnNewButton);
		
        if (titulo != null) {					
			textField.setText(titulo);
			nivel = level;
			txtrSsdf.setText(descri);
		}
        
        
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int esco = JOptionPane.showConfirmDialog(
                    frame, 
                    "Se você fechar, perderá a anotação. Deseja continuar?", 
                    "Aviso", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE
                );

                if (esco == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });
		btnNewButton_3_1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	nivel = "1";
	            String[] niveis = {"1","2","3","4","5"};
	            
	            nivel = (String) JOptionPane.showInputDialog(
	                frame,
	                "Nivel de Urgência:",
	                "Escolha o nivel de Urgência da Tarefa",
	                JOptionPane.PLAIN_MESSAGE,
	                null,
	                niveis,
	                niveis[0]
	            );
	        }
	    });
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String conteudo = null;
				
				
				String title = textField.getText();				
				String descript = txtrSsdf.getText();
				
				try {
					conteudo = new String(Files.readAllBytes(Paths.get("data.json")));
				} catch (Exception e1) {
				    JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}

				JSONObject json = new JSONObject();
				
				json.put("Titulo", title);
				json.put("Level", nivel);
				json.put("Descricao",descript);
				
				JSONArray jsonArray = new JSONArray(conteudo);
				
				jsonArray.put(json);
				
				try (FileWriter file = new FileWriter("data.json",false)) {
					file.write(jsonArray.toString(4));
				} catch (Exception e1) {
				    JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
				
				Home.main(null);
				frame.dispose();
			}
			
		});
	}
	
}
