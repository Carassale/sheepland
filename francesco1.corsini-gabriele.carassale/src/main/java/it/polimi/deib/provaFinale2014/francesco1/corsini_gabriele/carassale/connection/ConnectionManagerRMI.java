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
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
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

    private boolean canDoAction;
    private boolean doRepeatAction;

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al metodo start
     *
     * @param playerConnections ArrayList contenente i player associati a questa
     * partita
     */
    public ConnectionManagerRMI(ArrayList<PlayerConnectionRMI> playerConnections) throws RemoteException {
        this.playerConnections = playerConnections;
        this.canDoAction = true;
        this.doRepeatAction = false;

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
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    /**
     * Invia a tutti i client il pastore aggiunto
     *
     * @param idShepard Pastore aggiunto
     * @param idRoad Strada posizionamento
     */
    @Override
    public void refreshAddShepard(int idShepard, int idRoad) {
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            singeRefreshAddShepard(playerConnection, idShepard, idRoad);
        }
    }

    private void singeRefreshAddShepard(PlayerConnectionRMI playerConnection, int idShepard, int idRoad) {
        try {
            playerConnection.getClientRMI().refreshAddShepard(idShepard, idRoad);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void singeRefreshAddShepard(PlayerConnectionRMI playerConnection,
            int idShepard, int idRoad, boolean isMine) {
        try {
            playerConnection.getClientRMI().refreshAddShepard(idShepard, idRoad, isMine);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                playerConnection.getClientRMI().refreshMoveShepard(idShepard, idRoad);
            } catch (RemoteException ex) {
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
            singleRefreshAddAnimal(playerConnection, idAnimal, idTerrain, kind);
        }
    }

    private void singleRefreshAddAnimal(PlayerConnectionRMI playerConnection, int idAnimal, int idTerrain, String kind) {
        try {
            playerConnection.getClientRMI().refreshAddAnimal(idAnimal, idTerrain, kind);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                playerConnection.getClientRMI().refreshMoveAnimal(idAnimal, idTerrain);
            } catch (RemoteException ex) {
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                playerConnection.getClientRMI().refreshKillAnimal(idAnimal);
            } catch (RemoteException ex) {
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
            try {
                playerConnection.getClientRMI().refreshTransformAnimal(idAnimal, kindFinal);
            } catch (RemoteException ex) {
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Cicla per il numero di azioni massime consentite il metodo doAction,
     * mette in pausa il ciclo con una variabile, la variabile verra gestita dai
     * metodi chiamati dal client per poter proseguire le azioni alla fine
     * chiama il metodo nextPlayerConnection
     */
    @Override
    public void startAction() {
        for (int i = 0; i < NUMACTION; i++) {
            while (!canDoAction) {
                if (doRepeatAction) {
                    doRepeatAction = false;
                    doAction();
                }
            }
            doAction();
        }
        nextPlayerConnections();
    }

    /**
     * Risveglia il currentPlayer, riceve indietro un valore se l'azione è
     * andata a buon fine
     *
     * @return True se l'azione è andata a buon fine
     */
    private void doAction() {
        canDoAction = false;
        try {
            currentPlayer.getClientRMI().wakeUp();
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            //dice al client di piazzare Shepard
            Integer id = currentPlayer.getClientRMI().getPlaceShepard(idShepard);

            //ricava l'oggetto
            Road roadChoosen = gameController.getGameTable().idToRoad(id);

            return roadChoosen;
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Viene invocato dal connectionClient, muove il pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Destinazione finale
     * @return Un messaggio di conferma
     * @throws RemoteException
     */
    public String moveShepard(int idShepard, int idRoad) throws RemoteException {
        //Converte idShepard nell'oggetto Shepard associato
        Shepard s = gameController.getGameTable().idToShepard(idShepard);

        //Converte idRoad nell'oggetto Road associato
        Road r = gameController.getGameTable().idToRoad(idRoad);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.MOVE_SHEPARD.toString())) {
            try {
                boolean refreshCoin = false;
                if (s.isExpensiveMove(r)) {
                    refreshCoin = true;
                }
                gameController.getPlayerPool().getFirstPlayer().moveShepard(r, s, gameController.getGameTable());
                refreshMoveShepard(idShepard, idRoad);
                if (refreshCoin) {
                    refreshCoin(1, false);
                }
                canDoAction = true;
            } catch (MoveException ex) {
                currentPlayer.getClientRMI().errorMove(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return StatusMessage.ERROR_MOVE.toString();
            } catch (CoinException ex) {
                currentPlayer.getClientRMI().errorCoin(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return ex.getMessage();
            }
            return StatusMessage.ACTION_OK.toString();
        } else {
            doRepeatAction = true;
            return StatusMessage.ACTION_ERROR.toString();
        }
    }

    /**
     * Viene invocato dal connectionClient, muove la pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String moveSheep(int idSheep, int idTerrain) throws RemoteException {
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(idSheep);

        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.MOVE_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
                refreshMoveAnimal(idSheep, idTerrain);
                canDoAction = true;
            } catch (MoveException ex) {
                currentPlayer.getClientRMI().errorMove(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return ex.getMessage();
            }
            return StatusMessage.ACTION_OK.toString();
        } else {
            doRepeatAction = true;
            return StatusMessage.ACTION_ERROR.toString();
        }
    }

    /**
     * Viene invocato dal connectionClient, compra una carta
     *
     * @param typeOfTerrain Tipo di carta da comprare
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String buyCard(String typeOfTerrain) throws RemoteException {
        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.BUY_CARD.toString())) {
            try {
                int cost = gameController.getPlayerPool().getFirstPlayer().buyTerrainCard(typeOfTerrain, gameController.getGameTable());
                refreshCard(typeOfTerrain, false);
                refreshCoin(cost, false);
                canDoAction = true;
            } catch (CoinException ex) {
                currentPlayer.getClientRMI().errorCoin(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return StatusMessage.ERROR_COIN.toString();
            } catch (CardException ex) {
                currentPlayer.getClientRMI().errorCard(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return StatusMessage.ERROR_CARD.toString();
            }
            return StatusMessage.ACTION_OK.toString();
        } else {
            doRepeatAction = true;
            return StatusMessage.ACTION_ERROR.toString();
        }
    }

    /**
     * Viene invocato dal connectionClient, accoppia un montone e una pecora
     *
     * @param idTerrain Terreno di accoppiamento
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String joinSheep(int idTerrain) throws RemoteException {
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(idTerrain);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.JOIN_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().joinSheeps(t, gameController.getGameTable());
                int idAnimal = gameController.getGameTable().getSheeps().get(gameController.getGameTable().getSheeps().size() - 1).getId();
                idAnimal++;
                refreshAddAnimal(idAnimal, idTerrain, TypeAnimal.LAMB.toString());
                canDoAction = true;
            } catch (MoveException ex) {
                currentPlayer.getClientRMI().errorMove(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return ex.getMessage();
            }
            return StatusMessage.ACTION_OK.toString();
        } else {
            doRepeatAction = true;
            return StatusMessage.ACTION_ERROR.toString();
        }
    }

    /**
     * Viene invocato dal connectionClient, uccide una pecora
     *
     * @param idSheep Pecora da uccidere
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String killSheep(int idSheep) throws RemoteException {
        //Converte sheep nell'oggetto Sheep associato
        Sheep sheep = gameController.getGameTable().idToSheep(idSheep);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction(TypeAction.KILL_SHEEP.toString())) {
            try {
                gameController.getPlayerPool().getFirstPlayer().killAnimal(sheep, gameController.getGameTable());
                refreshKillAnimal(idSheep);
                canDoAction = true;
            } catch (CoinException ex) {
                currentPlayer.getClientRMI().errorCoin(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return ex.getMessage();
            } catch (MoveException ex) {
                currentPlayer.getClientRMI().errorMove(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return ex.getMessage();
            } catch (WrongDiceNumberException ex) {
                currentPlayer.getClientRMI().errorDice(ex.getMessage());
                doRepeatAction = true;
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);

                return StatusMessage.ERROR_DICE.toString() + ex.getMessage();
            }
            return StatusMessage.ACTION_OK.toString();
        } else {
            doRepeatAction = true;
            return StatusMessage.ACTION_ERROR.toString();
        }
    }

    /**
     * Refresh di tutto il game table nel caso un giocatore si sia ricollegato
     *
     * @param idPlayer
     */
    public void refreshAllToPlayer(int idPlayer) {
        PlayerConnectionRMI thisPlayer = null;
        for (PlayerConnectionRMI playerConnection : playerConnections) {
            if (playerConnection.getIdPlayer() == idPlayer) {
                thisPlayer = playerConnection;
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
