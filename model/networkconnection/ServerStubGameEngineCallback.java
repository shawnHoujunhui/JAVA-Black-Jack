package model.networkconnection;

import java.io.*;
import java.net.*;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

public class ServerStubGameEngineCallback implements GameEngineCallback{
	public int port;
	private Socket socket;
	private ObjectOutputStream toClient;

	
	public ServerStubGameEngineCallback(int port) {
		this.port = port;
		
		try{
			socket = new Socket("localHost", port);
			toClient = new ObjectOutputStream(socket.getOutputStream());
			toClient.reset();
			
		}catch(UnknownHostException e){
			System.err.println(e);
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		try{
			toClient.writeUTF("NextCard");
			toClient.writeObject(player);
			toClient.writeObject(card);
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		try{
			toClient.writeUTF("BustCard");
			toClient.writeObject(player);
			toClient.writeObject(card);
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		try{
			toClient.writeUTF("Result");
			toClient.writeObject(player);
			toClient.writeObject(result);
		}catch(IOException e){			
			System.err.println(e);
		}
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		try{
			toClient.writeUTF("NextHouseCard");
			toClient.writeObject(card);
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		try{
			toClient.writeUTF("HouseBustCard");
			toClient.writeObject(card);
		}catch(IOException e){
			System.err.println(e);
		}
		
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		try{
			toClient.writeUTF("HouseResult");
			toClient.writeObject(result);
		}catch(IOException e){
			System.err.println(e);
		}
		
	}
	
    

}
