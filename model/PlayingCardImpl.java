package model;

import model.interfaces.PlayingCard;

/**
 * PlayingCard implementation for SADI assignment 1, Semester 2, 2015
 * @author junhui.hou
 */
public class PlayingCardImpl implements PlayingCard
{
	   private Suit Suit;
	   private Value Value;
	   private int score;

	   public PlayingCardImpl(Suit suit, Value value)
	   {
	      this.Suit = suit;
	      this.Value = value;
	   }    
	   
	   @Override
	   public Suit getSuit()
	   {
	      return Suit;
	   }

	   @Override
	   public Value getValue()
	   {
	      return Value;
	   }

	   @Override
	   public int getScore()
	   {
		   switch(Value){
		      case Ace:
		         score = 1;
		         break;
		      case Two:
		         score = 2;
		         break;
		      case Three:
		         score = 3;
		         break;
		      case Four:
		         score = 4;
		         break;
		      case Five:
		         score = 5;
		         break;
		      case Six:
		         score = 6;
		         break;
		      case Seven:
		         score = 7;
		         break;
		      case Eight:
		         score = 8;
		         break;
		      case Nine:
		         score = 9;
		         break;
		      case Ten:
		      case King:
		      case Queen:
		      case Jack:
		         score = 10;
		         break;
		      default:
		         System.err.println("Error! No matched card");
		      }
	      return score;
	   }
	   
	   @Override
	   public String toString(){
		   return "Suit: " + Suit + ", Value: " + Value + ", Score=" + score;
	   }
           
           public String getImageName(){
               return Suit.name()+Value.name();
           }
}
