package controller;

import java.io.IOException;

import model.networkconnection.GameEngineClientStub;


public class StartClient {
	public static void main(String[] args) throws IOException{
		new GameEngineClientStub();
		
	}
}
