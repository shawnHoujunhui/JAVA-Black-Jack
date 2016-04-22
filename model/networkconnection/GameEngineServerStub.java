package model.networkconnection;

import java.awt.BorderLayout;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;
import java.util.*;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;



public class GameEngineServerStub extends JFrame {
	/**
	 * 
	 */

	int port = 8000;
	private static final long serialVersionUID = 1L;
	JTextArea jtaLog = new JTextArea();
	private GameEngine engine;
	
	
	
	@SuppressWarnings("resource")
	public GameEngineServerStub(){
		
		// Create a scroll pane to hold text area
		JScrollPane scrollPane = new JScrollPane(jtaLog);
		
		// Add the scroll pane to the frame
		add(scrollPane, BorderLayout.CENTER);
		
		setTitle("Black Jack Server");
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // It is necessary to show the frame here!
		
		try{
			//Create a server socket
			ServerSocket serverSocket = new ServerSocket(port);
			jtaLog.append(new Date() + "Server is running now \n");
			jtaLog.append(new Date() + "Server start at socket" + port + '\n');
			
			// Number a player
			int playerNo = 1;
			
			while(true){
				//Listen for a new connection request
				Socket socket = serverSocket.accept();
				
				// Display the player number
				jtaLog.append(new Date() + " player " + playerNo + " joined game " + '\n');
				
				//Find the client's host name, and IP address
				InetAddress inetAddress = socket.getInetAddress();
				
				jtaLog.append(" Player " + playerNo +  " - " + "Host name : " + inetAddress.getHostName() + '\n');
				
				jtaLog.append(" Player " + playerNo +  " - " + "IP Address is " + inetAddress.getHostAddress() + '\n');
				
				//create a new thread for the connection
				HandleSession task = new HandleSession(socket, engine);
				
				//start the new thread
				new Thread(task).start();
				
				//increment playerNo
				playerNo++;
			}
		}catch(IOException e){
			System.err.println(e);
		}
		
	}
// Inner class
// Define the thread class for handling new connection
class HandleSession implements Runnable, Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Socket socket;    // connected socket
		private GameEngine engine;
		private GameEngineCallback gameEngineCallback;
		
		Player player;
		int delay;
		String id;
		PlayingCard card;
		int bet;
		int port = 8000;
		ObjectInputStream inputFromClient;
		ObjectOutputStream outputToClient;
		
		
		public HandleSession(Socket socket, GameEngine engine){
			this.socket = socket;
			this.engine = engine;
		}
		
		/**Run a thread**/
		@Override
		public void run() {

			//create stream
			try {
				
				inputFromClient = new ObjectInputStream(socket.getInputStream());
				outputToClient = new ObjectOutputStream(socket.getOutputStream());
				
				while(true){
					// reading flag from client side
					String reading = inputFromClient.readUTF();
					
					switch(reading){
					case "dealPlayer":
						try{
							//Reading object
							player = (Player)inputFromClient.readObject();
						}catch(ClassNotFoundException e){
							System.err.println(e);
						}
						//Calling dealPlayer method from GameEngine
						engine.dealPlayer(player,delay);
						break;
					case "dealHouse":
						// Calling dealHouse method from GameEngine
						engine.dealHouse(delay);
						break;
					case "addPlayer":
						try{
							//Reading object
							player = (Player)inputFromClient.readObject();
						}catch(ClassNotFoundException e){
							System.err.println(e);
						}
						// Calling addPlayer method from GameEngine
						engine.addPlayer(player);
						break;
					case "getPlayer":
						try{
							//Reading object
							id = (String)inputFromClient.readObject();
							outputToClient.writeObject(engine.getPlayer(id));
							outputToClient.flush();
							outputToClient.reset();
						}catch(ClassNotFoundException e){
							System.err.println(e);
						}
						// Calling getPlayer method from GameEngine
						engine.getPlayer(id);
						break;
					case "removePlayer":
						try{
							
							player = (Player)inputFromClient.readObject();
						}catch(ClassNotFoundException e){
							System.err.println(e);
						}
						// Calling removePlayer method from GameEngine
						engine.removePlayer(player);
						break;
					case "Calculate":
						engine.calculateResult();
						break;
					case "GameEngineCallback":
						engine.addGameEngineCallback(gameEngineCallback);
						break;
					case "AllPlayer":
						//Reading object
						outputToClient.writeObject(engine.getAllPlayers());
						outputToClient.flush();
						outputToClient.reset();
						break;
					case "Bet":
						try{
							player = (Player)inputFromClient.readObject();
							bet = (int)inputFromClient.readObject();
						}catch(ClassNotFoundException e){
							System.err.println(e);
						}
						engine.placeBet(player, bet);
						break;
					case "ShufflCard":
						outputToClient.writeObject(engine.getAllPlayers());
						outputToClient.flush();
						outputToClient.reset();
						break;
					case "Create":
						port = (int)inputFromClient.readInt();
						engine.addGameEngineCallback(new ServerStubGameEngineCallback(port));
						break;
					default:
						System.out.println("Crash Now!!!");
						break;
									}
					outputToClient.writeObject(reading);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}finally{
				try{
					inputFromClient.close();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
			
		}
	}
}
	
	
	
	

