package model.networkconnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.Socket;
import java.util.Collection;
import java.util.Deque;

import view.GuiClient;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.networkconnection.GameEngineClientStub;



public class GameEngineClientStub implements GameEngine {
	
	private ObjectInputStream inputFromServer;
	private ObjectOutputStream outputToServer;
	private GuiClient gui;
	ClientGameEngineCallbackServer clientCallback;
	String host = "Localhost";
	
	int port = 8000;
	Player player;
	Collection<Player> playerList;
	Deque<PlayingCard> playCard;
	
	public GameEngineClientStub(){
	// Create new GUICLINE for player 
		gui = new GuiClient();
		gui.setTitle("Black Jack Client");
		gui.setSize(500,600);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		// connect to the server
		connectToServer();
	}

	public void connectToServer(){
		try{
			
			@SuppressWarnings("resource")
			Socket socket = new Socket(host,port);
			
			//Create stream
			// output to server
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			
			// input from server
			inputFromServer = new ObjectInputStream(socket.getInputStream());
					
			clientCallback = new ClientGameEngineCallbackServer(outputToServer);
						
		}catch(IOException ex){
			System.err.println(ex);
		}
		
		//Control the game on a separate thread
		Thread thread = new Thread((Runnable) this);
		thread.start();
	}
	
	
	
	@Override
	public void dealPlayer(Player player, int delay) {
		// TODO Auto-generated method stub
		try{
			outputToServer.writeUTF("dealPlayer");
			outputToServer.writeObject(player);	
			outputToServer.flush();
		}catch(IOException e){
			System.err.println(e);
		}
	}

	@Override
	public void dealHouse(int delay) {
		// TODO Auto-generated method stub
		try{
			outputToServer.writeUTF("dealHouse");
			outputToServer.flush();
		}catch(IOException e){
			System.err.println(e);
		}
	}

	@Override
	public void addPlayer(Player player) {
		
		try{
			outputToServer.writeUTF("addPlayer");
			outputToServer.writeObject(player);
			outputToServer.flush();
			
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

	@Override
	public Player getPlayer(String id) {
		
		try{
			outputToServer.writeUTF("gerPlayer");
			outputToServer.writeObject(id);
			outputToServer.flush();
		}catch(Exception e){
			System.err.println(e);
		}
		return player;
	}

	@Override
	public boolean removePlayer(Player player) {
	    try{
	    	outputToServer.writeUTF("RemovePlayer");
	    	outputToServer.writeObject(player);
	    	outputToServer.flush();
	    }catch(IOException e){
	    	System.err.println(e);
	    }
		return true;
	}

	@Override
	public void calculateResult() {
		try{
			outputToServer.writeUTF("Calculate");
			outputToServer.flush();
		}catch(IOException e){
			System.err.println(e);
		}
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		try{
			outputToServer.writeUTF("GameEngineCallback");
			outputToServer.flush();
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Player> getAllPlayers() {	
		try{
			outputToServer.writeUTF("AllPlayer");
			outputToServer.flush();
			playerList = (Collection<Player>)inputFromServer.readObject();
		}catch(Exception e){
			System.err.println(e);
		}
		return playerList;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		try{
			outputToServer.writeUTF("Bet");
			outputToServer.writeObject(player);
			outputToServer.writeObject(bet);
			outputToServer.flush();
		}catch(IOException e){
			System.err.println(e);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		try{
			outputToServer.writeUTF("ShufflCard");	
			outputToServer.flush();
			 playCard = (Deque<PlayingCard>)inputFromServer.readObject();
		}catch(Exception e){
			System.out.println(e);
		}
		return playCard;
	}

	

}
