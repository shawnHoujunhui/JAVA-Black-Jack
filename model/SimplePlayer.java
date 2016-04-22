package model;

import java.io.Serializable;

import model.interfaces.Player;

/**
 * Player implementation for SADI assignment 1, Semester 2, 2015
 * @author junhui.hou
 */
public class  SimplePlayer implements Player, Serializable{

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   private String id;
	   private String name;
	   private int points;
	   private int bet;
	   private int result;
	   
	   public SimplePlayer(String id, String name, int points) {
	      this.id = id;
	      this.name = name;
	      this.points = points;
	   }
	   
	   public SimplePlayer(String name, int points, int bet){
		   this.name = name;
		   this.points = points;
		   this.bet = bet;
	   }
	   

	   @Override
	   public String getPlayerName() {
	      return name;
	   }

	   @Override
	   public void setPlayerName(String name) {
	      this.name = name ;
	   }

	   @Override
	   public int getPoints() {
	      return points;
	   }

	   @Override
	   public void setPoints(int points) {
	      this.points = points;
	   }

	   @Override
	   public String getPlayerId() {
	      return id;
	   }

	   @Override
	   public boolean placeBet(int bet) {
	      if(bet > 0 && bet <= points){
	         this.bet = bet;
	         return true;
	      }

	      else
	         return false;
	   }

	   @Override
	   public int getBet() {
	      return bet;
	   }

	   @Override
	   public int getResult() {
	      return result;
	   }

	   @Override
	   public void setResult(int result) {
	      this.result = result;
	   }
	   
	   @Override
	   public String toString(){
		   return "Player: id=" + id + ", name=" + name +", points=" + points;
	   }
   
}
