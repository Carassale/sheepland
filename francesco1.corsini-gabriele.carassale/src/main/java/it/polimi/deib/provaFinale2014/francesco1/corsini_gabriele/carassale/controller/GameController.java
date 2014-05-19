package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnection;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import java.util.ArrayList;
import java.util.Iterator;

public class GameController {

    private PlayerPool playerPool;
    private GameTable gameTable;

    /**
     * Controllore di gioco che controlla che le mosse siano corrette e che le esegue in parte
     * //TODO ricordarsi il controllo sulle mosse giuste
     * //TODO posizionamento Shepards
     * @param playerConnections 
     */
    public GameController(ArrayList<? extends PlayerConnection> playerConnections) {
        
        inizializeGame();
        placeShepards();
        playGame();
        declareWinner();
    }


    public void playRound() {
    }

    public PlayerPool getPlayerPool() {
        return playerPool;
    }

    public void playGame() {
        
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    private void inizializeGame() {
        gameTable = new GameTable();
    }

    private void declareWinner() {
    }

    private void placeShepards(){
        
        //TODO aggiorna la view
        
        do{
            Player currentPlayer = playerPool.getFirstPlayer();
            
            //TODOprende la posizione dello shepard piazzato dalla view e la immette qui sotto
            Road roadChoosen = new Road(545);
            
            Shepard shep = new Shepard(roadChoosen,currentPlayer);
            currentPlayer.getShepards().add(shep);
            gameTable.getShepards().add(shep);
        }while(!(playerPool.nextPlayer()));
    }

}
