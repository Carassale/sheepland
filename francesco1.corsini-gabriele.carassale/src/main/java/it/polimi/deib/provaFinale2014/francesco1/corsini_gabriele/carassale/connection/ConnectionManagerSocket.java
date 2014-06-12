package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CardException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea il collegamento diretto tra il GameController e il Client
 * gestito da un ConnectionClient di tipo Socket
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerSocket extends ConnectionManager {

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al method start
     *
     * @param playerConnection ArrayList contenente i player associati a questa
     * partita
     * @param map
     */
    public ConnectionManagerSocket(List<PlayerConnection> playerConnection, MapServerPlayer map) {
        super(playerConnection, map);
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

        refreshTurnPlayer();

        isConnected = true;
        int i = 0;
        while (i < NUMACTION && isConnected && !isFinishGame) {
            if (doAction()) {
                i++;
            }
        }
        checkIsFinishGame();
        nextPlayerConnections();
    }

    /**
     * Risveglia il currentPlayer, riceve una string contenente l'azione
     * richiesta e chiama quindi il method associato. Nel caso l'azione sollevi
     * un'eccezione viene inviata al client
     *
     * @return True se l'azione è andata a buon fine
     */
    public boolean doAction() {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        try {
            wakeUpPlayer(currentPlayer);
            String actionToDo = player.getNextLine();

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
            if (checkCurrentClientDisconnected()) {
                //fa rifare la mossa
                return false;
            } else {
                //passa al prossimo client
                isConnected = false;
                return true;
            }
        }
    }

    /**
     * Risveglia un player inviando una stringa, serve per il method doAction
     *
     * @param player Player da svegliare
     */
    public void wakeUpPlayer(PlayerConnection player) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

        playerConnection.printLn(TypeAction.WAKE_UP.toString());
    }

    /**
     * Muove il pastore
     *
     * @return True se la mossa è andata a buon fine
     * @throws MoveException Impossibile muovere
     * @throws CoinException Soldi insufficienti
     */
    private boolean moveShepard() throws PlayerDisconnect {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        //Riceve via socket l'ID dello shepard
        Integer idShepard = player.getNextInt();
        //Converte shepard nell'oggetto Shepard associato
        Shepard s = gameController.getGameTable().idToShepard(idShepard);

        //Riceve via socket l'ID della strada
        Integer idRoad = player.getNextInt();
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
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        //Riceve via socket l'ID della sheep
        Integer idSheep = player.getNextInt();
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(idSheep);

        //Riceve via socket l'ID del Terrain
        Integer idTerrain = player.getNextInt();
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
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        //Riceve via socket il tipo di TerrainCard
        String kind = player.getNextLine();

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
     * Accoppia una pecora e un montone
     *
     * @return True se l'azione va a buon fine
     * @throws MoveException Movimento non consentito
     */
    private boolean joinSheep() throws PlayerDisconnect {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        //Riceve via socket l'ID del Terrain
        Integer idTerrain = player.getNextInt();
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
     * Uccide una pecora
     *
     * @return True se l'azione va a buon fine
     * @throws CoinException Soldi inssufficienti
     * @throws MoveException Mossa non consentita
     * @throws WrongDiceNumberException Errore lancio dado
     */
    private boolean killSheep() throws PlayerDisconnect {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        //Riceve via socket l'ID della sheep
        Integer idSheep = player.getNextInt();
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
     * Invia al current player un messaggio dicendo che la mossa è andata a buon
     * fine
     */
    private void printCorrectAction() {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        player.printLn(TypeAction.MESSAGE_TEXT.toString());
        player.printLn(Message.ACTION_OK.toString());
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa NON è andata a
     * buon fine
     */
    private void printUncorectAction() {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        player.printLn(TypeAction.ERROR_MESSAGE.toString());
        player.printLn(Message.ACTION_ERROR.toString());
    }

    /**
     * Invia al current player un messaggio dicendo che gli oggetti selezionati
     * non esistono
     */
    private void printImpossibleSelection() {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        player.printLn(TypeAction.ERROR_MESSAGE.toString());
        player.printLn(Message.IMPOSSIBLE_SELECTION.toString());
    }

    /**
     * Invia al current player un messagge di errore conentenente un messaggio
     *
     * @param message
     */
    private void printErrorMessage(String message) {
        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        player.printLn(TypeAction.ERROR_MESSAGE.toString());
        player.printLn(message);
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
        refreshTurnPlayer();

        PlayerConnectionSocket player = (PlayerConnectionSocket) currentPlayer;

        boolean repeat = false;
        do {
            try {
                //dice al client di piazzare Shepard
                player.printLn(TypeAction.PLACE_SHEPARD.toString());
                player.printLn(idShepard);
                //attende risposta
                Integer id = player.getNextInt();
                //ricava l'oggetto e lo invia
                return gameController.getGameTable().idToRoad(id);
            } catch (PlayerDisconnect ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);

                if (checkCurrentClientDisconnected()) {
                    repeat = true;
                }
            }
        } while (repeat);
        return null;
    }

    /**
     * Invia a tutti i client il movimento del pastore
     *
     * @param idShepard Pastore spostato
     * @param idRoad Strada destinazione
     */
    public void refreshMoveShepard(int idShepard, int idRoad) {
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

            s = gameController.getGameTable().idToShepard(idShepard);
            isMine = playerConnection.getIdPlayer() == s.getOwner().getIdPlayer();
            singeRefreshAddShepard(playerConnection, idShepard, idRoad, isMine);
        }
    }

    /**
     * Invia al player scelto il pastore aggiunto
     *
     * @param player Player a cui inviare il refresh
     * @param idShepard Pastore aggiunto
     * @param idRoad Strada posizionamento
     * @param isMine True se è del player selezionato
     */
    @Override
    public void singeRefreshAddShepard(PlayerConnection player, int idShepard, int idRoad, boolean isMine) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

            singleRefreshAddAnimal(playerConnection, idAnimal, idTerrain, kind);
        }
    }

    /**
     * Invia al player scelto l'animale aggiunto
     *
     * @param player Player a cui inviare
     * @param idAnimal Animale aggiunto
     * @param idTerrain Terreno posizionamento
     * @param kind Tipo di animale (blackSheep, whiteSheep, lamb, ram, wolf)
     */
    @Override
    public void singleRefreshAddAnimal(PlayerConnection player, int idAnimal, int idTerrain, String kind) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;
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
        for (PlayerConnection player : playerConnections) {
            PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

            playerConnection.printLn(TypeAction.REFRESH_TRANSFORM_ANIMAL.toString());
            playerConnection.printLn(idAnimal);
            playerConnection.printLn(kindFinal);
        }
    }

    /**
     * Invia al player scelto la carta comprata o venduta
     *
     * @param player Player a cui mandare
     * @param kind Tipo di carta
     * @param isSold True se è venduta
     */
    @Override
    public void refreshCard(PlayerConnection player, String kind, boolean isSold) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

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
     * Invia al player scelto le monete da aggiungere o rimuovere
     *
     * @param player Player scelto
     * @param coins Valore dei coin
     * @param addCoin True se vanno aggiunti
     */
    @Override
    public void refreshCoin(PlayerConnection player, int coins, boolean addCoin) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

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
     * @param player Player scelto
     */
    @Override
    public void refreshAllFence(PlayerConnection player) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

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
    @Override
    public void refreshWinner() {
        for (Player player : gameController.getPlayerPool().getPlayers()) {
            for (PlayerConnection playerC : playerConnections) {
                PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) playerC;

                if (player.getIdPlayer() == playerConnection.getIdPlayer()) {
                    playerConnection.printLn(TypeAction.REFRESH_WINNER.toString());
                    playerConnection.printLn(player.getFinalPosition());
                    playerConnection.printLn(player.getFinalScore());
                }
            }
        }
        cleanMap();
    }

    @Override
    public void refreshSingleAddPlayer(PlayerConnection player, String nikcname, int idPlayer) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

        playerConnection.printLn(TypeAction.REFRESH_ADD_PLAYER.toString());
        playerConnection.printLn(nikcname);
        playerConnection.printLn(idPlayer);
    }

    @Override
    public void refreshSingleWaitPlayer(PlayerConnection player, int idPlayer) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

        playerConnection.printLn(TypeAction.REFRESH_WAIT_PLAYER.toString());
        playerConnection.printLn(idPlayer);
    }

    @Override
    public void refreshSingleTurnOffPlayer(PlayerConnection player, int idPlayer, boolean turnOff) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

        playerConnection.printLn(TypeAction.REFRESH_TURN_OFF_PLAYER.toString());
        playerConnection.printLn(idPlayer);
        playerConnection.printLn(String.valueOf(turnOff));
    }

    @Override
    public void refreshSingleTurnPlayer(PlayerConnection player, int idPlayer) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

        playerConnection.printLn(TypeAction.REFRESH_TURN_PLAYER.toString());
        playerConnection.printLn(idPlayer);
    }

    /**
     * stampa un mesaggio a un player scelto
     *
     * @param player player a cui inviare il messaggio
     * @param message Messaggio da inviare
     */
    @Override
    public void printMessage(PlayerConnection player, String message) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

        playerConnection.printLn(TypeAction.MESSAGE_TEXT.toString());
        playerConnection.printLn(message);
    }

    /**
     * Controlla se il player passato come parametro è pronto a giocare
     *
     * @param player
     * @return
     */
    @Override
    public boolean isRadyClient(PlayerConnection player) {
        PlayerConnectionSocket playerConnection = (PlayerConnectionSocket) player;

        playerConnection.printLn(TypeAction.IS_READY.toString());
        try {
            return Boolean.valueOf(playerConnection.getNextLine());
        } catch (PlayerDisconnect ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return true;
        }
    }

}
