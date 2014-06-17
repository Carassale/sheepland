package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CardException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.Player;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.ShepherdException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.WrongDiceNumberException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepherd;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server.MapServerPlayer;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea il collegamento diretto tra il GameController e il Client
 * gestito da un ConnectionClient di tipo RMI
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerRMI extends ConnectionManager implements ConnectionRMI {

    private Object objectSyncrinized = new Object();
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
    public ConnectionManagerRMI(List<PlayerConnection> playerConnections, MapServerPlayer map) throws RemoteException {
        super(playerConnections, map);
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Cicla per il numero di azioni massime consentite il method do Action,
     * mette in pausa il ciclo chimando il method wait response,alla fine chiama
     * il method nextPlayerConnection
     *
     * @throws FinishGame
     */
    @Override
    public void startAction() throws FinishGame {
        if (currentPlayer.getShepherdToPlace() > 0) {
            placeShepherd(currentPlayer);
        }

        refreshTurnPlayer();

        isConnected = true;
        for (actionDone = 0; actionDone < NUMACTION && isConnected && !isFinishGame; actionDone++) {
            setCanDoAction(false);
            doAction();
            waitResponseFromClient();
        }

        checkIsFinishGame();
        nextPlayerConnections();
    }

    /**
     * Risveglia il currentPlayer, riceve indietro un valore se l'azione è
     * andata a buon fine
     *
     * @return True se l'azione è andata a buon fine
     */
    private void doAction() {
        PlayerConnectionRMI player = (PlayerConnectionRMI) currentPlayer;

        boolean repeat;
        do {
            repeat = false;

            try {
                player.getClientRMI().wakeUp();
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);

                repeat = checkCurrentClientDisconnected();
            }
        } while (repeat);
    }

    /**
     * Viene invocato dal connectionClient, muove il pastore
     *
     * @param idShepherd Pastore da muovere
     * @param idRoad Destinazione finale
     * @throws RemoteException
     */
    public void moveShepherd(int idShepherd, int idRoad) throws RemoteException {
        //Converte idShepherd nell'oggetto Shepherd associato
        Shepherd s = gameController.getGameTable().idToShepherd(idShepherd);

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
                gameController.getPlayerPool().getFirstPlayer().moveShepherd(r, s, gameController.getGameTable());
                printCorrectAction();
                refreshMoveShepherd(idShepherd, idRoad);
                if (refreshCoin) {
                    refreshCoin(1, false);
                }
                setCanDoAction(true);
                return;
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            } catch (ShepherdException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            setRepeatAction(true);
        } else {
            printUncorectAction();
            setRepeatAction(true);
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
            setRepeatAction(true);
            return;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.MOVE_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
                printCorrectAction();
                refreshMoveAnimal(idSheep, idTerrain);
                setCanDoAction(true);
                return;
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            setRepeatAction(true);
        } else {
            printUncorectAction();
            setRepeatAction(true);
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
                return;
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            } catch (CardException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            setRepeatAction(true);
        } else {
            printUncorectAction();
            setRepeatAction(true);
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
                return;
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            setRepeatAction(true);
        } else {
            printUncorectAction();
            setRepeatAction(true);
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
                return;
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            } catch (WrongDiceNumberException ex) {
                printCorrectAction();
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
                setCanDoAction(true);
                return;
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            setRepeatAction(true);
        } else {
            printUncorectAction();
            setRepeatAction(true);
        }
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa è andata a buon
     * fine
     */
    private void printCorrectAction() {
        PlayerConnectionRMI player = (PlayerConnectionRMI) currentPlayer;

        boolean repeat;
        do {
            repeat = false;

            try {
                player.getClientRMI().messageText(Message.ACTION_OK.toString());
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);

                repeat = checkCurrentClientDisconnected();
            }
        } while (repeat);
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa NON è andata a
     * buon fine
     */
    private void printUncorectAction() {
        PlayerConnectionRMI player = (PlayerConnectionRMI) currentPlayer;

        boolean repeat;
        do {
            repeat = false;

            try {
                player.getClientRMI().errorMessage(Message.ACTION_ERROR.toString());
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);

                repeat = checkCurrentClientDisconnected();
            }
        } while (repeat);
    }

    /**
     * Invia al current player un messaggio dicendo che non è possibile
     * effettuare l'azione con gli oggetti selezionati
     */
    private void printImpossibleSelection() {
        PlayerConnectionRMI player = (PlayerConnectionRMI) currentPlayer;

        boolean repeat;
        do {
            repeat = false;

            try {
                player.getClientRMI().errorMessage(Message.IMPOSSIBLE_SELECTION.toString());
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);

                repeat = checkCurrentClientDisconnected();
            }
        } while (repeat);
    }

    /**
     * Stampa un messaggio di errore al current player
     *
     * @param message Messaggio da stampare
     */
    private void printErrorMessage(String message) {
        PlayerConnectionRMI player = (PlayerConnectionRMI) currentPlayer;

        boolean repeat;
        do {
            repeat = false;

            try {
                player.getClientRMI().errorMessage(message);
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);

                repeat = checkCurrentClientDisconnected();
            }
        } while (repeat);
    }

    /**
     * Method chiamato dal gameController per serializzare la comunicazione
     * iniziale degli Shepherd dei vari giocatori
     *
     * @param idShepherd
     * @return Road dove posizionare lo Shepherd
     */
    @Override
    public Road getPlacedShepherd(int idShepherd) {
        refreshTurnPlayer();

        PlayerConnectionRMI player = (PlayerConnectionRMI) currentPlayer;

        boolean repeat;
        do {
            repeat = false;

            try {
                //dice al client di piazzare Shepherd
                Integer id = player.getClientRMI().getPlaceShepherd(idShepherd);

                //ricava l'oggetto e lo invia
                return gameController.getGameTable().idToRoad(id);
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);

                repeat = checkCurrentClientDisconnected();
            }
        } while (repeat);
        return null;
    }

    /**
     * Invia a tutti i client il movimento del pastore
     *
     * @param idShepherd Pastore spostato
     * @param idRoad Strada destinazione
     */
    public void refreshMoveShepherd(int idShepherd, int idRoad) {
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshMoveShepherd(idShepherd, idRoad);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
                }
            }
        }
    }

    /**
     * Invia a tutti i client il pastore aggiunto
     *
     * @param idShepherd Pastore aggiunto
     * @param idRoad Strada posizionamento
     */
    @Override
    public void refreshAddShepherd(int idShepherd, int idRoad) {
        Shepherd s;
        boolean isMine;
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            if (map.isOnLine(playerConnection.getNickname())) {
                s = gameController.getGameTable().idToShepherd(idShepherd);
                isMine = playerConnection.getIdPlayer() == s.getOwner().getIdPlayer();
                singeRefreshAddShepherd(playerConnection, idShepherd, idRoad, isMine);
            }
        }
    }

    /**
     * Invia al player passato come parametro il pastore aggiunto
     *
     * @param player Player a cui inviare
     * @param idShepherd Id del pastore
     * @param idRoad strada in cui aggiungere
     * @param isMine True se è del player a cui invia
     */
    @Override
    public void singeRefreshAddShepherd(PlayerConnection player, int idShepherd, int idRoad, boolean isMine) {
        PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

        try {
            playerConnection.getClientRMI().refreshAddShepherd(idShepherd, idRoad, isMine);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            if (map.isOnLine(playerConnection.getNickname())) {
                singleRefreshAddAnimal(playerConnection, idAnimal, idTerrain, kind);
            }
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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshMoveAnimal(idAnimal, idTerrain);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
                }
            }
        }
    }

    /**
     * Invia al player passato come parametro il l'animale aggiunto
     *
     * @param player Player a cui inviare
     * @param idAnimal id dell'animale da aggiungere
     * @param idTerrain terreno in cui aggiungere
     * @param kind tipo dell'animale
     */
    @Override
    public void singleRefreshAddAnimal(PlayerConnection player, int idAnimal, int idTerrain, String kind) {
        PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

        try {
            playerConnection.getClientRMI().refreshAddAnimal(idAnimal, idTerrain, kind);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }
    }

    /**
     * Invia a tutti i client l'animale da rimuovere
     *
     * @param idAnimal Animale da rimuovere
     */
    @Override
    public void refreshKillAnimal(int idAnimal) {
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshKillAnimal(idAnimal);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            if (map.isOnLine(playerConnection.getNickname())) {
                try {
                    playerConnection.getClientRMI().refreshTransformAnimal(idAnimal, kindFinal);
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
                }
            }
        }
    }

    /**
     * Invia al player passato come parametro la carta comprata o venduta
     *
     * @param player Player a cui inviare
     * @param kind tipo di carta
     * @param isSold True se è venduta
     */
    @Override
    public void refreshCard(PlayerConnection player, String kind, boolean isSold) {
        PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

        try {
            playerConnection.getClientRMI().refreshCard(kind, isSold);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }
    }

    /**
     * Invia al player passato come parametro le monete da aggiungere o
     * rimuovere
     *
     * @param player Player a cui inviare
     * @param coins Valore dei coin
     * @param addCoin True se vanno aggiunti
     */
    @Override
    public void refreshCoin(PlayerConnection player, int coins, boolean addCoin) {
        PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

        try {
            playerConnection.getClientRMI().refreshCoin(coins, addCoin);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }
    }

    /**
     * Invia al player passato come parametro il refresh di tutte le fence
     * presenti sulla plancia
     *
     * @param player
     */
    @Override
    public void refreshAllFence(PlayerConnection player) {
        PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

        for (Road road : gameController.getGameTable().getMap().getRoads()) {
            if (road.hasFence()) {
                try {
                    playerConnection.getClientRMI().refreshAddFence(road.getId());
                } catch (RemoteException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
                }
            }
        }
    }

    /**
     * Invia ad ogni player lo stato finale del giocatore: Punteggio e posizione
     * in classifica
     */
    @Override
    public void refreshWinner() {
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            for (PlayerConnection playerC : playerConnections) {
                PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) playerC;

                if (player.getIdPlayer() == playerConnection.getIdPlayer()) {
                    refreshSingleWinner(playerConnection, player);
                }
            }
        }

        cleanMap();
        unbind();
    }

    private void refreshSingleWinner(PlayerConnectionRMI playerConnection, Player player) {
        try {
            playerConnection.getClientRMI().refreshWinner(player.getFinalPosition(), player.getFinalScore());
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }
    }

    @Override
    public void refreshSingleAddPlayer(PlayerConnection player, String nikcname, int idPlayer) {
        try {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            playerConnection.getClientRMI().refreshAddPlayer(nikcname, idPlayer);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }
    }

    @Override
    public void refreshSingleWaitPlayer(PlayerConnection player, int idPlayer) {
        try {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            playerConnection.getClientRMI().refreshWaitPlayer(idPlayer);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }
    }

    @Override
    public void refreshSingleTurnOffPlayer(PlayerConnection player, int idPlayer, boolean turnOff) {
        try {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            playerConnection.getClientRMI().refreshTurnOffPlayer(idPlayer, turnOff);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }
    }

    @Override
    public void refreshSingleTurnPlayer(PlayerConnection player, int idPlayer) {
        try {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            playerConnection.getClientRMI().refreshTurnPlayer(idPlayer);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
        }

    }

    /**
     * Invia un messaggio al player scelto
     *
     * @param player Player a cui inviare
     * @param message Messaggio da inviare
     */
    @Override
    public void printMessage(PlayerConnection player, String message) {
        PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

        try {
            playerConnection.getClientRMI().messageText(message);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);

        }
    }

    /**
     * Controlla se il player passato come parametro è pronto a giocare
     *
     * @param player
     * @return
     */
    @Override
    public boolean isRadyClient(PlayerConnection player) {
        PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

        try {
            if (!playerConnection.getClientRMI().isReady()) {
                return false;
            }
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
        }
        return true;
    }

    /**
     * Effettua la syncronized su un oggetto e aspetta finchè la viariabile can
     * do action non torna a true
     */
    private void waitResponseFromClient() {
        synchronized (objectSyncrinized) {
            while (!canDoAction) {
                try {
                    objectSyncrinized.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
                }
            }
        }
    }

    /**
     * Effettua la syncronized su un oggetto e imposta la viaribile can do
     * action come il parametro passato, infine fa una notify sull'oggetto
     *
     * @param b
     */
    private void setCanDoAction(boolean b) {
        synchronized (objectSyncrinized) {
            canDoAction = b;
            objectSyncrinized.notifyAll();
        }
    }

    /**
     * Se il parametro passato è true, abbassa di uno il numero di azioni
     * effettuate
     *
     * @param doRepeat
     */
    private void setRepeatAction(boolean doRepeat) {
        if (doRepeat) {
            actionDone--;
        }
        setCanDoAction(true);
    }

    /**
     * Refresh di tutto il game table nel caso un giocatore si sia ricollegato:
     * invia le carte e le monete possedute, la posizione di tutti i pastori,
     * degli animali e delle fence
     *
     * @param idPlayer
     */
    @Override
    public void reconnectPlayer(int idPlayer) {
        super.reconnectPlayer(idPlayer);

        PlayerConnectionRMI thisPlayer = null;
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            if (playerConnection.getIdPlayer() == idPlayer) {
                thisPlayer = playerConnection;
                break;
            }
        }
        try {
            thisPlayer.getClientRMI().setConnectionRMI(this);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
        }
    }

    /**
     * Gestisce la disconnessione del client e setta lo stato nell'hash Map
     * ofline
     *
     * @param playerConnection
     */
    @Override
    public void clientDisconnected(PlayerConnection playerConnection) {
        super.clientDisconnected(playerConnection);

        if (playerConnection.getIdPlayer() == currentPlayer.getIdPlayer()) {
            isConnected = false;
            setCanDoAction(true);
        }
    }

    @Override
    public boolean checkCurrentClientDisconnected() {
        if (super.checkCurrentClientDisconnected()) {
            return true;
        } else {
            isConnected = false;
            setCanDoAction(true);
            return false;
        }
    }

    /**
     * Effettua l'unicast
     */
    private void unbind() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
            turnOffGame();
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
        }
    }

    /**
     * Pulische l'hash map, effettua l'unbind e setta la variabile isFinishGame
     * a true
     */
    @Override
    public void turnOffGame() {
        super.turnOffGame();
        unbind();
    }

    @Override
    public void waitOkFromClient() {
        super.waitOkFromClient();
        changeReferenceToClient();
    }

    /**
     * Per ogni client collegato a questo connectionManager invia il prorpio
     * skeleton
     */
    private void changeReferenceToClient() {
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionRMI playerConnection = (PlayerConnectionRMI) player;

            try {
                playerConnection.getClientRMI().setConnectionRMI(this);
            } catch (RemoteException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), Message.DISCONNECTED.toString(), ex);
            }
        }
    }
}
