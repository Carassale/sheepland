package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Animal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.BlackSheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Dice;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Wolf;

public class Turn {

    private boolean forceLastRound;
    private final GameTable game;
    private final Dice dice;
    private ConnectionManager connectionManager;

    public Turn(boolean isLastTurn, GameTable gameTable) {
        this.connectionManager = null;
        forceLastRound = isLastTurn;
        //TODO vedere come questo isLastTurn si immette per le Fence Finali

        dice = new Dice();
        game = gameTable;
    }

    public Turn(boolean isLastTurn, GameTable gameTable, ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        forceLastRound = isLastTurn;
        //TODO vedere come questo isLastTurn si immette per le Fence Finali

        dice = new Dice();
        game = gameTable;
    }

    public boolean playTurn() {
        moveBlackSheep();

        connectionManager.startAction();

        market();
        moveWolf();

        //------
        if (game.getFenceNumber() <= 0) {
            forceLastRound = true;
        }
        return forceLastRound;
    }

    /**
     * gestisce il movimento pecora nera. E' protected e non private poichè lo
     * devo chiamare per far fare il test su di lui
     *
     * @return true se la BlackSheep è stata mossa
     */
    protected boolean moveBlackSheep() {

        BlackSheep blackSheep = game.getBlacksheep();
        int diceNumber = dice.getRandom();

        try {
            Road road = blackSheep.hasToMove(diceNumber);
            blackSheep.move(road);
            return true;
        } catch (WrongDiceNumberException e) {
            //nel caso qui devo comunicare il risultato uscito
            return false;
        }

    }

    protected boolean moveWolf() {

        Wolf wolf = game.getWolf();
        int diceNumber = dice.getRandom();
        boolean wolfHasEaten = false;

        try {
            Road road = wolf.hasToMove(diceNumber);
            wolf.move(road);
            Animal sheepDead = wolf.isAbleToEat();
            if (sheepDead != null) {
                wolf.getPosition().getAnimals().remove(sheepDead);
                wolfHasEaten = true;
            }
            return wolfHasEaten;
        } catch (WrongDiceNumberException e) {
            //nel caso qui devo comunicare il risultato uscito
            return wolfHasEaten;
        }
        //TODO ora mangia le pecore
    }

    private void market() {
    }

}
