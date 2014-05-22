package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.BlackSheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Dice;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Turn {

    private boolean forceLastRound;
    private final GameTable game;
    private final Dice dice;
    private ConnectionManager connectionManager;

    /**
     * Costruttore solo per eseguire i Test(non ha il connectionManager)
     */
    public Turn(boolean isLastTurn, GameTable gameTable) {
        connectionManager = null;

        forceLastRound = isLastTurn;
        //TODO vedere come questo isLastTurn si immette per le Fence Finali

        dice = new Dice();
        game = gameTable;
    }

    /**
     * Costruttore
     *
     * @param isLastTurn true se è un turno dell'ultimo giro(verranno messe le
     * Fence finali)
     * @param gameTable gioco su cui si sta giocando
     * @param connectionManager dove sono tutte le connessioni
     * @param playerPool
     */
    public Turn(boolean isLastTurn, GameTable gameTable, ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        forceLastRound = isLastTurn;
        //TODO vedere come questo isLastTurn si immette per le Fence Finali

        dice = new Dice();
        game = gameTable;
    }

    public boolean playTurn() {
        moveBlackSheep();

        //questo controllo serve per poter utilizzare i test senza connessioni(nel caso di Test non esistono i Client connessi)
        if (connectionManager != null) {

            connectionManager.startAction();
        }

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
            Logger.getLogger(Turn.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }

}
