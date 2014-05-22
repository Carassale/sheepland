package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Animal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Dice;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Wolf;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameController {

    private PlayerPool playerPool;
    private GameTable gameTable;
    private ConnectionManager connectionManager;
    private Dice dice;

    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore SOLO per TESTs
     *
     * @param numberOfPlayers
     */
    public GameController(int numberOfPlayers) {
        dice = new Dice();
        this.connectionManager = null;
        inizializeGame(numberOfPlayers);
        placeShepards(numberOfPlayers);
    }

    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore reale
     *
     * @param connectionManager connessione per poter chiamare azioni del client
     */
    public GameController(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        dice = new Dice();
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
        if (moveWolf()) {
            tryEatSheep();
        }
        market();

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

    /**
     * Metodo per effettuare test
     *
     * @param numTurns
     */
    public void playGame(int numTurns) {
        boolean isGameOver = false;
        int i = numTurns;
        while (!(isGameOver) && i != 0) {
            isGameOver = playRounds();
            i--;
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
     * SOLO PER TEST
     *
     * @param numPlayer
     */
    protected void placeShepards(int numPlayer) {

        int i = 0;
        do {
            Player currentPlayer = playerPool.getFirstPlayer();
            Shepard shep = new Shepard(gameTable.getMap().getRoads().get(i), currentPlayer, i);
            currentPlayer.getShepards().add(shep);
            gameTable.getShepards().add(shep);
            i++;
        } while (!(playerPool.nextPlayer()));
    }

    private void placeShepards(boolean isGameTwoPlayers) {
        int i = 0;
        int id;
        Road roadChoosen = new Road(100); //onde evitare errore di compilazione perché sosteneva che nel do/while poteva non essere inizializzato
        do {
            int shepardsPerPlayer = 1;//questo fa che il ciclo for venga eseguito una sola volta
            if (isGameTwoPlayers) {
                shepardsPerPlayer = 0;//in questo caso farà il ciclo for per 2 volte
            }
            for (; shepardsPerPlayer < 2; shepardsPerPlayer++) {
                boolean playerHasPlacedShepard = false;
                Player currentPlayer = playerPool.getFirstPlayer();

                do {
                    boolean hasToScroll = false;
                    if (shepardsPerPlayer == 1) {
                        hasToScroll = true;
                    }
                    roadChoosen = connectionManager.getPlacedShepard(hasToScroll);
                    if (!roadChoosen.hasShepard()) {
                        playerHasPlacedShepard = true;
                    }
                } while (!playerHasPlacedShepard);

                Shepard shep = new Shepard(roadChoosen, currentPlayer, i);

                currentPlayer.getShepards().add(shep);
                gameTable.getShepards().add(shep);
                i++;
            }

        } while (!(playerPool.nextPlayer()));
    }

    /**
     * Metodo che serve a fare la distribuzione iniziale delle carte ai vari
     * giocatori E' protected perchè così posso testarlo
     */
    protected void distributeCard() {
        String terrainKind = null;
        boolean[] alredyPicked = new boolean[6];
        for (int i = 0; i < 6; i++) {
            alredyPicked[i] = false;
        }

        do {
            Player playerThatPicks = playerPool.getFirstPlayer();
            boolean playerHasPicked = false;

            while (!playerHasPicked) {
                int random = (int) (Math.random() * 6);
                if (alredyPicked[random] == false) {

                    try {
                        terrainKind = matchNumToTerrainKind(random);
                        if (terrainKind != null) {
                            playerThatPicks.buyTerrainCard(terrainKind, gameTable);
                            alredyPicked[random] = true;
                            playerHasPicked = true;
                        } else {
                            throw new NullPointerException("errore distribuzione carte");
                        }
                    } catch (CoinException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        } while (!(playerPool.nextPlayer()));

    }

    private void createPlayerPool(int numberOfPlayers) {
        Player player;
        playerPool = new PlayerPool();
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == 0) {
                player = new Player(true);
            } else {
                player = new Player(false);
            }
            playerPool.addPlayer(player);
        }
    }

    private String matchNumToTerrainKind(int random) {
        String terrainKind = null;
        if (random == 0) {
            terrainKind = "Plain";
        } else if (random == 1) {
            terrainKind = "Forest";
        } else if (random == 2) {
            terrainKind = "River";
        } else if (random == 3) {
            terrainKind = "Desert";
        } else if (random == 4) {
            terrainKind = "Mountain";
        } else if (random == 5) {
            terrainKind = "Field";
        }
        return terrainKind;
    }

    /**
     * gestisce il movimento pecora nera. E' protected e non private poichè lo
     * devo chiamare per far fare il test su di lui
     *
     * @return true se il Wolf è stato mosso
     */
    protected boolean moveWolf() {
        Wolf wolf = gameTable.getWolf();
        int diceNumber = dice.getRandom();
        Road road;

        try {
            road = wolf.hasToMove(diceNumber);
            wolf.move(road);
            return true;
        } catch (WrongDiceNumberException ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean tryEatSheep() {
        Wolf wolf = gameTable.getWolf();
        Animal sheepDead = wolf.isAbleToEat();

        if (sheepDead != null) {
            wolf.getPosition().getAnimals().remove(sheepDead);
            return true;
        }
        return false;
    }

    private void market() {
        //TODO market
    }

    public void start(int numberOfPlayers) {
        inizializeGame(numberOfPlayers);
        if (numberOfPlayers != 2) {
            placeShepards(false);
        } else {
            placeShepards(true);
        }
        playGame();
        declareWinner();
    }
}
