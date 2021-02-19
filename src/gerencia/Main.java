package gerencia;

import java.io.IOException;
import java.lang.Thread;
import java.net.MulticastSocket;
import redes.ChatRoom;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main extends Thread {

	static Scanner read = new Scanner(System.in);
    static List<String> chatRooms = new ArrayList<String>();
	static public int idRoom = 225, idUser = 0;
	static MulticastSocket mSocket = null;
	static boolean cont = true;
	public static String rooms = "";

	public static void main(String[] args) throws IOException {
	        		User user = new User(idUser);
	        		idUser++;
	                int opt;
	                // Pequeno menu que gerencia qual caminho o usuário vai tomar
	                do {
	                	System.out.println("O que você deseja?" );
	                    System.out.println("1 - Criar sala");
	                    System.out.println("2 - Listar salas");
	                    System.out.println("3 - Entrar em uma sala");
	                    System.out.println("4 - Sair do sistema");
	                    opt = read.nextInt();
	                	 switch(opt) {
	                     case 1:
	                    	 // É criado a sala e o sistema muda o id para que a próxima sala tenha um ip diferente
	                    	 ChatRoom.createRoom(idRoom + ".5.6.7");
	                    	 chatRooms.add(idRoom+ ".5.6.7");
	                         idRoom++; 
	                         break;
	                     case 2: 
	                    	 System.out.println(rooms);
	                         break;
	                     case 3:
	                    	 System.out.println("Qual o ip da sala que você quer entrar?");
	                         String room = read.next();
	  	                	 System.out.println("Digite -sair para sair e -listar para listar os membros da sala");
	                    	 ChatRoom.enterRoom(room, user.getName());
	                    	 break;
	                     case 4: 
	                    	 System.out.println("Obrigado por usar o nosso chat");
	 	                     System.exit(0);
		                     break;
	                     default: System.out.println("Opção inexistente");
	                        break;
	                    	 }
	                } while (opt != 4);       
	          }
	     
}
	
