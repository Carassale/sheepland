package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.BlackSheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Dice;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Wolf;

public class Turn {

    private boolean forceLastRound;
    private final GameTable game;
    private final Dice dice;

    public Turn(boolean isLastTurn,GameTable gameTable) {
        forceLastRound = isLastTurn;
        //TODO vedere come questo isLastTurn si immette per le Fence Finali
        
        dice = new Dice();
        game = gameTable;
    }

    
    
    public boolean playTurn() {
        moveBlackSheep();
        
        
        
        
        market();
        moveWolf();
        
        //------
        if(game.getFenceNumber() <= 0)
            forceLastRound = true;
        return forceLastRound;
    }

    private void moveBlackSheep() {
        
        BlackSheep blackSheep = game.getBlacksheep();
        int diceNumber = dice.getRandom();
        
        try{
        Road road = blackSheep.hasToMove(diceNumber);
        blackSheep.move(road);
        }
        catch(NullPointerException e){
             //in questo caso non muove 
        }
        
    }

    private void moveWolf() {
        
        Wolf wolf = game.getWolf();
        int diceNumber = dice.getRandom();
        
        try{
            Road road = wolf.hasToMove(diceNumber);
            wolf.move(road);
        }
        catch(NullPointerException e){
            //in questo caso non muove
        }
        //TODO ora mangia le pecore
    }

    private void market() {
    }

}
