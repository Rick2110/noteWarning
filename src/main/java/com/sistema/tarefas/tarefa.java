package com.sistema.tarefas;
import org.json.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class tarefa {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		List<Integer> lista = new ArrayList<>(List.of(1,2,3,4,5));
		do {
			System.out.println("1 - Adicionar tarefa\n"
					+"2 - Editar tarefa\n"
					+"3 - Excluir tarefa\n"
					+"4 - listar tarefas\n"
					+"5 - Sair");
			try {
				int respo = sc.nextInt();
			
				if (lista.contains(respo)) {
					switch(respo) {
					case 1:
						adicionar();
						break;
					case 2:
						editar();
						break;
					case 3:
						excluir();
						break;
					case 4:
						listar();
						break;
					case 5:
						break;
					}
				}
			} catch (Exception e) {
				System.out.println("Opção invalida!");
			}
		} while(true);
		
	}
	public static void adicionar() {
		String conteudo = null;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Titulo: ");
		String title = sc.nextLine();
		System.out.print("Level: ");
		String nivel = sc.nextLine();
		System.out.print("Descrição: ");
		String descript = sc.nextLine();
		
		try {
			conteudo = new String(Files.readAllBytes(Paths.get("data.json")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		
		json.put("Titulo", title);
		json.put("Level", nivel);
		json.put("Descricao",descript);
		
		JSONArray jsonArray = new JSONArray(conteudo);
		
		jsonArray.put(json);
		
		try (FileWriter file = new FileWriter("data.json",false)) {
			file.write(jsonArray.toString(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void editar() {
		Scanner sc = new Scanner(System.in);
		String conteudo = null;
		
		try {
			conteudo = new String (Files.readAllBytes(Paths.get("data.json")));
			JSONArray lista = new JSONArray(conteudo);
			if (lista.length() != 0) {				
				tarefas();
				System.out.println("Selecione qual você quer editar: ");
				int respo = sc.nextInt();
				
				System.out.print("Titulo: ");
				String a = sc.nextLine();
				String title = sc.nextLine();
				System.out.print("Level: ");
				String nivel = sc.nextLine();
				System.out.print("Descrição: ");
				String descript = sc.nextLine();
				
				JSONObject json = new JSONObject();
				
				json.put("Titulo", title);
				json.put("Level", nivel);
				json.put("Descricao",descript);
				
				lista.put(respo - 1,json);
				Files.write(Paths.get("data.json"), lista.toString(4).getBytes());
				
			} else {
				System.out.println("Você não tem tarefas salvas");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static void excluir() {
		String conteudo = null;
		Scanner sc = new Scanner(System.in);
		try {
			conteudo = new String (Files.readAllBytes(Paths.get("data.json")));
			JSONArray lista = new JSONArray(conteudo);
			if (lista.length() != 0) {				
				tarefas();
				System.out.println("Selecione qual voce quer excluir");
				int respo = sc.nextInt();
				lista.remove(respo - 1);
				Files.write(Paths.get("data.json"), lista.toString(4).getBytes());
			} else {
				System.out.println("Você não tem tarefas salvas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void listar() {
		String conteudo = null;
		Scanner sc = new Scanner(System.in);
		
		try {
			conteudo = new String (Files.readAllBytes(Paths.get("data.json")));
			JSONArray lista = new JSONArray(conteudo);
			if (lista.length() != 0) {
				tarefas();
			} else {
				System.out.println("Você não tem tarefas salvas");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void tarefas() {
		String conteudo = null;
		try {
			conteudo = new String(Files.readAllBytes(Paths.get("data.json")));
			JSONArray lista = new JSONArray(conteudo);
			for (int i = 0; i < lista.length(); i++) {
				JSONObject json = lista.getJSONObject(i);
				System.out.println(i + 1 + " - " + json.getString("Titulo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void menu() {
		
	}
}

