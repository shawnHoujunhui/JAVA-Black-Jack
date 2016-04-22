package view;

import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;


import model.GameEngineCallbackImpl;
import model.GameEngineImpl;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;

/**
 * Simple console client for SADI assignment 1, Semester 2, 2015
 * @author Caspar Ryan
 */
public class Client
{
	private static Logger logger = Logger.getLogger("assignment1");

	public static void main(String args[])
	{
		final GameEngine gameEngine = new GameEngineImpl();

		// create two test players
		Player[] players = new Player[]{
				new SimplePlayer("1", "The Shark", 1000),
				new SimplePlayer("2", "The Loser", 500)}; 

		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());

		//Deque<PlayingCard> shuffledDeck = gameEngine.getShuffledDeck();
		//printCards(shuffledDeck);

		// main loop to add player place a bet and roll
		for (Player player : players)
		{
			player.placeBet(100);
			gameEngine.addPlayer(player);
			gameEngine.dealPlayer(player, 10);
		}

		// all players have rolled so now house rolls (GameEngineCallBack is
		// called)
		// and results are calculated
		gameEngine.calculateResult();

		// log final points balances so we can check correctness
		for (Player player : gameEngine.getAllPlayers())
			logger.log(Level.INFO, player.toString());
	}

	@SuppressWarnings("unused")
	private static void printCards(Deque<PlayingCard> deck)
	{
		for (PlayingCard card : deck)
			System.out.println(card.toString());
	}

}
