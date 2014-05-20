package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnection;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import java.util.ArrayList;

public class GameController {

    private PlayerPool playerPool;
    private GameTable gameTable;

    /**
     * Controllore di gioco che controlla che le mosse siano corrette e che le
     * esegue in parte //TODO ricordarsi il controllo sulle mosse giuste
     *
     */
    public GameController() {

        inizializeGame();
        placeShepards();
        playGame();
        declareWinner();
    }

    /**
     * Metodo che crea e chiama i vari Turn dei singoli giocatori nel singolo
     * Round.(se 4 giocatori ==> 4 Turns = 1 Round ) Il isGameOver serve sia
     * come costruttore del Turn(serve per sapere se utilizzare Fence Finali)
     * sia come ritorno al PlayGame che così smetterà di richiamare questo
     * metodo
     *
     * @return true se il gioco è finito
     */
    public boolean playRounds() {
        boolean isGameOver = false;

        do {
            Turn round = new Turn(isGameOver, gameTable);
            isGameOver = round.playTurn();
        } while (!(playerPool.nextPlayer()));

        return isGameOver;
    }

    public PlayerPool getPlayerPool() {
        return playerPool;
    }

    /**
     * Metodo che chiama dei Round fino a che il gioco non è finito
     */
    public void playGame() {
        boolean isGameOver = false;
        while (!(isGameOver)) {
            isGameOver = playRounds();
        }

    }

    public GameTable getGameTable() {
        return gameTable;
    }

    private void inizializeGame() {
        gameTable = new GameTable();
    }

    private void declareWinner() {
    }

    private void placeShepards() {

        int i = 0;
        //TODO aggiorna la view
        do {
            Player currentPlayer = playerPool.getFirstPlayer();

            //TODOprende la posizione dello shepard piazzato dalla view e la immette qui sotto
            Road roadChoosen = new Road(545);

            Shepard shep = new Shepard(roadChoosen, currentPlayer, i);
            currentPlayer.getShepards().add(shep);
            gameTable.getShepards().add(shep);
            i++;
        } while (!(playerPool.nextPlayer()));
    }

}
