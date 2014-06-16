package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.FinishGame;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.BlackSheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Dice;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe rappresenta il turno di un giocatore
 *
 * @author Carassale Gabriele
 */
public class Turn {

    private boolean forceLastRound;
    private final GameTable game;
    private final Dice dice;
    private final ConnectionManager connectionManager;

    /**
     * Costruttore solo per eseguire i Test(non ha il connectionManager)
     *
     * @param isLastTurn
     * @param gameTable
     */
    public Turn(boolean isLastTurn, GameTable gameTable) {
        connectionManager = null;

        forceLastRound = isLastTurn;

        dice = new Dice();
        game = gameTable;
    }

    /**
     * Costruttore
     *
     * @param isLastTurn true se è un turno dell'ultimo giro
     * @param gameTable gioco su cui si sta giocando
     * @param connectionManager dove sono tutte le connessioni
     */
    public Turn(boolean isLastTurn, GameTable gameTable, ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;

        forceLastRound = isLastTurn;

        dice = new Dice();
        game = gameTable;
    }

    /**
     * Avvia il turno
     *
     * @return True se i cancelli sono finiti e il round che ha avviato il turno
     * deve diventare l'ultimo round
     * @throws FinishGame
     */
    public boolean playTurn() throws FinishGame {
        moveBlackSheep();
        //questo controllo serve per poter utilizzare i test senza connessioni(nel caso di Test non esistono i Client connessi)
        if (connectionManager != null) {
            connectionManager.startAction();
        }

        //Alla fine delle azioni del player crescono gli animali
        growUpLambs();

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

            if (connectionManager != null) {
                connectionManager.refreshMoveAnimal(-1, blackSheep.getPosition().getID());
            }

            return true;
        } catch (WrongDiceNumberException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }

    }

    /**
     * Method che fa crescere tutti gli agnelli di 1 turno e se ne trova uno
     * abbastanza grande lo fa diventare pecora o montone
     */
    protected void growUpLambs() {
        //è protected per poterlo testare
        for (Sheep ele : game.getSheeps()) {
            //se è un lamb lo faccio crescere 
            if (ele.isLamb()) {
                ele.growUpOneTurn();
                //se non è più lamb allora è cresciuto
                if (!ele.isLamb()) {
                    String kind;
                    if (ele.isRam()) {
                        kind = TypeAnimal.RAM.toString();
                    } else {
                        kind = TypeAnimal.WHITE_SHEEP.toString();
                    }
                    connectionManager.refreshTransformAnimal(ele.getId(), kind);
                }
            }
        }
    }

}
