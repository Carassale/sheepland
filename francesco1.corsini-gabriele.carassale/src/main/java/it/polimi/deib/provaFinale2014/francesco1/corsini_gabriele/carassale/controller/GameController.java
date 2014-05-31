package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Animal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Dice;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Wolf;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Crea un oggetto contente la lista dei giocatori, il gameTable, il dado e il
 * connectionManager. Gestisce l'intera partita
 *
 * @author Francesco Corsini
 */
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
        this.playerPool = null;
        this.gameTable = null;
        this.connectionManager = null;

        inizializeGame(numberOfPlayers);
        placeShepards();
    }

    /**
     * Controllore di gioco che serve a inizializzare e far giocare la partita
     * Costruttore reale
     *
     * @param connectionManager connessione per poter chiamare azioni del client
     */
    public GameController(ConnectionManager connectionManager) {
        dice = new Dice();
        this.playerPool = null;
        this.gameTable = null;

        this.connectionManager = connectionManager;
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
            connectionManager.refreshMoveAnimal(-2, gameTable.getWolf().getPosition().getID());
            tryEatSheep();
        }

        market();

        return isGameOver;
    }

    /**
     * Restituisce il player pool
     *
     * @return
     */
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

    /**
     * Restituisce il gameTable
     *
     * @return GameTable della partita
     */
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

    /**
     * Dichiara il vincitore
     */
    private void declareWinner() {
        int[] numSheepsForTerrain = new int[6];
        for (int i = 0; i < 6; i++) {
            numSheepsForTerrain[i] = 0;
        }

        //Conto le pecore per tipo di terreno e incremento il proprio contatore
        String kind;
        for (Sheep sheep : gameTable.getSheeps()) {
            if (!sheep.getPosition().isSheepsbourg()) {
                kind = sheep.getPosition().getTerrainType();
                numSheepsForTerrain[checkNumCard(kind)]++;
            }
        }
        //Aggiungo il conteggio per la pecora nera
        if (!gameTable.getBlacksheep().getPosition().isSheepsbourg()) {
            kind = gameTable.getBlacksheep().getPosition().getTerrainType();
            numSheepsForTerrain[checkNumCard(kind)] += 2;
        }

        //Aggiorno i punteggi di ogni giocatore
        int tempCard;
        int tempScore;
        ArrayList<Integer> tempArray = new ArrayList<Integer>();
        for (Player player : playerPool.getPlayers()) {
            tempScore = player.getCoins();

            tempCard = player.getTerrainCardsOwned(TypeCard.DESERT.toString()).size();
            tempScore += numSheepsForTerrain[checkNumCard(TypeCard.DESERT.toString())] * tempCard;

            tempCard = player.getTerrainCardsOwned(TypeCard.FIELD.toString()).size();
            tempScore += numSheepsForTerrain[checkNumCard(TypeCard.FIELD.toString())] * tempCard;

            tempCard = player.getTerrainCardsOwned(TypeCard.FOREST.toString()).size();
            tempScore += numSheepsForTerrain[checkNumCard(TypeCard.FOREST.toString())] * tempCard;

            tempCard = player.getTerrainCardsOwned(TypeCard.MOUNTAIN.toString()).size();
            tempScore += numSheepsForTerrain[checkNumCard(TypeCard.MOUNTAIN.toString())] * tempCard;

            tempCard = player.getTerrainCardsOwned(TypeCard.PLAIN.toString()).size();
            tempScore += numSheepsForTerrain[checkNumCard(TypeCard.PLAIN.toString())] * tempCard;

            tempCard = player.getTerrainCardsOwned(TypeCard.RIVER.toString()).size();
            tempScore += numSheepsForTerrain[checkNumCard(TypeCard.RIVER.toString())] * tempCard;

            player.setFinalScore(tempScore);
            tempArray.add(tempCard);
        }

        //Ordino la classifica
        boolean tempOrd;
        do {
            tempOrd = true;
            for (int i = 0; i < tempArray.size() - 1; i++) {
                if (tempArray.get(i) < tempArray.get(i + 1)) {
                    tempScore = tempArray.get(i);
                    tempArray.remove(i);
                    tempArray.add(i + 1, tempScore);

                    tempOrd = false;
                }
            }
        } while (tempOrd);

        //Attribuisco ad ogni giocatore la posizione in classfica
        for (Player player : playerPool.getPlayers()) {
            for (int i = 0; i < tempArray.size(); i++) {
                if (player.getFinalScore() == tempArray.get(i)) {
                    player.setFinalPosition(i + 1);
                }
            }
        }

        //Refresho il connection Manager
        connectionManager.refreshWinner();
    }

    private int checkNumCard(String kind) {
        if (kind.equals(TypeCard.DESERT.toString())) {
            return 0;
        } else if (kind.equals(TypeCard.FIELD.toString())) {
            return 1;
        } else if (kind.equals(TypeCard.FOREST.toString())) {
            return 2;
        } else if (kind.equals(TypeCard.MOUNTAIN.toString())) {
            return 3;
        } else if (kind.equals(TypeCard.PLAIN.toString())) {
            return 4;
        } else {
            return 5;
        }
    }

    /**
     * Invia al al connection manager gli animali da refreshare sul client
     */
    private void sendAnimalToClient() {
        String kind = "";
        for (Sheep sheep : gameTable.getSheeps()) {
            if (sheep.isLamb()) {
                kind = TypeAnimal.LAMB.toString();
            } else if (sheep.isRam()) {
                kind = TypeAnimal.RAM.toString();
            } else {
                kind = TypeAnimal.WHITE_SHEEP.toString();
            }

            connectionManager.refreshAddAnimal(sheep.getId(), sheep.getPosition().getID(), kind);
        }

        Animal wolf = gameTable.getWolf();
        connectionManager.refreshAddAnimal(-2, wolf.getPosition().getID(), TypeAnimal.WOLF.toString());

        Animal blackSheep = gameTable.getBlacksheep();
        connectionManager.refreshAddAnimal(-1, blackSheep.getPosition().getID(), TypeAnimal.BLACK_SHEEP.toString());
    }

    /**
     * Invia la connection manager i soldi da impostare ai vari giocatori
     */
    private void sendCoinToClient() {
        do {
            Player currentPlayer = playerPool.getFirstPlayer();

            connectionManager.refreshCoin(currentPlayer.getCoins(), true);
            connectionManager.nextPlayerConnections();
        } while (!(playerPool.nextPlayer()));
    }

    /**
     * SOLO PER TEST
     *
     * @param numPlayer
     */
    private void placeShepards() {
        int i = 0;
        do {
            Player currentPlayer = playerPool.getFirstPlayer();
            Shepard shep = new Shepard(gameTable.getMap().getRoads().get(i), currentPlayer, i);
            currentPlayer.getShepards().add(shep);
            gameTable.getShepards().add(shep);
            i++;
        } while (!(playerPool.nextPlayer()));
    }

    /**
     * Posiziona un pastore o più pastori, manda al connection manager che
     * gestisce la richiesta e restituisce la road da settare
     *
     * @param isGameTwoPlayers True se il gioco a composto da solo due giocatori
     */
    private void placeShepards(boolean isGameTwoPlayers) {
        int idShepard = 0;

        //onde evitare errore di compilazione perché sosteneva che nel do/while poteva non essere inizializzato
        Road roadChoosen = new Road(100);
        do {
            //questo fa che il ciclo for venga eseguito una sola volta
            int shepardsPerPlayer = 1;

            //in questo caso farà il ciclo for per 2 volte
            if (isGameTwoPlayers) {
                shepardsPerPlayer = 0;
            }

            for (; shepardsPerPlayer < 2; shepardsPerPlayer++) {
                boolean playerHasPlacedShepard = false;
                Player currentPlayer = playerPool.getFirstPlayer();

                do {
                    roadChoosen = connectionManager.getPlacedShepard(idShepard);
                    if (!roadChoosen.hasShepard()) {
                        playerHasPlacedShepard = true;
                    }
                } while (!playerHasPlacedShepard);

                Shepard shepard = new Shepard(roadChoosen, currentPlayer, idShepard);
                currentPlayer.getShepards().add(shepard);
                gameTable.getShepards().add(shepard);

                connectionManager.refreshAddShepard(idShepard, roadChoosen.getId());

                idShepard++;
            }

            connectionManager.nextPlayerConnections();
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
            boolean playerHasPicked = false;

            while (!playerHasPicked) {
                int random = (int) (Math.random() * 6);
                if (!alredyPicked[random]) {

                    try {
                        terrainKind = matchNumToTerrainKind(random);

                        playerThatPicks.buyTerrainCard(terrainKind, gameTable);
                        alredyPicked[random] = true;
                        playerHasPicked = true;
                    } catch (CoinException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (CardException ex) {
                        Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            if (connectionManager != null) {
                connectionManager.refreshCard(terrainKind, false);
                connectionManager.nextPlayerConnections();
            }
        } while (!(playerPool.nextPlayer()));

        clearUnlessTerrainCard();
    }

    /**
     * Rimuovere le carte terreno iniziali non utilizzate
     */
    public void clearUnlessTerrainCard() {
        if (gameTable.getTerrainCardPool(TypeCard.DESERT.toString()).size() == 6) {
            gameTable.getTerrainCardPool(TypeCard.DESERT.toString()).remove(0);
        }
        if (gameTable.getTerrainCardPool(TypeCard.FIELD.toString()).size() == 6) {
            gameTable.getTerrainCardPool(TypeCard.FIELD.toString()).remove(0);
        }
        if (gameTable.getTerrainCardPool(TypeCard.FOREST.toString()).size() == 6) {
            gameTable.getTerrainCardPool(TypeCard.FOREST.toString()).remove(0);
        }
        if (gameTable.getTerrainCardPool(TypeCard.MOUNTAIN.toString()).size() == 6) {
            gameTable.getTerrainCardPool(TypeCard.MOUNTAIN.toString()).remove(0);
        }
        if (gameTable.getTerrainCardPool(TypeCard.PLAIN.toString()).size() == 6) {
            gameTable.getTerrainCardPool(TypeCard.PLAIN.toString()).remove(0);
        }
        if (gameTable.getTerrainCardPool(TypeCard.RIVER.toString()).size() == 6) {
            gameTable.getTerrainCardPool(TypeCard.RIVER.toString()).remove(0);
        }
    }

    /**
     * Crea il player pool e setta il primo giocatore del round
     *
     * @param numberOfPlayers numero di giocatori da inserire nel gioco
     */
    private void createPlayerPool(int numberOfPlayers) {
        Player player;
        playerPool = new PlayerPool();
        int coin = 20;
        if (numberOfPlayers == 2) {
            coin = 30;
        }
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == 0) {
                player = new Player(true, i);
            } else {
                player = new Player(false, i);
            }
            player.setCoins(coin);
            playerPool.addPlayer(player);
        }
    }

    /**
     * Ricevuto un numero random restituisce il tipo della carta associata
     *
     * @param random Numero casuale da passare
     * @return String tipo di carta
     */
    private String matchNumToTerrainKind(int random) {
        String terrainKind = null;
        if (random == 0) {
            terrainKind = TypeCard.PLAIN.toString();
        } else if (random == 1) {
            terrainKind = TypeCard.FOREST.toString();
        } else if (random == 2) {
            terrainKind = TypeCard.RIVER.toString();
        } else if (random == 3) {
            terrainKind = TypeCard.DESERT.toString();
        } else if (random == 4) {
            terrainKind = TypeCard.MOUNTAIN.toString();
        } else if (random == 5) {
            terrainKind = TypeCard.FIELD.toString();
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

    /**
     * Prova a far mangiare al lupo una pecora
     *
     * @return True se ha mangiato
     */
    public boolean tryEatSheep() {
        Wolf wolf = gameTable.getWolf();
        Animal sheepDead = null;

        boolean repetWhile = false;
        do {
            sheepDead = wolf.isAbleToEat();

            // Se l'animale è la pecora nera ed è anche l'unico animale delterreno allora imposta a null l'animale ed esce dal while
            if (sheepDead == gameTable.getBlacksheep()
                    && wolf.getPosition().getAnimals().size() == 1) {
                repetWhile = false;
                sheepDead = null;
            }

            // Se l'animale è la pecora nera ma nn è l'unico animale del terreno allora ripete il while
            if (sheepDead == gameTable.getBlacksheep()
                    && wolf.getPosition().getAnimals().size() > 1) {
                repetWhile = true;
            }
        } while (repetWhile);

        if (sheepDead != null) {
            if (connectionManager != null) {
                Sheep s = (Sheep) sheepDead;
                connectionManager.refreshKillAnimal(s.getId());
            }
            wolf.getPosition().getAnimals().remove(sheepDead);
            return true;
        }
        return false;
    }

    /**
     * Metodo per gestire il market di fine round
     */
    private void market() {
        //TODO market
    }

    /**
     * Avvia la partita
     *
     * @param numberOfPlayers numero di giocatori
     */
    public void start(int numberOfPlayers) {
        dice = new Dice();
        inizializeGame(numberOfPlayers);

        if (connectionManager != null) {
            sendAnimalToClient();
            sendCoinToClient();
        }

        if (numberOfPlayers > 2) {
            placeShepards(false);
        } else {
            placeShepards(true);
        }
        playGame();
        declareWinner();
    }
}
