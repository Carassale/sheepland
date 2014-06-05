package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CardException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.Player;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.ShepardException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.WrongDiceNumberException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server.MapServerPlayer;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea il collegamento diretto tra il GameController e il Client
 * gestito da un ConnectionClient di tipo RMI
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerRMI extends UnicastRemoteObject implements ConnectionManager, ConnectionRMI, Runnable {

    private static final int NUMACTION = 3;
    private final ArrayList<PlayerConnectionRMI> playerConnections;
    private PlayerConnectionRMI currentPlayer;
    private GameController gameController;
    private MapServerPlayer map;
    private CheckThreadRMI checkThread;

    private Object objectSyncrinized = new Object();
    private boolean isConnected;
    private boolean canDoAction;
    private int actionDone;

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al method start
     *
     * @param playerConnections ArrayList contenente i player associati a questa
     * partita
     * @param map
     * @throws java.rmi.RemoteException
     */
    public ConnectionManagerRMI(ArrayList<PlayerConnectionRMI> playerConnections, MapServerPlayer map) throws RemoteException {
        this.map = map;
        this.playerConnections = playerConnections;

        changeReferenceToClient();

        Thread thread = new Thread(this);
        thread.start();
    }

    public ArrayList<PlayerConnectionRMI> getPlayerConnections() {
        return playerConnections;
    }

    /**
     * Per ogni client collegato a questo connectionManager invia il prorpio
     * skeleton
     */
    private void changeReferenceToClient() {
        for (PlayerConnectionRMI playerConnectionRMI : playerConnections) {
            try {
                playerConnectionRMI.getClientRMI().setConnectionRMI(this);
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

                clientDisconnected(playerConnectionRMI);
            }
        }
    }

    /**
     * Memorizza il currentPlayer prelevandolo dal primo elemento dell'Array,
     * crea il GameController e successivamente lo avvia
     */
    @Override
    public void startThread() {
        currentPlayer = playerConnections.get(0);
        gameController = new GameController(this);
        waitOkFromClient();
        gameController.start(playerConnections.size());
    }

    /**
     * Scorre la lista dei Player, sposta il primo in ultima posizione
     */
    @Override
    public void nextPlayerConnections() {
        playerConnections.add(playerConnections.get(0));
        playerConnections.remove(0);

        currentPlayer = playerConnections.get(0);
        if (!map.isOnLine(currentPlayer.getNickname())) {
            nextPlayerConnections();
        }
    }

    /**
     * Invia a tutti i client il pastore aggiunto
     *
     * @param idShepard Pastore aggiunto
     * @param idRoad Strada posizionamento
     */
    @Override
    public void refreshAddShepard(int idShepard, int idRoad) {
        Shepard s;
        boolean isMine;
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (map.isOnLine(playerConnection.getNickname())) {
                s = gameController.getGameTable().idToShepard(idShepard);
                isMine = playerConnection.getIdPlayer() == s.getOwner().getIdPlayer();
                singeRefreshAddShepard(playerConnection, idShepard, idRoad, isMine);
            }
        }
    }

    private void singeRefreshAddShepard(PlayerConnectionRMI playerConnection,
            int idShepard, int idRoad, boolean isMine) {
        try {
            playerConnection.getClientRMI().refreshAddShepard(idShepard, idRoad, isMine);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(playerConnection);
        }
    }

    /**
     * Invia a tutti i client il movimento del pastore
     *
     * @param idShepard Pastore spostato
     * @param idRoad Strada destinazione
     */
    public void refreshMoveShepard(int idShepard, int idRoad) {
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshMoveShepard(idShepard, idRoad);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

                    clientDisconnected(playerConnection);
                }
            }
        }
    }

    /**
     * Invia a tutti i client l'animale aggiunto
     *
     * @param idAnimal
     * @param idTerrain Terreno destinazione
     * @param kind Tipo di animale (blackSheep, whiteSheep, lamb, ram, wolf)
     */
    @Override
    public void refreshAddAnimal(int idAnimal, int idTerrain, String kind) {
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (map.isOnLine(playerConnection.getNickname())) {
                singleRefreshAddAnimal(playerConnection, idAnimal, idTerrain, kind);
            }
        }
    }

    private void singleRefreshAddAnimal(PlayerConnectionRMI playerConnection, int idAnimal, int idTerrain, String kind) {
        try {
            playerConnection.getClientRMI().refreshAddAnimal(idAnimal, idTerrain, kind);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(playerConnection);
        }
    }

    /**
     * Invia a tutti i client il movimento dell'animale
     *
     * @param idAnimal Animale da spostare
     * @param idTerrain Terreno destinazione
     */
    @Override
    public void refreshMoveAnimal(int idAnimal, int idTerrain) {
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshMoveAnimal(idAnimal, idTerrain);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

                    clientDisconnected(playerConnection);
                }
            }
        }
    }

    /**
     * Invia a tutti i client l'animale da rimuovere
     *
     * @param idAnimal Animale da rimuovere
     */
    @Override
    public void refreshKillAnimal(int idAnimal) {
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshKillAnimal(idAnimal);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

                    clientDisconnected(playerConnection);
                }
            }
        }
    }

    /**
     * Invia a tutti i client l'animale da trasformare
     *
     * @param idAnimal Animale da trasformare
     * @param kindFinal Trasformazione finale (whiteSheep, ram)
     */
    @Override
    public void refreshTransformAnimal(int idAnimal, String kindFinal) {
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshTransformAnimal(idAnimal, kindFinal);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

                    clientDisconnected(playerConnection);
                }
            }
        }
    }

    /**
     * Invia al currentPlayer le monete da aggiungere o rimuovere
     *
     * @param coins Valore dei coin
     * @param addCoin True se vanno aggiunti
     */
    @Override
    public void refreshCoin(int coins, boolean addCoin) {
        refreshCoin(currentPlayer, coins, addCoin);
    }

    private void refreshCoin(PlayerConnectionRMI playerConnection, int coins, boolean addCoin) {
        try {
            playerConnection.getClientRMI().refreshCoin(coins, addCoin);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(playerConnection);
        }
    }

    /**
     * Invia al currentPlayer la carta comprata o venduta
     *
     * @param kind Tipo di carta
     * @param isSold True se è venduta
     */
    @Override
    public void refreshCard(String kind, boolean isSold) {
        refreshCard(currentPlayer, kind, isSold);
    }

    private void refreshCard(PlayerConnectionRMI playerConnection, String kind, boolean isSold) {
        try {
            playerConnection.getClientRMI().refreshCard(kind, isSold);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(playerConnection);
        }
    }

    /**
     * Cicla per il numero di azioni massime consentite il method do Action,
     * mette in pausa il ciclo con una variabile, la variabile verra gestita dai
     * metodi chiamati dal client per poter proseguire le azioni alla fine
     * chiama il method nextPlayerConnection
     */
    @Override
    public void startAction() {
        isConnected = true;
        for (actionDone = 0; actionDone < NUMACTION && isConnected; actionDone++) {
            setCanDoAction(false);
            doAction();
            waitResponseFromClient();
        }
        nextPlayerConnections();
    }

    private void waitResponseFromClient() {
        synchronized (objectSyncrinized) {
            while (!canDoAction) {
                try {
                    objectSyncrinized.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void setCanDoAction(boolean b) {
        synchronized (objectSyncrinized) {
            canDoAction = b;
            objectSyncrinized.notifyAll();
        }
    }

    private void setRepeatAction(boolean doRepeat) {
        if (doRepeat) {
            actionDone--;
        }
        setCanDoAction(true);
    }

    /**
     * Risveglia il currentPlayer, riceve indietro un valore se l'azione è
     * andata a buon fine
     *
     * @return True se l'azione è andata a buon fine
     */
    private void doAction() {
        try {
            currentPlayer.getClientRMI().wakeUp();
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(currentPlayer);
        }
    }

    /**
     * Method chiamato dal gameController per serializzare la comunicazione
     * iniziale degli Shepard dei vari giocatori
     *
     * @param idShepard
     * @return Road dove posizionare lo Shepard
     */
    @Override
    public Road getPlacedShepard(int idShepard) {
        try {
            //dice al client di piazzare Shepard
            Integer id = currentPlayer.getClientRMI().getPlaceShepard(idShepard);

            //ricava l'oggetto e lo invia
            return gameController.getGameTable().idToRoad(id);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(currentPlayer);
            return null;
        }
    }

    /**
     * Viene invocato dal connectionClient, muove il pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Destinazione finale
     * @throws RemoteException
     */
    public void moveShepard(int idShepard, int idRoad) throws RemoteException {
        //Converte idShepard nell'oggetto Shepard associato
        Shepard s = gameController.getGameTable().idToShepard(idShepard);

        //Converte idRoad nell'oggetto Road associato
        Road r = gameController.getGameTable().idToRoad(idRoad);

        if (s == null || r == null) {
            printImpossibleSelection();
            return;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.MOVE_SHEPARD.toString())) {
            try {
                boolean refreshCoin = false;
                if (s.isExpensiveMove(r)) {
                    refreshCoin = true;
                }
                gameController.getPlayerPool().getFirstPlayer().moveShepard(r, s, gameController.getGameTable());
                printCorrectAction();
                refreshMoveShepard(idShepard, idRoad);
                if (refreshCoin) {
                    refreshCoin(1, false);
                }
                setCanDoAction(true);
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (ShepardException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            setRepeatAction(true);
            printUncorectAction();
        }
    }

    /**
     * Viene invocato dal connectionClient, muove la pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     * @throws RemoteException
     */
    public void moveSheep(int idSheep, int idTerrain) throws RemoteException {
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(idSheep);

        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (s == null || t == null) {
            printImpossibleSelection();
            return;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.MOVE_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
                printCorrectAction();
                refreshMoveAnimal(idSheep, idTerrain);
                setCanDoAction(true);
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            setRepeatAction(true);
            printUncorectAction();
        }
    }

    /**
     * Viene invocato dal connectionClient, compra una carta
     *
     * @param typeOfTerrain Tipo di carta da comprare
     * @throws RemoteException
     */
    public void buyCard(String typeOfTerrain) throws RemoteException {
        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.BUY_CARD.toString())) {
            try {
                int cost = gameController.getPlayerPool().getFirstPlayer().buyTerrainCard(typeOfTerrain, gameController.getGameTable());
                printCorrectAction();
                refreshCard(typeOfTerrain, false);
                refreshCoin(cost, false);
                setCanDoAction(true);
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (CardException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            setRepeatAction(true);
            printUncorectAction();
        }
    }

    /**
     * Viene invocato dal connectionClient, accoppia un montone e una pecora
     *
     * @param idTerrain Terreno di accoppiamento
     * @throws RemoteException
     */
    public void joinSheep(int idTerrain) throws RemoteException {
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (t == null) {
            printImpossibleSelection();
            return;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.JOIN_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().joinSheeps(t, gameController.getGameTable());
                int idAnimal = gameController.getGameTable().getSheeps().get(gameController.getGameTable().getSheeps().size() - 1).getId();
                printCorrectAction();
                idAnimal++;
                refreshAddAnimal(idAnimal, idTerrain, TypeAnimal.LAMB.toString());
                setCanDoAction(true);
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            setRepeatAction(true);
            printUncorectAction();
        }
    }

    /**
     * Viene invocato dal connectionClient, uccide una pecora
     *
     * @param idSheep Pecora da uccidere
     * @throws RemoteException
     */
    public void killSheep(int idSheep) throws RemoteException {
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(idSheep);

        if (s == null) {
            printImpossibleSelection();
            return;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.KILL_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().killAnimal(s, gameController.getGameTable());
                printCorrectAction();
                refreshKillAnimal(idSheep);
                setCanDoAction(true);
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                setRepeatAction(true);
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (WrongDiceNumberException ex) {
                printCorrectAction();
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                setCanDoAction(true);
            }
        } else {
            setRepeatAction(true);
            printUncorectAction();
        }
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa è andata a buon
     * fine
     */
    private void printCorrectAction() {
        try {
            currentPlayer.getClientRMI().messageText(Message.ACTION_OK.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa NON è andata a
     * buon fine
     */
    private void printUncorectAction() {
        try {
            currentPlayer.getClientRMI().errorMessage(Message.ACTION_ERROR.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printImpossibleSelection() {
        try {
            currentPlayer.getClientRMI().errorMessage(Message.IMPOSSIBLE_SELECTION.toString());
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(currentPlayer);
        }
    }

    private void printErrorMessage(String message) {
        try {
            currentPlayer.getClientRMI().errorMessage(message);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

            clientDisconnected(currentPlayer);
        }
    }

    private void refreshAllFence(PlayerConnectionRMI playerConnection) {
        for (Road road : gameController.getGameTable().getMap().getRoads()) {
            if (road.hasFence()) {
                try {
                    playerConnection.getClientRMI().refreshAddFence(road.getId());
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

                    clientDisconnected(playerConnection);
                }
            }
        }
    }

    /**
     * Invia ad ogni player lo stato finale del giocatore
     */
    public void refreshWinner() {
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            for (PlayerConnectionRMI playerConnection : playerConnections) {
                if (player.getIdPlayer() == playerConnection.getIdPlayer()) {
                    try {
                        playerConnection.getClientRMI().refreshWinner(player.getFinalPosition(), player.getFinalScore());
                    } catch (RemoteException ex) {
                        Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);

                        clientDisconnected(playerConnection);
                    }
                }
            }
        }

        try {
            UnicastRemoteObject.unexportObject(this, true);
            turnOffGame();
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Refresh di tutto il game table nel caso un giocatore si sia ricollegato
     *
     * @param idPlayer
     */
    public void reconnectPlayer(int idPlayer) {
        PlayerConnectionRMI thisPlayer = null;

        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (playerConnection.getIdPlayer() == idPlayer) {
                thisPlayer = playerConnection;
                break;
            }
        }

        boolean isReady = false;
        while (!isReady) {
            isReady = isRadyClient(thisPlayer);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String kind;
        for (Sheep sheep : gameController.getGameTable().getSheeps()) {
            if (sheep.isLamb()) {
                kind = TypeAnimal.LAMB.toString();
            } else if (sheep.isRam()) {
                kind = TypeAnimal.RAM.toString();
            } else {
                kind = TypeAnimal.WHITE_SHEEP.toString();
            }
            singleRefreshAddAnimal(thisPlayer, sheep.getId(), sheep.getPosition().getID(), kind);
        }

        kind = TypeAnimal.WOLF.toString();
        singleRefreshAddAnimal(thisPlayer, -2, gameController.getGameTable().getWolf().getPosition().getID(), kind);

        kind = TypeAnimal.BLACK_SHEEP.toString();
        singleRefreshAddAnimal(thisPlayer, -1, gameController.getGameTable().getBlacksheep().getPosition().getID(), kind);

        boolean isMine;
        for (Shepard shepard : gameController.getGameTable().getShepards()) {
            isMine = shepard.getOwner().getIdPlayer() == idPlayer;
            singeRefreshAddShepard(thisPlayer, shepard.getId(), shepard.getPosition().getId(), isMine);
        }

        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.getIdPlayer() == idPlayer) {
                refreshCoin(thisPlayer, player.getCoins(), true);

                int i;
                for (i = 0; i < player.getTerrainCardsOwned(TypeCard.DESERT.toString()).size(); i++) {
                    refreshCard(thisPlayer, TypeCard.DESERT.toString(), false);
                }
                for (i = 0; i < player.getTerrainCardsOwned(TypeCard.FIELD.toString()).size(); i++) {
                    refreshCard(thisPlayer, TypeCard.FIELD.toString(), false);
                }
                for (i = 0; i < player.getTerrainCardsOwned(TypeCard.FOREST.toString()).size(); i++) {
                    refreshCard(thisPlayer, TypeCard.FOREST.toString(), false);
                }
                for (i = 0; i < player.getTerrainCardsOwned(TypeCard.MOUNTAIN.toString()).size(); i++) {
                    refreshCard(thisPlayer, TypeCard.MOUNTAIN.toString(), false);
                }
                for (i = 0; i < player.getTerrainCardsOwned(TypeCard.PLAIN.toString()).size(); i++) {
                    refreshCard(thisPlayer, TypeCard.PLAIN.toString(), false);
                }
                for (i = 0; i < player.getTerrainCardsOwned(TypeCard.RIVER.toString()).size(); i++) {
                    refreshCard(thisPlayer, TypeCard.RIVER.toString(), false);
                }
            }
        }

        refreshAllFence(thisPlayer);

        map.setOnLine(thisPlayer.getNickname(), true);
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.getIdPlayer() == thisPlayer.getIdPlayer()) {
                player.setOnLine(true);
            }
        }
        
        printMessage(thisPlayer, Message.RECONNECTED.toString());
    }
    
    private void printMessage(PlayerConnectionRMI playerConnection, String message) {
        try {
            playerConnection.getClientRMI().messageText(message);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, Message.DISCONNECTED.toString(), ex);
            
            clientDisconnected(playerConnection);
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
     * Gestisce la disconnessione del client e setta lo stato nell'hash Map
     * ofline
     *
     * @param playerConnection
     */
    public void clientDisconnected(PlayerConnectionRMI playerConnection) {
        if (playerConnection.getIdPlayer() == currentPlayer.getIdPlayer()) {
            isConnected = false;
            setCanDoAction(true);
        }
        map.setOnLine(playerConnection.getNickname(), false);
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.getIdPlayer() == playerConnection.getIdPlayer()) {
                player.setOnLine(false);
            }
        }

        checkAllStatus();
    }

    private void checkAllStatus() {
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.isOnLine()) {
                //Ne trovo almeno uno online
                return;
            }
        }
        //else
        System.out.println("Non ci sono più player collegati\nFine parita!");
        turnOffGame();
    }

    private void turnOffGame() {
        gameController.setConnectionManager(null);
        gameController = null;
    }

    private boolean isAllClientReady() {
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (!isRadyClient(playerConnection)) {
                return false;
            }
        }
        return true;
    }

    private boolean isRadyClient(PlayerConnectionRMI playerConnection) {
        try {
            if (!playerConnection.getClientRMI().isReady()) {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private void waitOkFromClient() {
        while (!isAllClientReady()) {
            try {
                System.out.println("In attesa di tutti i giocatori...");
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Tutto pronto, il gioco ha inizio.");
        checkThread = new CheckThreadRMI();
    }

    private class CheckThreadRMI implements Runnable {

        public CheckThreadRMI() {
            Thread thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (true) {
                checkStatus();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void checkStatus() {
            for (PlayerConnectionRMI playerConnection : playerConnections) {
                try {
                    playerConnection.getClientRMI().isAlive();
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);

                    clientDisconnected(playerConnection);
                }
            }
        }

    }
}
