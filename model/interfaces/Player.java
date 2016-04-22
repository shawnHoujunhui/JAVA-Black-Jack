package model.interfaces;

/**
 * Assignment interface for SADI representing the player
 * 
 * @author Caspar Ryan
 * 
 */
public interface Player
{
   /**
    * @return human readable player name
    */
   public abstract String getPlayerName();

   /**
    * @param playerName
    *            human readable player name
    */
   public abstract void setPlayerName(String playerName);

   /**
    * 
    * @return number of points from setPoints()
    */
   public abstract int getPoints();

   /**
    * @param points
    *            for betting (updated with each win or loss)
    */
   public abstract void setPoints(int points);

   /**
    * @return the player ID which is generated by the implementing class
    */
   public abstract String getPlayerId();

   /**
    * 
    * @param bet
    *            the bet in points
    * @return true if the player had sufficient points and the bet was placed
    */
   public abstract boolean placeBet(int bet);

   /**
    * @return the bet as set with placeBet()
    */
   public abstract int getBet();

   /**
    * @return the result of the most recent hand
    * 
    * @see model.interfaces.GameEngineCallback
    */
   public abstract int getResult();

   /**
    * 
    * @param result
    *            the result of the most recent hand
    * @see model.interfaces.GameEngineCallback
    */
   public abstract void setResult(int result);

   /**
    * 
    * @return a human readable String that lists the values of this Player
    *         instance for debugging or console display
    */
   public abstract String toString();
}
