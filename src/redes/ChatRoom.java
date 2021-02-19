package redes;

import persistencia.FileSystem;
import java.net.*;
import java.util.Scanner;

import gerencia.Main;

import java.io.*;

public class ChatRoom {
	static Scanner read = new Scanner(System.in);
    static FileSystem fs = new FileSystem();
    
	public static void createRoom(String ip) throws IOException {
	
		MulticastSocket mSocket = null;
	
		try {
			// Esse IP foi reservado para o armazenamento dos dados do sistema
			String adress = "224.1.1.1";
			InetAddress groupIp = InetAddress.getByName(adress);
			mSocket = new MulticastSocket(6789);
			mSocket.joinGroup(groupIp);
			byte[] rooms = ip.getBytes();
			DatagramPacket creating = new DatagramPacket(rooms, rooms.length, groupIp, 6789);
			mSocket.send(creating);
		}catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
		
		System.out.println("Sala criada com o ip:" + ip);
	}
	
	public void listRooms() {
		try {
			MulticastSocket mSocket = null;
			// É feito a leitura dos dados que foram mandados para o IP que gerencia o sistema
			String adress = "224.1.1.1";
			InetAddress groupIp = InetAddress.getByName(adress);
			mSocket = new MulticastSocket(6789);
			mSocket.joinGroup(groupIp);
			byte[] buffer = new byte[1000];
			DatagramPacket roomsCreated = new DatagramPacket(buffer, buffer.length);
			mSocket.receive(roomsCreated);
			System.out.println("Salas:" + new String(roomsCreated.getData()).trim());
			
		}catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}
	
	
	public static void enterRoom(String ip, String user) {
		MulticastSocket mSocket = null;
		try {
			InetAddress groupIp = InetAddress.getByName(ip);
			// Gambiarra, o sistema salva o nome do user num arquivo externo para ser feito a leitura
		    fs.gravar(user, ip);
			
			do {
				// O usuário digita a mensagem e o sistema verifica se essa mensagem é algum dos comandos
				String message = read.nextLine();
				// Caso ele queira a lista dos membros na sala ele imprime o arquivo externo
				if(message.equals("-listar"))
					System.out.println(fs.ler(ip));
				// Caso ele queira sair o sistema deleta o arquivo de membros e sai da sala
				if(message.equals("-sair")) {
					fs.delete(ip);
					return;
				}
				// Caso não seja ele escreve 
				new Thread(() -> writeMessage(ip, user + "-> "+ message)).start(); 
				new Thread(() -> readMessage(ip)).start();
				
			} while (true);
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} 
	}
//	
//	private static void  listUsers(String user, boolean read) {
//		try {
//
//			MulticastSocket mSocket = null;
//			String adress = "224.1.1.2";
//            InetAddress groupIp = InetAddress.getByName(adress);
//			mSocket = new MulticastSocket(6789);
//			mSocket.joinGroup(groupIp);
//			String users = user + ",";
//			byte[] message = users.getBytes();
//			DatagramPacket membersOut = new DatagramPacket(message, message.length, groupIp, 6789);
//			mSocket.send(membersOut);
//			byte[] buffer = new byte[1000];
//			if(read) {
//				for (int i = 0; i < 1; i++) { // get messages from others in group
//					DatagramPacket membersIn = new DatagramPacket(buffer, buffer.length);
//					mSocket.receive(membersIn);
//					System.out.println("Membros:" + new String(membersIn.getData()).trim());
//				}
//			}	
//			mSocket.leaveGroup(groupIp);
//		}catch (SocketException e) {
//			System.out.println("Socket: " + e.getMessage());
//		} catch (IOException e) {
//			System.out.println("IO: " + e.getMessage());
//		}
//	
//	}

	private static void readMessage(String ip){
		try {
			MulticastSocket mSocket = null;
			InetAddress groupIp = InetAddress.getByName(ip);
			mSocket = new MulticastSocket(6789);
			mSocket.joinGroup(groupIp);
			byte[] buffer = new byte[1000];
			DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
		    mSocket.receive(messageIn);
            System.out.println(new String(messageIn.getData()).trim());
		}catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
	}

	private static void writeMessage( String ip, String mesa) {
		try {
			MulticastSocket mSocket = null;
			InetAddress groupIp = InetAddress.getByName(ip);
			mSocket = new MulticastSocket(6789);
			mSocket.joinGroup(groupIp);
			byte[] message = mesa.getBytes();
			DatagramPacket messageOut = new DatagramPacket(message, message.length, groupIp, 6789);
			mSocket.send(messageOut);
		}catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		}
		
	}

}
