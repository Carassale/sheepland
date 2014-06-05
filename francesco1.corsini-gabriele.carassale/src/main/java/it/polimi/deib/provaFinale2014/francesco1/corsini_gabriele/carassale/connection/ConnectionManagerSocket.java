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
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea il collegamento diretto tra il GameController e il Client
 * gestito da un ConnectionClient di tipo Socket
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerSocket implements ConnectionManager, Runnable {

    private static final int NUMACTION = 3;
    private final List<PlayerConnectionSocket> playerConnections;
    private PlayerConnectionSocket currentPlayer;
    private GameController gameController;
    private MapServerPlayer map;
    private boolean isConnected;
    private boolean isFinishGame = false;
    private int shepardToPlace = 0;

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al method start
     *
     * @param playerConnection ArrayList contenente i player associati a questa
     * partita
     * @param map
     */
    public ConnectionManagerSocket(List<PlayerConnectionSocket> playerConnection, MapServerPlayer map) {
        this.map = map;
        this.playerConnections = playerConnection;
        Thread thread = new Thread(this);
        thread.start();
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
        try {
            gameController.start(playerConnections.size());
        } catch (FinishGame ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Cicla per il numero di azioni massime consentite il method do Action, in
     * caso di ritorno false dal doAction fa ripetere il method finchè non
     * vengono effettuate un numero corretto di azioni, alla fine chiama il
     * method nextPlayerConnection
     *
     * @throws FinishGame
     */
    @Override
    public void startAction() throws FinishGame {
        if (shepardToPlace > 0) {
            placeShepard(currentPlayer);
        }

        isConnected = true;
        int i = 0;
        while (i < NUMACTION && isConnected) {
            if (doAction()) {
                i++;
            }
        }
        nextPlayerConnections();
        checkIsFinishGame();
    }

    /**
     * Risveglia il currentPlayer, riceve una string contenente l'azione
     * richiesta e chiama quindi il method associato. Nel caso l'azione sollevi
     * un'eccezione viene inviata al client
     *
     * @return True se l'azione è andata a buon fine
     */
    public boolean doAction() {
        try {
            wakeUpPlayer(currentPlayer);
            String actionToDo = currentPlayer.getNextLine();

            boolean actionDo = false;
            if (TypeAction.MOVE_SHEPARD.toString().equals(actionToDo)) {
                actionDo = moveShepard();
            } else if (TypeAction.MOVE_SHEEP.toString().equals(actionToDo)) {
                actionDo = moveSheep();
            } else if (TypeAction.BUY_CARD.toString().equals(actionToDo)) {
                actionDo = buyCard();
            } else if (TypeAction.KILL_SHEEP.toString().equals(actionToDo)) {
                actionDo = killSheep();
            } else if (TypeAction.JOIN_SHEEP.toString().equals(actionToDo)) {
                actionDo = joinSheep();
            }
            return actionDo;
        } catch (PlayerDisconnect ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            clientDisconnected();
            isConnected = false;
            return true;
        }
    }

    /**
     * Ritorna l'arrey contenente i Player connessi alla partita tramite Socket
     *
     * @return ArrayList di PlayerConnectionSocket
     */
    public List<PlayerConnectionSocket> getPlayerConnections() {
        return playerConnections;
    }

    /**
     * Risveglia un player inviando una stringa, serve per il method doAction
     *
     * @param pcs Player da svegliare
     */
    public void wakeUpPlayer(PlayerConnectionSocket pcs) {
        pcs.printLn(TypeAction.WAKE_UP.toString());
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
     * Muove il pastore
     *
     * @return True se la mossa è andata a buon fine
     * @throws MoveException Impossibile muovere
     * @throws CoinException Soldi insufficienti
     */
    private boolean moveShepard() throws PlayerDisconnect {
        //Riceve via socket l'ID dello shepard
        Integer idShepard = currentPlayer.getNextInt();
        //Converte shepard nell'oggetto Shepard associato
        Shepard s = gameController.getGameTable().idToShepard(idShepard);

        //Riceve via socket l'ID della strada
        Integer idRoad = currentPlayer.getNextInt();
        //Converte road nell'oggetto Road associato
        Road r = gameController.getGameTable().idToRoad(idRoad);

        if (s == null || r == null) {
            printImpossibleSelection();
            return false;
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
                return true;
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (ShepardException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            return false;
        } else {
            printUncorectAction();
            return false;
        }
    }

    /**
     * Muove la pecora
     *
     * @return True se la mossa va a buon fine
     * @throws MoveException Impossiblie muovere
     */
    private boolean moveSheep() throws PlayerDisconnect {
        //Riceve via socket l'ID della sheep
        Integer idSheep = currentPlayer.getNextInt();
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(idSheep);

        //Riceve via socket l'ID del Terrain
        Integer idTerrain = currentPlayer.getNextInt();
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (s == null || t == null) {
            printImpossibleSelection();
            return false;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.MOVE_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
                printCorrectAction();
                refreshMoveAnimal(idSheep, idTerrain);
                return true;
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            return false;
        } else {
            printUncorectAction();
            return false;
        }
    }

    /**
     * Compra una carta
     *
     * @return True se l'azione va a buon fine
     * @throws CoinException Soldi insufficienti
     */
    private boolean buyCard() throws PlayerDisconnect {
        //Riceve via socket il tipo di TerrainCard
        String kind = currentPlayer.getNextLine();

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.BUY_CARD.toString())) {
            try {
                int cost = gameController.getPlayerPool().getFirstPlayer().buyTerrainCard(kind, gameController.getGameTable());
                printCorrectAction();
                refreshCoin(cost, false);
                refreshCard(kind, false);
                return true;
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (CardException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            return false;
        } else {
            printUncorectAction();
            return false;
        }
    }

    /**
     * Uccide una pecora
     *
     * @return True se l'azione va a buon fine
     * @throws CoinException Soldi inssufficienti
     * @throws MoveException Mossa non consentita
     * @throws WrongDiceNumberException Errore lancio dado
     */
    private boolean killSheep() throws PlayerDisconnect {
        //Riceve via socket l'ID della sheep
        Integer idSheep = currentPlayer.getNextInt();
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(idSheep);

        if (s == null) {
            printImpossibleSelection();
            return false;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.KILL_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().killAnimal(s, gameController.getGameTable());
                printCorrectAction();
                refreshKillAnimal(idSheep);
                return true;
            } catch (CoinException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            } catch (WrongDiceNumberException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                printCorrectAction();
                return true;
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            return false;
        } else {
            printUncorectAction();
            return false;
        }
    }

    /**
     * Accoppia una pecora e un montone
     *
     * @return True se l'azione va a buon fine
     * @throws MoveException Movimento non consentito
     */
    private boolean joinSheep() throws PlayerDisconnect {
        //Riceve via socket l'ID del Terrain
        Integer idTerrain = currentPlayer.getNextInt();
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (t == null) {
            printImpossibleSelection();
            return false;
        }

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.JOIN_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().joinSheeps(t, gameController.getGameTable());
                int idAnimal = gameController.getGameTable().getSheeps().get(gameController.getGameTable().getSheeps().size() - 1).getId();
                idAnimal++;
                refreshAddAnimal(idAnimal, idTerrain, TypeAnimal.LAMB.toString());
                printCorrectAction();
                return true;
            } catch (MoveException ex) {
                printErrorMessage(ex.getMessage());
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            gameController.getPlayerPool().getFirstPlayer().clearLastAction();
            return false;
        } else {
            printUncorectAction();
            return false;
        }
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa è andata a buon
     * fine
     */
    private void printCorrectAction() {
        currentPlayer.printLn(TypeAction.MESSAGE_TEXT.toString());
        currentPlayer.printLn(Message.ACTION_OK.toString());
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa NON è andata a
     * buon fine
     */
    private void printUncorectAction() {
        currentPlayer.printLn(TypeAction.ERROR_MESSAGE.toString());
        currentPlayer.printLn(Message.ACTION_ERROR.toString());
    }

    /**
     * Invia al current player un messaggio dicendo che gli oggetti selezionati
     * non esistono
     */
    private void printImpossibleSelection() {
        currentPlayer.printLn(TypeAction.ERROR_MESSAGE.toString());
        currentPlayer.printLn(Message.IMPOSSIBLE_SELECTION.toString());
    }

    /**
     * Invia al current player un messagge di errore conentenente un messaggio
     *
     * @param message
     */
    private void printErrorMessage(String message) {
        currentPlayer.printLn(TypeAction.ERROR_MESSAGE.toString());
        currentPlayer.printLn(message);
    }

    /**
     * method chiamato dal gameController per serializzare la comunicazione
     * iniziale degli Shepard dei vari giocatori
     *
     * @param idShepard
     * @return Road dove posizionare lo Shepard
     */
    @Override
    public Road getPlacedShepard(int idShepard) {
        try {
            //dice al client di piazzare Shepard
            currentPlayer.printLn(TypeAction.PLACE_SHEPARD.toString());
            currentPlayer.printLn(idShepard);
            //attende risposta
            Integer id = currentPlayer.getNextInt();
            //ricava l'oggetto e lo invia
            return gameController.getGameTable().idToRoad(id);
        } catch (PlayerDisconnect ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            clientDisconnected();
        }
        return null;
    }

    /**
     * Invia a tutti i client il movimento del pastore
     *
     * @param idShepard Pastore spostato
     * @param idRoad Strada destinazione
     */
    public void refreshMoveShepard(int idShepard, int idRoad) {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            playerConnection.printLn(TypeAction.REFRESH_MOVE_SHEPARD.toString());
            playerConnection.printLn(idShepard);
            playerConnection.printLn(idRoad);
        }
    }

    /**
     * Invia a tutti i client il pastore aggiunto chiamando il method single
     * Refresh add shepard
     *
     * @param idShepard Pastore aggiunto
     * @param idRoad Strada posizionamento
     */
    @Override
    public void refreshAddShepard(int idShepard, int idRoad) {
        Shepard s;
        boolean isMine;
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            s = gameController.getGameTable().idToShepard(idShepard);
            isMine = playerConnection.getIdPlayer() == s.getOwner().getIdPlayer();
            singeRefreshAddShepard(playerConnection, idShepard, idRoad, isMine);
        }
    }

    /**
     * Invia al player scelto il pastore aggiunto
     *
     * @param playerConnection Player a cui inviare il refresh
     * @param idShepard Pastore aggiunto
     * @param idRoad Strada posizionamento
     * @param isMine True se è del player selezionato
     */
    private void singeRefreshAddShepard(PlayerConnectionSocket playerConnection,
            int idShepard, int idRoad, boolean isMine) {
        playerConnection.printLn(TypeAction.REFRESH_ADD_SHEPARD.toString());
        playerConnection.printLn(idShepard);
        playerConnection.printLn(idRoad);
        if (isMine) {
            playerConnection.printLn(0);
        } else {
            playerConnection.printLn(1);
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
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            playerConnection.printLn(TypeAction.REFRESH_MOVE_ANIMAL.toString());
            playerConnection.printLn(idAnimal);
            playerConnection.printLn(idTerrain);
        }
    }

    /**
     * Invia a tutti i client l'animale aggiunto chiamando il method single
     * Refresh add animal
     *
     * @param idAnimal Animale da aggiungere
     * @param idTerrain Terreno destinazione
     * @param kind Tipo di animale (blackSheep, whiteSheep, lamb, ram, wolf)
     */
    @Override
    public void refreshAddAnimal(int idAnimal, int idTerrain, String kind) {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            singleRefreshAddAnimal(playerConnection, idAnimal, idTerrain, kind);
        }
    }

    /**
     * Invia al player scelto l'animale aggiunto
     *
     * @param playerConnection Player a cui inviare
     * @param idAnimal Animale aggiunto
     * @param idTerrain Terreno posizionamento
     * @param kind Tipo di animale (blackSheep, whiteSheep, lamb, ram, wolf)
     */
    private void singleRefreshAddAnimal(PlayerConnectionSocket playerConnection, int idAnimal, int idTerrain, String kind) {
        playerConnection.printLn(TypeAction.REFRESH_ADD_ANIMAL.toString());
        playerConnection.printLn(idAnimal);
        playerConnection.printLn(idTerrain);
        playerConnection.printLn(kind);
    }

    /**
     * Invia a tutti i client l'animale da rimuovere
     *
     * @param idAnimal Animale da rimuovere
     */
    @Override
    public void refreshKillAnimal(int idAnimal) {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            playerConnection.printLn(TypeAction.REFRESH_KILL_ANIMAL.toString());
            playerConnection.printLn(idAnimal);
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
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            playerConnection.printLn(TypeAction.REFRESH_TRANSFORM_ANIMAL.toString());
            playerConnection.printLn(idAnimal);
            playerConnection.printLn(kindFinal);
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

    /**
     * Invia al player scelto la carta comprata o venduta
     *
     * @param playerConnection Player a cui mandare
     * @param kind Tipo di carta
     * @param isSold True se è venduta
     */
    private void refreshCard(PlayerConnectionSocket playerConnection, String kind, boolean isSold) {
        playerConnection.printLn(TypeAction.REFRESH_CARD.toString());
        playerConnection.printLn(kind);
        // isSold TRUE -> 0
        // isSold FALSE -> 1
        if (isSold) {
            playerConnection.printLn(0);
        } else {
            playerConnection.printLn(1);
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

    /**
     * Invia al player scelto le monete da aggiungere o rimuovere
     *
     * @param playerConnection Player scelto
     * @param coins Valore dei coin
     * @param addCoin True se vanno aggiunti
     */
    private void refreshCoin(PlayerConnectionSocket playerConnection, int coins, boolean addCoin) {
        playerConnection.printLn(TypeAction.REFRESH_COIN.toString());
        playerConnection.printLn(coins);
        // addCoin TRUE -> 0
        // addCoin FALSE -> 1
        if (addCoin) {
            playerConnection.printLn(0);
        } else {
            playerConnection.printLn(1);
        }
    }

    /**
     * Invia al player scelto il refresh di tutte le fance indicandone la strada
     *
     * @param playerConnection Player scelto
     */
    private void refreshAllFence(PlayerConnectionSocket playerConnection) {
        for (Road road : gameController.getGameTable().getMap().getRoads()) {
            if (road.hasFence()) {
                playerConnection.printLn(TypeAction.REFRESH_ADD_FENCE.toString());
                playerConnection.printLn(road.getId());
            }
        }
    }

    /**
     * Invia a tutti i player la loro posizione in classifica e la posizione
     * finale
     */
    public void refreshWinner() {
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            for (PlayerConnectionSocket playerConnection : playerConnections) {
                if (player.getIdPlayer() == playerConnection.getIdPlayer()) {
                    playerConnection.printLn(TypeAction.REFRESH_WINNER.toString());
                    playerConnection.printLn(player.getFinalPosition());
                    playerConnection.printLn(player.getFinalScore());
                }
            }
        }
        cleanMap();
    }

    /**
     * Refresh di tutto il game table nel caso un giocatore si sia ricollegato
     *
     * @param idPlayer
     */
    public void reconnectPlayer(int idPlayer) {
        PlayerConnectionSocket thisSocketPlayer = null;
        Player thisGamePlayer = null;

        for (PlayerConnectionSocket playerConnectionSocket : playerConnections) {
            if (playerConnectionSocket.getIdPlayer() == idPlayer) {
                thisSocketPlayer = playerConnectionSocket;
                break;
            }
        }

        for (Player player : gameController.getPlayerPool().getPlayers()) {
            if (player.getIdPlayer() == idPlayer) {
                thisGamePlayer = player;
                break;
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
            singleRefreshAddAnimal(thisSocketPlayer, sheep.getId(), sheep.getPosition().getID(), kind);
        }

        kind = TypeAnimal.WOLF.toString();
        singleRefreshAddAnimal(thisSocketPlayer, -2, gameController.getGameTable().getWolf().getPosition().getID(), kind);

        kind = TypeAnimal.BLACK_SHEEP.toString();
        singleRefreshAddAnimal(thisSocketPlayer, -1, gameController.getGameTable().getBlacksheep().getPosition().getID(), kind);

        boolean isMine;
        for (Shepard shepard : gameController.getGameTable().getShepards()) {
            isMine = shepard.getOwner().getIdPlayer() == idPlayer;
            singeRefreshAddShepard(thisSocketPlayer, shepard.getId(), shepard.getPosition().getId(), isMine);
        }

        refreshCoin(thisSocketPlayer, thisGamePlayer.getCoins(), true);

        int i;
        for (i = 0; i < thisGamePlayer.getTerrainCardsOwned(TypeCard.DESERT.toString()).size(); i++) {
            refreshCard(thisSocketPlayer, TypeCard.DESERT.toString(), false);
        }
        for (i = 0; i < thisGamePlayer.getTerrainCardsOwned(TypeCard.FIELD.toString()).size(); i++) {
            refreshCard(thisSocketPlayer, TypeCard.FIELD.toString(), false);
        }
        for (i = 0; i < thisGamePlayer.getTerrainCardsOwned(TypeCard.FOREST.toString()).size(); i++) {
            refreshCard(thisSocketPlayer, TypeCard.FOREST.toString(), false);
        }
        for (i = 0; i < thisGamePlayer.getTerrainCardsOwned(TypeCard.MOUNTAIN.toString()).size(); i++) {
            refreshCard(thisSocketPlayer, TypeCard.MOUNTAIN.toString(), false);
        }
        for (i = 0; i < thisGamePlayer.getTerrainCardsOwned(TypeCard.PLAIN.toString()).size(); i++) {
            refreshCard(thisSocketPlayer, TypeCard.PLAIN.toString(), false);
        }
        for (i = 0; i < thisGamePlayer.getTerrainCardsOwned(TypeCard.RIVER.toString()).size(); i++) {
            refreshCard(thisSocketPlayer, TypeCard.RIVER.toString(), false);
        }

        refreshAllFence(thisSocketPlayer);

        map.setOnLine(thisSocketPlayer.getNickname(), true);
        thisGamePlayer.setOnLine(true);

        if (thisGamePlayer.getShepards().isEmpty()) {
            shepardToPlace = 1;
        }
        if (playerConnections.size() == 2 && thisGamePlayer.getShepards().isEmpty()) {
            shepardToPlace = 2;
        }

        printMessage(thisSocketPlayer, Message.RECONNECTED.toString());
    }

    private void printMessage(PlayerConnectionSocket playerConnection, String message) {
        playerConnection.printLn(TypeAction.MESSAGE_TEXT.toString());
        playerConnection.printLn(message);
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
     * Viene chiamato nel caso il client si sia disconnesso, modifica i valori
     * onLine nella hash map e nel game controller
     */
    public void clientDisconnected() {
        clientDisconnected(currentPlayer);
    }

    private void clientDisconnected(PlayerConnectionSocket playerConnection) {
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
        System.out.println("Socket: Non ci sono più player collegati");
        turnOffGame();
    }

    private void checkIsFinishGame() throws FinishGame {
        if (isFinishGame) {
            throw new FinishGame("Socket: Partita finita");
        }
    }

    private void cleanMap() {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            map.removePlayer(playerConnection.getNickname());
        }
    }

    private void turnOffGame() {
        cleanMap();
        System.out.println("Socket: Fine parita!");
        isFinishGame = true;
    }

    private boolean isAllClientReady() {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            if (!isClientReady(playerConnection)) {
                return false;
            }
        }
        return true;
    }

    private boolean isClientReady(PlayerConnectionSocket playerConnection) {
        playerConnection.printLn(TypeAction.IS_READY.toString());
        try {
            return Boolean.valueOf(playerConnection.getNextLine());
        } catch (PlayerDisconnect ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return true;
        }
    }

    private void waitOkFromClient() {
        while (!isAllClientReady()) {
            try {
                System.out.println("Socket: In attesa di tutti i giocatori...");
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Socket: Tutto pronto, il gioco ha inizio.");
        new CheckThreadSocket();
    }

    private void placeShepard(PlayerConnectionSocket player) {
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

    private class CheckThreadSocket implements Runnable {

        public CheckThreadSocket() {
            Thread thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (!isFinishGame) {
                checkStatus();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void checkStatus() {
            for (PlayerConnectionSocket playerConnection : playerConnections) {
                if (!playerConnection.hasNext()) {
                    clientDisconnected(playerConnection);
                }
            }
        }

    }
}
