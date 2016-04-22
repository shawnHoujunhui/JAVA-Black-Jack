package model.interfaces;

import java.util.Collection;
import java.util.Deque;

/**
 * Assignment interface for SADI providing main Card Game model functionality
 * 
 * @author Caspar Ryan
 * 
 */
public interface GameEngine
{
   public static final int BUST_LEVEL = 21;

   /**
    * Deal cards to the player
    * increments of delay are in milliseconds (ms)
    * 
    * 1. deal a card to the player
    * 2. call GameEngineCallback.nextCard(...)
    * 3. continue looping until the player busts (default value of GameEngine.BUST_TOTAL=21)
    * 4. call {@link GameEngineCallback#result(Player, int, GameEngine)} with final result for player (the pre bust total)
    * 5. update the player with final result so it can be retrieved later
    * 
    * @param player
    *            the current player who will have their result set
    *            at the end of the hand
    * @param delay
    *            the delay between cards being dealt
    * @see model.interfaces.GameEngineCallback
    * 
    */
   public abstract void dealPlayer(Player player, int delay);

   /**
    * Same as dealPlayer() but deals for the house and calls the house versions
    * of the callback methods on GameEngineCallback, no player parameter is
    * required
    * 
    * @param delay
    * 
    * @see GameEngine#dealPlayer(Player, int)
    */
   public abstract void dealHouse(int delay);

   /**
    * @param player
    *            to add to game
    */
   public abstract void addPlayer(Player player);
   
   /**
    * @param id
    *            id of player to retrieve (null if not found)
    */
   public abstract Player getPlayer(String id);

   /**
    * @param player
    *            to remove from game
    * @return true if the player existed
    */
   public abstract boolean removePlayer(Player player);

   /**
    * this method deals for the house then goes through all players and applies
    * win or loss to update betting points. GameEngineCallback should be called for house
    * to allow GUI/client updates
    * 
    * @see model.interfaces.GameEngineCallback
    */
   public abstract void calculateResult();

   /**
    * @param gameEngineCallback
    *            a client specific implementation of GameEngineCallback used to
    *            perform display updates etc.
    * 
    *            you will use a different implementation of the GameEngineCallback for GUI and console
    *            versions
    *            
    */
   public abstract void addGameEngineCallback(
         GameEngineCallback gameEngineCallback);

   /**
    * 
    * @return an unmodifiable collection of all Players
    * @see model.interfaces.Player
    */
   public abstract Collection<Player> getAllPlayers();

   /**
    * the implementation should forward the call to the player class to handle
    * (this is required for assignment 2 but should be implemented in
    * assignment 1)
    * 
    * @param bet
    *            the bet in points
    * @return true if the player had sufficient points and the bet was placed
    */
   public abstract boolean placeBet(Player player, int bet);

   /**
    * A debug method to return a deck of cards containing 52 unique cards in random/shuffled order
    * 
    * @return a collection of PlayingCard
    * @see model.interfaces.PlayingCard
    */
   public abstract Deque<PlayingCard> getShuffledDeck();
}