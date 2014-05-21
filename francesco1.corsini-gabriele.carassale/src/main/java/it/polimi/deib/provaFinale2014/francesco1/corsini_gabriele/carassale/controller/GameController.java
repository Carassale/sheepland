package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Animal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Dice;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Wolf;

public class GameController {

    private PlayerPool playerPool;
    private GameTable gameTable;
    private ConnectionManager connectionManager;
    private Dice dice;

    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore SOLO per TESTs
     */
    public GameController(int numberOfPlayers, int numTurns) {
        dice = new Dice();
        this.connectionManager = null;
        inizializeGame(numberOfPlayers);
        placeShepards();
        playGame(numTurns);
        declareWinner();
    }
    
    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore SOLO per TESTs
     */
    public GameController(int numberOfPlayers) {
        dice = new Dice();
        this.connectionManager = null;
        inizializeGame(numberOfPlayers);
        placeShepards(numberOfPlayers);
        declareWinner();
    }

    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore reale
     *
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
            moveWolf();
            market();
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
     * Metodo per inizializzare gli shepard: viene chiamato al primo turno e
     * tutti i giocatori posizioneranno gli shepard
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
     * SOLO PER TEST
     */
    protected void placeShepards(int numPlayer) {

        int i = 0;
        //TODO caso solo 2 giocatori
        do {
            Player currentPlayer = playerPool.getFirstPlayer();
            Shepard shep = new Shepard(gameTable.getMap().getRoads().get(i), currentPlayer, i);
            currentPlayer.getShepards().add(shep);
            gameTable.getShepards().add(shep);
            i++;
        } while (!(playerPool.nextPlayer()));
    }

    /**
     * Metodo che serve a fare la distribuzione iniziale delle carte ai vari
     * giocatori
     * E' protected perchè così posso testarlo
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
                        //non possibile nella prima distribuzione
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
     *gestisce il movimento pecora nera. E' protected e non private poichè lo
     *devo chiamare per far fare il test su di lui
     *
     * @return true se il Wolf è stato mosso
    */
    protected boolean moveWolf() {

        Wolf wolf = gameTable.getWolf();
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
        
    }
    
    private void market(){
        do {
            Player currentPlayer = playerPool.getFirstPlayer();
            
            
            
            
        } while (!(playerPool.nextPlayer()));
    }
}
