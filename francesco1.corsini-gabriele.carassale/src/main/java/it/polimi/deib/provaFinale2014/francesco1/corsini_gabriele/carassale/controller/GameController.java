package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;

public class GameController {

    private PlayerPool playerPool;
    private GameTable gameTable;
    private ConnectionManager connectionManager;

    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore per i test
     */
    public GameController() {
        this.connectionManager = null;
        inizializeGame(4);
        placeShepards();
        playGame();
        declareWinner();
    }
    
    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore reale
     * @param connectionManager connessione per poter chiamare azioni del client
     */
    public GameController(int numberOfPlayers, ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        inizializeGame(numberOfPlayers);
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
    private void inizializeGame(int numberOfPlayers) {
        gameTable = new GameTable();
        createPlayerPool(numberOfPlayers);
        distributeCard();
        
    }

    private void declareWinner() {
        //TODO declare
    }

    /**
     * Metodo per inizializzare gli shepard: viene chiamato al primo turno e tutti i giocatori posizioneranno gli shepard
     */
    private void placeShepards() {

        int i = 0;
        //TODO caso solo 2 giocatori
        do {
            Player currentPlayer = playerPool.getFirstPlayer();

            //TODO prende la posizione dello shepard piazzato dalla view e la immette qui sotto
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
                    //non possibile nella prima distribuzione
                }
            }
        } while (!(playerPool.nextPlayer()));

    }
    
    private void createPlayerPool(int numberOfPlayers){
        Player player;
        playerPool = new PlayerPool(); 
        for(int i = 0; i < numberOfPlayers; i++){
            if(i == 0)
                player = new Player(true);
            else
                player = new Player(false);
            playerPool.addPlayer(player);
        }
    }
}
