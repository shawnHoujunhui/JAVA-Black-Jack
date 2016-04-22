package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
//import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;

/**
 * GameEngine implementation for SADI assignment 1, Semester 2, 2015
 * @author junhui.hou
 */
public class  GameEngineImpl implements GameEngine{

   private  List<PlayingCard> deck= new ArrayList<PlayingCard>(); 
   private  Deque<PlayingCard> shuffledCards = new LinkedList<PlayingCard>(); 
   private List<Player> playerList = new ArrayList<Player>(); 
   private PlayingCard card; 
   private int cardNo;
   
   private GameEngineCallback engineCallBack;
   private int houseResult;
   
   public GameEngineImpl(){
       shuffleCards();
   }
   public int getCardNo(){
       return cardNo;
   }
   public void shuffleCards(){
      deck.removeAll(deck);
      for(Suit s: Suit.values()){
          for(Value v: Value.values())
             deck.add(new PlayingCardImpl(s, v));
       }
       Collections.shuffle(deck);
       shuffledCards = new LinkedList<PlayingCard>(deck);
   }
   
   @Override
   public void dealPlayer(Player player, int delay) {
	  assert player != null;
	  assert player.getPoints() != 0;
	  assert player.getPoints() > player.getBet();
	  
      try {
    	    int result = 0;
            cardNo = 0;
    	    while(true){
    	  		card = shuffledCards.removeFirst();              
    	  		if(result + card.getScore() <= BUST_LEVEL){
                  engineCallBack.nextCard(player, card, this); // update UI
                  result += card.getScore();
    	  		}
    	  		else{
                      engineCallBack.bustCard(player, card, this);
                      player.setResult(result);
                      engineCallBack.result(player, result, this);
                      break;
    	  		}
                cardNo++;
    	  		Thread.sleep(delay);
    	    }
      	}catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void dealHouse(int delay) {
      try{
         houseResult = 0;
         cardNo = 0;
         while(true){
            card = shuffledCards.removeFirst();
            cardNo++;
            if(houseResult + card.getScore() <= BUST_LEVEL){
                engineCallBack.nextHouseCard(card, this); 
                houseResult += card.getScore();
            }else{
                engineCallBack.houseBustCard(card, this);             	
                engineCallBack.houseResult(houseResult, this);
                break;
            }
            Thread.sleep(delay);
         }
      }catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   @Override
   public void addPlayer(Player player) {
      if(player != null)
         playerList.add(player);
   }

   @Override
   public Player getPlayer(String id) {
	   for(Player p: playerList){
		   if(p.getPlayerId().equals(id))
			   return p;
	   }
	   return getPlayer(id);
   }

   @Override
   public boolean removePlayer(Player player) {
      if (playerList.contains(player)){
         playerList.remove(player);
         return true;
      }
      else{
         return false;
      }
   }

   @Override
   public void calculateResult() {
    dealHouse(600);
    for (Player p : playerList)
    {
    	 if(p.getResult() > houseResult){
    		 p.setPoints(p.getPoints() + p.getBet());
    	 }else if(p.getResult() < houseResult){
    		 p.setPoints(p.getPoints() - p.getBet());
    	 }
    }
   }

   @Override
   public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
      this.engineCallBack = gameEngineCallback;
   }

   @Override
   public Collection<Player> getAllPlayers() {
      return playerList;
   }

   @Override
   public boolean placeBet(Player player, int bet) {
	   return player.placeBet(bet);
   }

   @Override
   public Deque<PlayingCard> getShuffledDeck() {
      return shuffledCards;
   }
   
   public int getHouseResult(){
	   return houseResult;
   }
}
