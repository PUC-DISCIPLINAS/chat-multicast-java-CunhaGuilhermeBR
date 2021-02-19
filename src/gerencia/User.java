package gerencia;

import java.util.Scanner;

public class User {
	static Scanner read = new Scanner(System.in);
	private String name;
	private int id;

	
	public User(int id) {
		System.out.println("Seja bem vindo ao nosso chat, qual o seu nome?");
		this.name = read.nextLine();;
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
