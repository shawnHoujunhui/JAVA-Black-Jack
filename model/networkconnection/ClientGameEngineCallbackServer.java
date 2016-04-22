package model.networkconnection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;


public class ClientGameEngineCallbackServer implements GameEngineCallback{
	private static Logger logger = Logger.getLogger("assignment2");

    
	private ServerSocket serverSocket;
	@SuppressWarnings("unused")
	private Socket socket;
	public Player player;
	public PlayingCard card;
	public GameEngine engine;
	public int result;
	
	
	public ClientGameEngineCallbackServer(ObjectOutputStream outputToServer) {

		running(outputToServer);
		
	}
	
	private void running(ObjectOutputStream outputToServer) {
		try{
			serverSocket = new ServerSocket(0);
			outputToServer.writeUTF("Create");
			outputToServer.writeInt(serverSocket.getLocalPort());
			Socket socket = serverSocket.accept();
			System.out.println("accepted");
			ObjectInputStream Client = new ObjectInputStream(socket.getInputStream());
			
			while(true){
				String reading2 = Client.readUTF();
				
				switch(reading2){
				
				case "NextCard":
					try{
						player = (Player)Client.readObject();
						card = (PlayingCard)Client.readObject();
						nextCard(player,card,engine);
					}catch(ClassNotFoundException e){
						System.err.println(e);
					}
					break;
					
				case "BustCard":
					try{
						player = (Player)Client.readObject();
						card = (PlayingCard)Client.readObject();
						bustCard(player,card,engine); 
					}catch(ClassNotFoundException e){
						System.err.println(e);
					}
					break;
					
				case "Result":
					try{
						player = (Player)Client.readObject();
						result = (int)Client.readObject();
						result(player,result,engine);
					}catch(ClassNotFoundException e){
						System.err.println(e);
					}
					break;
				
				case "NextHouseCard":
					try{
						card =(PlayingCard)Client.readObject();
						nextHouseCard(card,engine);
					}catch(ClassNotFoundException e){
						System.err.println(e);
					}
					break;
					
				case "houseBustCard":
					try{
						card = (PlayingCard)Client.readObject();
						houseBustCard(card,engine);
					}catch(ClassNotFoundException e){
						System.err.println(e);
					}
					break;
					
				case "houseResult":
					try{
						result = (int)Client.readObject();
						houseResult(result,engine);
					}catch(ClassNotFoundException e){
						System.err.println(e);
					}
					break;
				default:
					break;
				}
			}
			
			
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

    	@Override
	   public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		   logger.log(Level.INFO, "Player: " + player.getPlayerName() + ", card=" + card.toString());
	   }

	   @Override
	   public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		   logger.log(Level.INFO, "Player: " + player.getPlayerName() + ", card=" + card.toString() + " ... YOU BUSTED!");
	   }

	   @Override
	   public void result(Player player, int result, GameEngine engine) {
		   logger.log(Level.INFO, "Player: " + player.getPlayerName() + ", final result=" + player.getResult());
	   }

	   @Override
	   public void nextHouseCard(PlayingCard card, GameEngine engine) {	  
		   logger.log(Level.INFO, "Player: House, card=" + card.toString());
	   }

	   @Override
	   public void houseBustCard(PlayingCard card, GameEngine engine) {
		   logger.log(Level.INFO, "Player: House, card=" + card.toString() + " ... YOU BUSTED!");
	   }

	   @Override
	   public void houseResult(int result, GameEngine engine) { 
		   logger.log(Level.INFO, "Player: House, final result=" + ((GameEngineImpl)engine).getHouseResult());
	   }

}
