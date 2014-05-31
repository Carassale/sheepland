package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CardException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.Player;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.WrongDiceNumberException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.util.ArrayList;
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
    private final ArrayList<PlayerConnectionSocket> playerConnections;
    private PlayerConnectionSocket currentPlayer;
    private GameController gameController;

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al metodo start
     *
     * @param playerConnection ArrayList contenente i player associati a questa
     * partita
     */
    public ConnectionManagerSocket(ArrayList<PlayerConnectionSocket> playerConnection) {
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
        gameController.start(playerConnections.size());
    }

    /**
     * Cicla per il numero di azioni massime consentite il metodo doAction, in
     * caso di ritorno false dal doAction fa ripetere il metodo finchè non
     * vengono effettuate un numero corretto di azioni, alla fine chiama il
     * metodo nextPlayerConnection
     */
    @Override
    public void startAction() {
        int i = 0;
        while (i < NUMACTION) {
            if (doAction()) {
                i++;
            }
        }
        nextPlayerConnections();
    }

    /**
     * Risveglia il currentPlayer, riceve una string contenente l'azione
     * richiesta e chiama quindi il metodo associato. Nel caso l'azione sollevi
     * un'eccezione viene inviata al client
     *
     * @return True se l'azione è andata a buon fine
     */
    public boolean doAction() {
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
    }

    /**
     * Ritorna l'arrey contenente i Player connessi alla partita tramite Socket
     *
     * @return ArrayList di PlayerConnectionSocket
     */
    public ArrayList<PlayerConnectionSocket> getPlayerConnections() {
        return playerConnections;
    }

    /**
     * Risveglia un player inviando una stringa, serve per il metodo doAction
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
    }

    /**
     * Muove il pastore
     *
     * @return True se la mossa è andata a buon fine
     * @throws MoveException Impossibile muovere
     * @throws CoinException Soldi insufficienti
     */
    private boolean moveShepard() {
        //Riceve via socket l'ID dello shepard
        Integer idShepard = currentPlayer.getNextInt();
        //Converte shepard nell'oggetto Shepard associato
        Shepard s = gameController.getGameTable().idToShepard(idShepard);

        //Riceve via socket l'ID della strada
        Integer idRoad = currentPlayer.getNextInt();
        //Converte road nell'oggetto Road associato
        Road r = gameController.getGameTable().idToRoad(idRoad);

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
                currentPlayer.printLn(TypeAction.ERROR_COIN.toString());
                currentPlayer.printLn(ex.getMessage());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, StatusMessage.ERROR_COIN.toString(), ex);
                return false;
            } catch (MoveException ex) {
                currentPlayer.printLn(TypeAction.ERROR_MOVE.toString());
                currentPlayer.printLn(StatusMessage.ERROR_MOVE.toString());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
                return false;
            }
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
    private boolean moveSheep() {
        //Riceve via socket l'ID della sheep
        Integer idSheep = currentPlayer.getNextInt();
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(idSheep);

        //Riceve via socket l'ID del Terrain
        Integer idTerrain = currentPlayer.getNextInt();
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.MOVE_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
                printCorrectAction();
                refreshMoveAnimal(idSheep, idTerrain);
                return true;
            } catch (MoveException ex) {
                currentPlayer.printLn(TypeAction.ERROR_MOVE.toString());
                currentPlayer.printLn(ex.getMessage());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
                return false;
            }
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
    private boolean buyCard() {
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
                currentPlayer.printLn(TypeAction.ERROR_COIN.toString());
                currentPlayer.printLn(StatusMessage.ERROR_COIN.toString());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, StatusMessage.ERROR_COIN.toString(), ex);
                return false;
            } catch (CardException ex) {
                currentPlayer.printLn(TypeAction.ERROR_CARD.toString());
                currentPlayer.printLn(ex.getMessage());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
                return false;
            }
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
    private boolean killSheep() {
        //Riceve via socket l'ID della sheep
        Integer idSheep = currentPlayer.getNextInt();
        //Converte sheep nell'oggetto Sheep associato
        Sheep sheep = gameController.getGameTable().idToSheep(idSheep);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.KILL_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().killAnimal(sheep, gameController.getGameTable());
                printCorrectAction();
                refreshKillAnimal(idSheep);
                return true;
            } catch (CoinException ex) {
                currentPlayer.printLn(TypeAction.ERROR_COIN.toString());
                currentPlayer.printLn(ex.getMessage());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
                return false;
            } catch (MoveException ex) {
                currentPlayer.printLn(TypeAction.ERROR_MOVE.toString());
                currentPlayer.printLn(ex.getMessage());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
                return false;
            } catch (WrongDiceNumberException ex) {
                currentPlayer.printLn(StatusMessage.ERROR_DICE.toString());
                currentPlayer.printLn(StatusMessage.ERROR_COIN.toString() + ex.getMessage());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, StatusMessage.ERROR_DICE.toString() + ex.getMessage(), ex);
                return false;
            }
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
    private boolean joinSheep() {
        //Riceve via socket l'ID del Terrain
        Integer idTerrain = currentPlayer.getNextInt();
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.JOIN_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().joinSheeps(t, gameController.getGameTable());
                int idAnimal = gameController.getGameTable().getSheeps().get(gameController.getGameTable().getSheeps().size() - 1).getId();
                idAnimal++;
                refreshAddAnimal(idAnimal, idTerrain, TypeAnimal.LAMB.toString());
                printCorrectAction();
                return true;
            } catch (MoveException ex) {
                currentPlayer.printLn(TypeAction.ERROR_MOVE.toString());
                currentPlayer.printLn(ex.getMessage());
                Logger.getLogger(ConnectionManagerSocket.class.getName())
                        .log(Level.SEVERE, ex.getMessage(), ex);
                return false;
            }
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
        currentPlayer.printLn(StatusMessage.ACTION_OK.toString());
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa NON è andata a
     * buon fine
     */
    private void printUncorectAction() {
        currentPlayer.printLn(TypeAction.ERROR_MESSAGE.toString());
        currentPlayer.printLn(StatusMessage.ACTION_ERROR.toString());

    }

    /**
     * Metodo chiamato dal gameController per serializzare la comunicazione
     * iniziale degli Shepard dei vari giocatori
     *
     * @param idShepard
     * @return Road dove posizionare lo Shepard
     */
    @Override
    public Road getPlacedShepard(int idShepard) {
        //dice al client di piazzare Shepard
        currentPlayer.printLn(TypeAction.PLACE_SHEPARD.toString());
        currentPlayer.printLn(idShepard);
        //attende risposta 
        Integer id = currentPlayer.getNextInt();
        //ricava l'oggetto
        Road roadChoosen = gameController.getGameTable().idToRoad(id);

        return roadChoosen;
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
     * Invia a tutti i client il pastore aggiunto
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
     * Invia a tutti i client l'animale aggiunto
     *
     * @param idAnimal
     * @param idTerrain Terreno destinazione
     * @param kind Tipo di animale (blackSheep, whiteSheep, lamb, ram, wolf)
     */
    @Override
    public void refreshAddAnimal(int idAnimal, int idTerrain, String kind) {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            singleRefreshAddAnimal(playerConnection, idAnimal, idTerrain, kind);
        }
    }

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

    private void refreshAllFence(PlayerConnectionSocket playerConnection) {
        for (Road road : gameController.getGameTable().getMap().getRoads()) {
            if (road.hasFence()) {
                playerConnection.printLn(TypeAction.REFRESH_ADD_FENCE.toString());
                playerConnection.printLn(road.getId());
            }
        }
    }

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
    }

    /**
     * Refresh di tutto il game table nel caso un giocatore si sia ricollegato
     *
     * @param idPlayer
     */
    public void refreshAllToPlayer(int idPlayer) {
        PlayerConnectionSocket thisPlayer = null;
        for (PlayerConnectionSocket playerConnectionSocket : playerConnections) {
            if (playerConnectionSocket.getIdPlayer() == idPlayer) {
                thisPlayer = playerConnectionSocket;
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
                refreshCoin(player.getCoins(), true);

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
