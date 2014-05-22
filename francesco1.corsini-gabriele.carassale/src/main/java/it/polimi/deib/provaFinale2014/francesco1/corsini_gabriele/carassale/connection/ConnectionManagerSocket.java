package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.WrongDiceNumberException;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea il collegamento diretto tra il GameController e il Client
 * gestito da un ConnectionClient di tipo Socket
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerSocket extends ConnectionManager {

    private final static int NUMACTION = 3;
    private final ArrayList<PlayerConnectionSocket> playerConnections;
    private PlayerConnectionSocket currentPlayer;
    private GameController gameController;
    private Thread thread;

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al metodo .start()
     *
     * @param playerConnectionSockets ArrayList contenente i player associati a
     * questa partita
     */
    public ConnectionManagerSocket(ArrayList<PlayerConnectionSocket> playerConnectionSockets) {
        this.playerConnections = playerConnectionSockets;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Memorizza il currentPlayer prelevandolo dal primo elemento dell'Array,
     * crea il GameController e successivamente lo avvia
     */
    @Override
    public void startThread() {
        currentPlayer = playerConnections.get(0);
        //gameController = null;
        gameController = new GameController(this);
        gameController.start(playerConnections.size());
        //setNickName();
        //refreshGame4AllPlayer();
    }

    /**
     * Cicla per il numero di azioni massime consentite il metodo doAction, in
     * caso di ritorno false dal doAction fa ripetere il metodo finchè non
     * vengono effettuate un numero corretto di azioni, alla fine chiama il
     * metodo nextPlayerConnection
     */
    @Override
    public void startAction() {
        for (int i = 0; i < NUMACTION; i++) {
            if (!doAction()) {
                i--;
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

        Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Mossa del client: {0}", actionToDo);

        boolean actionDo = false;
        if ("moveShepard".equals(actionToDo)) {
            try {
                actionDo = moveShepard();
            } catch (MoveException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorMove");
            } catch (CoinException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorCoin");
            }
        } else if ("moveSheep".equals(actionToDo)) {
            try {
                actionDo = moveSheep();
            } catch (MoveException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorMove");
            }
        } else if ("buyCard".equals(actionToDo)) {
            try {
                actionDo = buyCard();
            } catch (CoinException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorCoin");
            }
        } else if ("killSheep".equals(actionToDo)) {
            try {
                actionDo = killSheep();
            } catch (CoinException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorCoin");
            } catch (MoveException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorMove");
            } catch (WrongDiceNumberException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorDice");
            }
        } else if ("joinSheep".equals(actionToDo)) {
            try {
                actionDo = joinSheep();
            } catch (MoveException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
                currentPlayer.printLn("errorMove");
            }
        }
        //TODO
        //refreshGame4AllPlayer();
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
        pcs.printLn("wakeUp");
    }

    /**
     * Serializza e invia ad ogni Player il gameTable
     */
    public void refreshGame4AllPlayer() {
        Logger.getLogger(ConnectionManagerSocket.class.getName()).fine("Invio la mappa ai giocatori");
        for (PlayerConnectionSocket playerConnection : playerConnections) {

            playerConnection.printLn("refresh");

            FileOutputStream out;
            ObjectOutputStream oos;

            try {
                out = new FileOutputStream("save.ser");
                oos = new ObjectOutputStream(out);
                oos.writeObject(gameController.getGameTable());
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionManagerSocket.class.getName()).log(Level.FINE, "Errore: {0}", ex.getMessage());
            }

        }
    }

    /*
     * Imposta il nickName del Player
     */
    public void setNickName() {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            playerConnection.printLn("setNickname");
            gameController.getPlayerPool().getFirstPlayer().setNickName(playerConnection.getNextLine());
        }
    }

    /**
     * Scorre la lista dei Player, sposta il primo in ultima posizione
     */
    public void nextPlayerConnections() {
        playerConnections.add(playerConnections.get(0));
        playerConnections.remove(0);
        currentPlayer = playerConnections.get(0);
    }

    private boolean moveShepard() throws MoveException, CoinException {
        //Riceve via socket l'ID dello shepard
        String shepard = currentPlayer.getNextLine();
        Integer id = new Integer(shepard);
        //Converte shepard nell'oggetto Shepard associato
        Shepard s = gameController.getGameTable().idToShepard(id);

        //Riceve via socket l'ID della strada
        String road = currentPlayer.getNextLine();
        id = new Integer(road);
        //Converte road nell'oggetto Road associato
        Road r = gameController.getGameTable().idToRoad(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("moveShepard")) {
            gameController.getPlayerPool().getFirstPlayer().moveShepard(r, s, gameController.getGameTable());
            PrintCorrectAction();
            return true;
        } else {
            PrintUncorectAction();
            return false;
        }
    }

    private boolean moveSheep() throws MoveException {
        //Riceve via socket l'ID della sheep
        Integer id = currentPlayer.getNextInt();
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(id);

        //Riceve via socket l'ID del Terrain
        id = currentPlayer.getNextInt();
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("moveSheep")) {
            gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
            PrintCorrectAction();
            return true;
        } else {
            PrintUncorectAction();
            return false;
        }
    }

    private boolean buyCard() throws CoinException {
        //Riceve via socket il tipo di TerrainCard
        String kind = currentPlayer.getNextLine();

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("buyCard")) {
            gameController.getPlayerPool().getFirstPlayer().buyTerrainCard(kind, gameController.getGameTable());
            PrintCorrectAction();
            return true;
        } else {
            PrintUncorectAction();
            return false;
        }
    }

    private boolean killSheep() throws CoinException, MoveException, WrongDiceNumberException {
        //Riceve via socket l'ID della sheep
        Integer id = currentPlayer.getNextInt();
        //Converte sheep nell'oggetto Sheep associato
        Sheep sheep = gameController.getGameTable().idToSheep(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("killSheep")) {
            gameController.getPlayerPool().getFirstPlayer().killAnimal(sheep, gameController.getGameTable());
            PrintCorrectAction();
            return true;
        } else {
            PrintUncorectAction();
            return false;
        }
    }

    private boolean joinSheep() throws MoveException {
        //Riceve via socket l'ID del Terrain
        Integer id = currentPlayer.getNextInt();
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("joinSheep")) {
            gameController.getPlayerPool().getFirstPlayer().joinSheeps(t, gameController.getGameTable());
            PrintCorrectAction();
            return true;
        } else {
            PrintUncorectAction();
            return false;
        }
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa è andata a buon
     * fine
     */
    private void PrintCorrectAction() {
        currentPlayer.printLn("messageText");
        currentPlayer.printLn("Mossa effettua");
    }

    /**
     * Invia al current player un messaggio dicendo che la mossa NON è andata a
     * buon fine
     */
    private void PrintUncorectAction() {
        currentPlayer.printLn("messageText");
        currentPlayer.printLn("Non è possibile fare questa mossa, ricorda di muovere il pastore");

    }

    /**
     * Metodo chiamato dal gameController per serializzare la comunicazione
     * iniziale degli Shepard dei vari giocatori
     *
     * @return Road dove posizionare lo Shepard
     */
    @Override
    public Road getPlacedShepard(boolean hasToScroll) {
        //dice al client di piazzare Shepard
        currentPlayer.printLn("PlaceShepard");
        //attende risposta 
        Integer id = currentPlayer.getNextInt();
        //ricava l'oggetto
        Road roadChoosen = gameController.getGameTable().idToRoad(id);

        if (hasToScroll) {
            nextPlayerConnections();
        }
        return roadChoosen;
    }
}
