package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.Player;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server.MapServerPlayer;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionVariable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * È un iterfaccia, serve a definire i metodi guida da utilizzare nei due casi
 * Socket o RMI
 *
 * @author Carassale Gabriele
 */
public abstract class ConnectionManager implements Runnable {

    static final int NUMACTION = 3;
    List<PlayerConnection> playerConnections;
    PlayerConnection currentPlayer;
    GameController gameController;
    MapServerPlayer map;
    boolean isConnected;
    int shepardToPlace = 0;
    boolean isFinishGame = false;

    private String thisGame;
    private CheckThread checkThread;
    private final Object objectSyncronized = new Object();

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al method start
     *
     * @param playerConnection ArrayList contenente i player associati a questa
     * partita
     * @param map
     */
    public ConnectionManager(List<PlayerConnection> playerConnection, MapServerPlayer map) {
        this.map = map;
        this.playerConnections = playerConnection;

        thisGame = map.getTypeConnection(playerConnection.get(0).getNickname());
        thisGame += ", Game " + map.getIdGame(playerConnection.get(0).getNickname());

        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Memorizza il currentPlayer prelevandolo dal primo elemento dell'Array,
     * crea il GameController e successivamente lo avvia
     */
    public void startThread() {
        currentPlayer = playerConnections.get(0);
        gameController = new GameController(this);
        waitOkFromClient();
        sendsInitialMessage();
        try {
            gameController.start(playerConnections.size());
        } catch (FinishGame ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Ritorna l'arrey contenente i Player connessi alla partita tramite Socket
     *
     * @return ArrayList di PlayerConnectionSocket
     */
    public List<PlayerConnection> getPlayerConnections() {
        return playerConnections;
    }

    /**
     * Chiama il metodo isAllClientReady finchè non ritorna true, in seguito
     * crea un CheckThreadSocket e fa proseguire il processo del gioco
     */
    public void waitOkFromClient() {
        while (!isAllClientReady()) {
            try {
                System.out.println(thisGame + ": In attesa di tutti i giocatori...");
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        System.out.println(thisGame + ": Tutto pronto, il gioco ha inizio.");

        checkThread = new CheckThread();
    }

    public void sendsInitialMessage() {
        for (PlayerConnection currentPlayerConnection : playerConnections) {
            for (PlayerConnection playerConnection : playerConnections) {
                //Invia quello di tutti tranne il suo
                if (currentPlayerConnection.getIdPlayer() != playerConnection.getIdPlayer()) {
                    printMessage(currentPlayerConnection,
                            playerConnection.getNickname() + " si è connesso alla parita. Il suo id è: " + playerConnection.getIdPlayer());
                }
            }
        }
    }

    public void printMessage(PlayerConnection playerConnection, String message) {
    }

    /**
     * Inserito qui per creare un method guida, dice al connectionManager di
     * comunicare al Client che può eseguire le azioni e gestisce quindi le
     * chiamate Client-Server
     *
     * @throws FinishGame
     */
    public void startAction() throws FinishGame {

    }

    /**
     * Inserito qui per creare un method guida, dice al client di posizionareve
     * posizionare due pastori
     *
     * @param idShepard
     * @return Road nella quale è stato posizionato il pastore
     */
    public Road getPlacedShepard(int idShepard) {
        return null;
    }

    /**
     * Inserito qui per creare un method guida
     */
    public void nextPlayerConnections() {
        playerConnections.add(playerConnections.get(0));
        playerConnections.remove(0);

        currentPlayer = playerConnections.get(0);
        if (!gameController.getPlayerPool().isOnLinePlayer(currentPlayer.getIdPlayer())) {
            nextPlayerConnections();
        }
    }

    /**
     * Inserito qui per creare un method guida
     *
     * @param idShepard
     * @param idRoad
     */
    public void refreshAddShepard(int idShepard, int idRoad) {

    }

    /**
     * Inserito qui per creare un method guida
     *
     * @param idAnimal
     * @param idTerrain
     * @param kind
     */
    public void refreshAddAnimal(int idAnimal, int idTerrain, String kind) {

    }

    /**
     * Inserito qui per creare un method guida
     *
     * @param idAnimal
     * @param idTerrain
     */
    public void refreshMoveAnimal(int idAnimal, int idTerrain) {

    }

    /**
     * Inserito qui per creare un method guida
     *
     * @param idAnimal
     */
    public void refreshKillAnimal(int idAnimal) {

    }

    /**
     * Inserito qui per creare un method guida
     *
     * @param idAnimal
     * @param kindFinal
     */
    public void refreshTransformAnimal(int idAnimal, String kindFinal) {

    }

    /**
     * Invia al currentPlayer le monete da aggiungere o rimuovere
     *
     * @param coins Valore dei coin
     * @param addCoin True se vanno aggiunti
     */
    public void refreshCoin(int coins, boolean addCoin) {
        refreshCoin(currentPlayer, coins, addCoin);
    }

    public void refreshCoin(PlayerConnection player, int coins, boolean addCoin) {
    }

    /**
     * Invia al currentPlayer la carta comprata o venduta
     *
     * @param kind Tipo di carta
     * @param isSold True se è venduta
     */
    public void refreshCard(String kind, boolean isSold) {
        refreshCard(currentPlayer, kind, isSold);
    }

    public void refreshCard(PlayerConnection player, String kind, boolean isSold) {
    }

    /**
     * Refresh di tutto il game table nel caso un giocatore si sia ricollegato:
     * invia le carte e le monete possedute, la posizione di tutti i pastori,
     * degli animali e delle fance
     *
     * @param idPlayer
     */
    public void reconnectPlayer(int idPlayer) {
        PlayerConnection thisPlayer = null;
        Player thisGamePlayer = null;

        for (PlayerConnection playerConnection : playerConnections) {
            if (playerConnection.getIdPlayer() == idPlayer) {
                thisPlayer = playerConnection;
                break;
            }
        }

        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.getIdPlayer() == idPlayer) {
                thisGamePlayer = player;
                break;
            }
        }

        map.setOnLine(thisPlayer.getNickname(), true);
        thisGamePlayer.setOnLine(true);

        boolean isReady = false;
        while (!isReady) {
            isReady = isRadyClient(thisPlayer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        reconnectRefreshAddAnimal(thisPlayer);
        reconnectRefreshAddShepard(thisPlayer, idPlayer);
        refreshCoin(thisPlayer, thisGamePlayer.getCoins(), true);
        reconnectRefreshCard(thisPlayer, thisGamePlayer);
        refreshAllFence(thisPlayer);

        if (thisGamePlayer.getShepards().isEmpty()) {
            shepardToPlace = 1;
        }
        if (playerConnections.size() == 2 && thisGamePlayer.getShepards().isEmpty()) {
            shepardToPlace = 2;
        }

        for (PlayerConnection playerConnection : playerConnections) {
            if (playerConnection.getIdPlayer() != thisPlayer.getIdPlayer()) {
                printMessage(playerConnection, "Il player " + thisPlayer.getNickname() + " si è riconnesso.");
            }
        }
        printMessage(thisPlayer, Message.RECONNECTED.toString());

        synchronized (objectSyncronized) {
            objectSyncronized.notify();
        }
    }

    private void reconnectRefreshAddAnimal(PlayerConnection player) {
        String kind;
        for (Sheep sheep : gameController.getGameTable().getSheeps()) {
            if (sheep.isLamb()) {
                kind = TypeAnimal.LAMB.toString();
            } else if (sheep.isRam()) {
                kind = TypeAnimal.RAM.toString();
            } else {
                kind = TypeAnimal.WHITE_SHEEP.toString();
            }
            singleRefreshAddAnimal(player, sheep.getId(), sheep.getPosition().getID(), kind);
        }

        kind = TypeAnimal.WOLF.toString();
        singleRefreshAddAnimal(player, -2, gameController.getGameTable().getWolf().getPosition().getID(), kind);

        kind = TypeAnimal.BLACK_SHEEP.toString();
        singleRefreshAddAnimal(player, -1, gameController.getGameTable().getBlacksheep().getPosition().getID(), kind);
    }

    private void reconnectRefreshAddShepard(PlayerConnection player, int idPlayer) {
        boolean isMine;
        for (Shepard shepard : gameController.getGameTable().getShepards()) {
            isMine = shepard.getOwner().getIdPlayer() == idPlayer;
            singeRefreshAddShepard(player, shepard.getId(), shepard.getPosition().getId(), isMine);
        }
    }

    private void reconnectRefreshCard(PlayerConnection player, Player gamePlayer) {
        int i;
        for (i = 0; i < gamePlayer.getTerrainCardsOwned(TypeCard.DESERT.toString()).size(); i++) {
            refreshCard(player, TypeCard.DESERT.toString(), false);
        }
        for (i = 0; i < gamePlayer.getTerrainCardsOwned(TypeCard.FIELD.toString()).size(); i++) {
            refreshCard(player, TypeCard.FIELD.toString(), false);
        }
        for (i = 0; i < gamePlayer.getTerrainCardsOwned(TypeCard.FOREST.toString()).size(); i++) {
            refreshCard(player, TypeCard.FOREST.toString(), false);
        }
        for (i = 0; i < gamePlayer.getTerrainCardsOwned(TypeCard.MOUNTAIN.toString()).size(); i++) {
            refreshCard(player, TypeCard.MOUNTAIN.toString(), false);
        }
        for (i = 0; i < gamePlayer.getTerrainCardsOwned(TypeCard.PLAIN.toString()).size(); i++) {
            refreshCard(player, TypeCard.PLAIN.toString(), false);
        }
        for (i = 0; i < gamePlayer.getTerrainCardsOwned(TypeCard.RIVER.toString()).size(); i++) {
            refreshCard(player, TypeCard.RIVER.toString(), false);
        }
    }

    public void singleRefreshAddAnimal(PlayerConnection player, int idAnimal, int idTerrain, String kind) {
    }

    public void singeRefreshAddShepard(PlayerConnection player, int idShepard, int idRoad, boolean isMine) {
    }

    public void refreshAllFence(PlayerConnection player) {
    }

    /**
     * Inserito qui per creare un method guida
     */
    public void refreshWinner() {

    }

    public void printTurnOf() {
        for (PlayerConnection playerConnection : playerConnections) {
            if (playerConnection.getIdPlayer() != currentPlayer.getIdPlayer()) {
                printMessage(playerConnection, "È il turno di " + currentPlayer.getNickname());
            }
        }
    }

    /**
     * Viene chiamato nel caso il currentPlayer si sia disconnesso, prima di
     * effettuare la disconnessione fa partire un timer
     *
     * @return True se si è ricollegato in tempo
     */
    public boolean checkCurrentClientDisconnected() {
        map.setOnLine(currentPlayer.getNickname(), false);

        for (PlayerConnection playerConnection : playerConnections) {
            printMessage(playerConnection, "il player " + currentPlayer.getNickname() + " si è disconnesso, rimani in attesa...");
        }

        synchronized (objectSyncronized) {
            try {
                objectSyncronized.wait(ConnectionVariable.TIME_MAX);
            } catch (InterruptedException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }

        //Se alla fine del timer il player è acnora offline allora procedo alla disconnessione nei vari punti
        if (!map.isOnLine(currentPlayer.getNickname())) {
            clientDisconnected(currentPlayer);
            return false;
        }
        return true;
    }

    /**
     * Gestisce la disconnessione del client e setta lo stato nell'hash Map
     * ofline
     *
     * @param playerConnection
     */
    public void clientDisconnected(PlayerConnection playerConnection) {
        map.setOnLine(playerConnection.getNickname(), false);
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.getIdPlayer() == playerConnection.getIdPlayer()) {
                player.setOnLine(false);
            }
        }
        checkAllStatus();
    }

    /**
     * Controlla se tutti i player sono collegati effettuando la chiamata al
     * metodo isOnline del player, nel caso in cui siano tutti disconnessi
     * effettua la chiamata al metodo turnOffGame per terminare il gioco
     */
    public void checkAllStatus() {
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.isOnLine()) {
                //Ne trovo almeno uno online
                return;
            }
        }
        //else
        System.out.println(thisGame + ": Non ci sono più player collegati");

        turnOffGame();
    }

    /**
     * Contolla se la variabile is finish game è settata a true, in tal caso
     * solleva un'eccezione Finish Game
     *
     * @throws FinishGame
     */
    public void checkIsFinishGame() throws FinishGame {
        if (isFinishGame) {
            throw new FinishGame(thisGame + ": Partita finita");
        }
    }

    /**
     * Leva dall'Hash map il nickname di tutti i player di questa partita
     */
    public void cleanMap() {
        for (PlayerConnection playerConnection : playerConnections) {
            map.removePlayer(playerConnection.getNickname());
        }
    }

    /**
     * Pulische l'hash map e setta la variabile isFinishGame a true
     */
    public void turnOffGame() {
        cleanMap();
        checkThread.interrupt();
        System.out.println(thisGame + ": Fine parita!");
        isFinishGame = true;
    }

    /**
     * Controlla se tutti i client sono pronti effettuando una chiamata al
     * metodo isReadyClient
     *
     * @return
     */
    public boolean isAllClientReady() {
        for (PlayerConnection playerConnection : playerConnections) {
            if (!isRadyClient(playerConnection)) {
                return false;
            }
        }
        return true;
    }

    public boolean isRadyClient(PlayerConnection player) {
        return false;
    }

    /**
     * Nel caso in cui il player riconnesso non ha ancora piazzato il pastore,
     * viene chiamato questo metodo per permettere il posizionamento del pastore
     * sulla mappa
     *
     * @param player
     */
    public void placeShepard(PlayerConnection player) {
        Player playerGame = null;
        for (Player player1 : gameController.getPlayerPool().getPlayers()) {
            if (player1.getIdPlayer() == player.getIdPlayer()) {
                playerGame = player1;
                break;
            }
        }

        int idShepard = gameController.getGameTable().getShepards().get(gameController.getGameTable().getShepards().size() - 1).getId();
        while (shepardToPlace > 0) {
            idShepard++;

            //onde evitare errore di compilazione perché sosteneva che nel do/while poteva non essere inizializzato
            Road roadChoosen = new Road(100);
            boolean playerHasPlacedShepard;
            boolean skip;

            do {
                playerHasPlacedShepard = false;
                skip = false;

                roadChoosen = getPlacedShepard(idShepard);
                if (roadChoosen != null) {
                    if (!roadChoosen.hasShepard()) {
                        playerHasPlacedShepard = true;
                    }
                } else {
                    playerHasPlacedShepard = true;
                    skip = true;
                }
            } while (!playerHasPlacedShepard);

            if (!skip) {
                Shepard shepard = new Shepard(roadChoosen, playerGame, idShepard);
                playerGame.getShepards().add(shepard);
                gameController.getGameTable().getShepards().add(shepard);

                refreshAddShepard(idShepard, roadChoosen.getId());
            }

            shepardToPlace--;
        }
    }

    /**
     * Questa classe implementa un Runnable, le due classi che la estendono
     * hanno entrambe un attributo Thread creato passando come parametro This e
     * successivamente avviato con la chiamata start
     */
    @Override
    public void run() {
        startThread();
    }

    /**
     * È un thread parallelo che controlla ogni 5 secondi se qualche client si è
     * disconnesso
     */
    private class CheckThread extends Thread {

        /**
         * Crea l'oggetto, crea un thread passandoli this come parametro e fa la
         * start
         */
        public CheckThread() {
        }

        /**
         * Controlla finchè il gioco non è finito lo stato dei player
         */
        @Override
        public void run() {
            while (!isFinishGame) {
                checkStatus();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }

        /**
         * Chiama per ogni player il metodo isStillConnected per controllare se
         * sono ancora connessi
         */
        private void checkStatus() {
            for (PlayerConnection playerConnection : playerConnections) {
                if (!playerConnection.isStillConnected()) {
                    clientDisconnected(playerConnection);
                }
            }
        }

    }
}
