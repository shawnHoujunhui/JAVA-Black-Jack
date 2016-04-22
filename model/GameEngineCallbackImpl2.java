package model;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.GuiClient;

/**
 * GameEngineCallback implementation for SADI assignment 1, Semester 2, 2015
 * @author junhui.hou
 */
public class GameEngineCallbackImpl2 implements GameEngineCallback{

        private GuiClient gClient;
	private static Logger logger = Logger.getLogger("assignment1");

   public GameEngineCallbackImpl2(GuiClient g)
   {
       gClient = g;
   }
 
   @Override
   public void nextCard(Player player, PlayingCard card, GameEngine engine) {
        gClient.showCard(player,card,false);
	logger.log(Level.INFO, "Player: " + player.getPlayerName() + ", card=" + card.toString());
   }

   @Override
   public void bustCard(Player player, PlayingCard card, GameEngine engine) {
        gClient.showCard(player,card,true);
	logger.log(Level.INFO, "Player: " + player.getPlayerName() + ", card=" + card.toString() + " ... YOU BUSTED!");
   }

   @Override
   public void result(Player player, int result, GameEngine engine) {
        gClient.showResult(player, result);
	logger.log(Level.INFO, "Player: " + player.getPlayerName() + ", final result=" + player.getResult());
   }

   @Override
   public void nextHouseCard(PlayingCard card, GameEngine engine) {
       gClient.showCard(null,card,false);
       logger.log(Level.INFO, "Player: House, card=" + card.toString());
   }

   @Override
   public void houseBustCard(PlayingCard card, GameEngine engine) {
       gClient.showCard(null,card,true);
       logger.log(Level.INFO, "Player: House, card=" + card.toString() + " ... YOU BUSTED!");
   }

   @Override
   public void houseResult(int result, GameEngine engine) {
        gClient.showResult(null, result);
       logger.log(Level.INFO, "Player: House, final result=" + ((GameEngineImpl)engine).getHouseResult());
   }
}
