package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {

    private PlayerPool playerPool;
    private GameTable gameTable;
    private ConnectionManager connectionManager;

    /**
     * Controllore di gioco che controlla che le mosse siano corrette e che le
     * esegue in parte //TODO ricordarsi il controllo sulle mosse giuste
     *
     */
    public GameController() {
        this.connectionManager = null;
        inizializeGame();
        placeShepards();
        playGame();
        declareWinner();
    }

    public GameController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
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
            Turn round = new Turn(isGameOver, gameTable, connectionManager);
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

    /**
     * Metodo che serve a creare la Plancia di gioco con tutti i suoi
     * componenti(Mappa, Animali, Shepards, ecc...) e a distribuire le carte
     * iniziali
     */
    private void inizializeGame() {
        gameTable = new GameTable();
        distributeCard();
    }

    private void declareWinner() {
        //TODO declare
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

    /**
     * Metodo che serve a fare la distribuzione iniziale delle carte ai vari
     * giocatori
     */
    private void distributeCard() {
        String terrainKind = null;
        boolean[] alredyPicked = new boolean[6];
        for (int i = 0; i < 6; i++) {
            alredyPicked[i] = false;
        }

        do {
            Player playerThatPicks = playerPool.getFirstPlayer();
            int random = (int) (Math.random() * 6 + 1);
            if (random == 0) {
                terrainKind = "Plain";
                alredyPicked[0] = true;
            } else if (random == 1) {
                terrainKind = "Forest";
                alredyPicked[1] = true;
            } else if (random == 2) {
                terrainKind = "River";
                alredyPicked[2] = true;
            } else if (random == 3) {
                terrainKind = "Desert";
                alredyPicked[3] = true;
            } else if (random == 4) {
                terrainKind = "Mountain";
                alredyPicked[4] = true;
            } else if (random == 5) {
                terrainKind = "Field";
                alredyPicked[5] = true;
            }
            if (alredyPicked[random] == false) {
                try {
                    if (terrainKind != null) {
                        playerThatPicks.buyTerrainCard(terrainKind, gameTable);
                    } else {
                        throw new NullPointerException("errore distribuzione carte");
                    }
                } catch (CoinException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } while (!(playerPool.nextPlayer()));

    }

}
