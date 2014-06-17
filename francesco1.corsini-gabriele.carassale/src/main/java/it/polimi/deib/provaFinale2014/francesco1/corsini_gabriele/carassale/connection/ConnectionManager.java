package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.Player;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepherd;
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
    int shepherdToPlace = 0;
    boolean isFinishGame = false;

    private String thisGame;
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
        refreshAddPlayer();
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
     * Chiama il method isAllClientReady finchè non ritorna true, in seguito
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
    }

    /**
     * Effettua per tutti i player connessi il refreshSingleAddPlayer
     */
    public void refreshAddPlayer() {
        for (PlayerConnection playerConnection1 : playerConnections) {
            for (PlayerConnection playerConnection2 : playerConnections) {
                refreshSingleAddPlayer(playerConnection1, playerConnection2.getNickname(), playerConnection2.getIdPlayer());
            }
        }
    }

    /**
     * Invia al client l'id e il nickname del player aggiunto
     *
     * @param player Player a cui inviare
     * @param nickname Nickname del player aggiunto
     * @param idPlayer Id del player aggiunto
     */
    public void refreshSingleAddPlayer(PlayerConnection player, String nickname, int idPlayer) {
    }

    /**
     * Invia al client l'id del player in attesa,
     *
     * @param player Player a cui inviare
     * @param idPlayer Id del player in attesa
     */
    public void refreshSingleWaitPlayer(PlayerConnection player, int idPlayer) {
    }

    /**
     * Invia al client l'id del player disconnessco
     *
     * @param player Player a cui inviare
     * @param idPlayer Id del player disconnesso/riconnesso
     * @param turnOff True se è disconnesso, false se riconnesso
     */
    public void refreshSingleTurnOffPlayer(PlayerConnection player, int idPlayer, boolean turnOff) {
    }

    /**
     * stampa un mesaggio a un player scelto
     *
     * @param playerConnection player a cui inviare il messaggio
     * @param message Messaggio da inviare
     */
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
     * @param idShepherd
     * @return Road nella quale è stato posizionato il pastore
     */
    public Road getPlacedShepherd(int idShepherd) {
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
     * @param idShepherd
     * @param idRoad
     */
    public void refreshAddShepherd(int idShepherd, int idRoad) {

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

    /**
     * Invia al player scelto le monete da aggiungere o rimuovere
     *
     * @param player
     * @param coins
     * @param addCoin
     */
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

    /**
     * Invia al player scelto il tipo di carta comprata/venduta
     *
     * @param player
     * @param kind
     * @param isSold
     */
    public void refreshCard(PlayerConnection player, String kind, boolean isSold) {
    }

    /**
     * Refresh di tutto il game table nel caso un giocatore si sia ricollegato:
     * invia le carte e le monete possedute, la posizione di tutti i pastori,
     * degli animali e delle fence
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

        for (PlayerConnection playerConnection : playerConnections) {
            refreshSingleAddPlayer(thisPlayer, playerConnection.getNickname(), playerConnection.getIdPlayer());
            refreshSingleTurnOffPlayer(playerConnection, thisPlayer.getIdPlayer(), false);
        }
        refreshSingleTurnPlayer(thisPlayer, currentPlayer.getIdPlayer());

        reconnectRefreshAddAnimal(thisPlayer);
        reconnectRefreshAddShepherd(thisPlayer, idPlayer);
        refreshCoin(thisPlayer, thisGamePlayer.getCoins(), true);
        reconnectRefreshCard(thisPlayer, thisGamePlayer);
        refreshAllFence(thisPlayer);

        if (thisGamePlayer.getShepherds().isEmpty()) {
            shepherdToPlace = 1;
        }
        if (playerConnections.size() == 2 && thisGamePlayer.getShepherds().isEmpty()) {
            shepherdToPlace = 2;
        }

        printMessage(thisPlayer, Message.RECONNECTED.toString());

        synchronized (objectSyncronized) {
            objectSyncronized.notify();
        }
    }

    /**
     * Invia tutti gli animali al player, viene usato nel caso della
     * riconnessione
     *
     * @param player Player a cui inviare
     */
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

    /**
     * Invia tutti i pastori al player, viene usato nel caso della riconnessione
     *
     * @param player Player a cui inviare
     * @param idPlayer idPlayer per controllare se isMine
     */
    private void reconnectRefreshAddShepherd(PlayerConnection player, int idPlayer) {
        boolean isMine;
        for (Shepherd shepherd : gameController.getGameTable().getShepherds()) {
            isMine = shepherd.getOwner().getIdPlayer() == idPlayer;
            singeRefreshAddShepherd(player, shepherd.getId(), shepherd.getPosition().getId(), isMine);
        }
    }

    /**
     * Invia tutte le carte possedute dal player, viene usato nel caso della
     * riconnessione
     *
     * @param player Player a cui inviare
     * @param gamePlayer
     */
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

    /**
     * Invia al player l'animale aggiunto
     *
     * @param player
     * @param idAnimal
     * @param idTerrain
     * @param kind
     */
    public void singleRefreshAddAnimal(PlayerConnection player, int idAnimal, int idTerrain, String kind) {
    }

    /**
     * Invia al player il pastore aggiunto
     *
     * @param player
     * @param idShepherd
     * @param idRoad
     * @param isMine
     */
    public void singeRefreshAddShepherd(PlayerConnection player, int idShepherd, int idRoad, boolean isMine) {
    }

    /**
     * Invia al player le fence aggiunte
     *
     * @param player
     */
    public void refreshAllFence(PlayerConnection player) {
    }

    /**
     * Inserito qui per creare un method guida
     */
    public void refreshWinner() {

    }

    /**
     * Per tutti i player effettua il refreshSingleTurnPlayer
     */
    public void refreshTurnPlayer() {
        for (PlayerConnection playerConnection : playerConnections) {
            refreshSingleTurnPlayer(playerConnection, currentPlayer.getIdPlayer());
        }
    }

    /**
     * Invia al player scelto l'id del player a cui tocca giocare
     *
     * @param player
     * @param idPlayer
     */
    public void refreshSingleTurnPlayer(PlayerConnection player, int idPlayer) {
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
            refreshSingleWaitPlayer(playerConnection, currentPlayer.getIdPlayer());
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
        for (PlayerConnection player : playerConnections) {
            refreshSingleTurnOffPlayer(player, playerConnection.getIdPlayer(), true);
        }
        checkAllStatus();
    }

    /**
     * Controlla se tutti i player sono collegati effettuando la chiamata al
     * method isOnline del player, nel caso in cui siano tutti disconnessi
     * effettua la chiamata al method turnOffGame per terminare il gioco
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
        System.out.println(thisGame + ": Fine parita!");
        isFinishGame = true;
    }

    /**
     * Controlla se tutti i client sono pronti effettuando una chiamata al
     * method isReadyClient
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

    /**
     * Controlla se il player passato come parametro è pronto a giocare, non
     * implementato qui ma nelle classi che la estendono
     *
     * @param player
     * @return
     */
    public boolean isRadyClient(PlayerConnection player) {
        return false;
    }

    /**
     * Nel caso in cui il player riconnesso non ha ancora piazzato il pastore,
     * viene chiamato questo method per permettere il posizionamento del pastore
     * sulla mappa
     *
     * @param player
     */
    public void placeShepherd(PlayerConnection player) {
        Player playerGame = null;
        for (Player player1 : gameController.getPlayerPool().getPlayers()) {
            if (player1.getIdPlayer() == player.getIdPlayer()) {
                playerGame = player1;
                break;
            }
        }

        int idShepherd = gameController.getGameTable().getShepherds().get(gameController.getGameTable().getShepherds().size() - 1).getId();
        while (shepherdToPlace > 0) {
            idShepherd++;

            //onde evitare errore di compilazione perché sosteneva che nel do/while poteva non essere inizializzato
            Road roadChoosen = new Road(100);
            boolean playerHasPlacedShepherd;
            boolean skip;

            do {
                playerHasPlacedShepherd = false;
                skip = false;

                roadChoosen = getPlacedShepherd(idShepherd);
                if (roadChoosen != null) {
                    if (!roadChoosen.hasShepherd()) {
                        playerHasPlacedShepherd = true;
                    }
                } else {
                    playerHasPlacedShepherd = true;
                    skip = true;
                }
            } while (!playerHasPlacedShepherd);

            if (!skip) {
                Shepherd shepherd = new Shepherd(roadChoosen, playerGame, idShepherd);
                playerGame.getShepherds().add(shepherd);
                gameController.getGameTable().getShepherds().add(shepherd);

                refreshAddShepherd(idShepherd, roadChoosen.getId());
            }

            shepherdToPlace--;
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

}
