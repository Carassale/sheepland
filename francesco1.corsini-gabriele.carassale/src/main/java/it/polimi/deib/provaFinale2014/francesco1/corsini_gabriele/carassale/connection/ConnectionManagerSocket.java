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

/**
 * Questa classe crea il collegamento diretto tra il GameController e il Client
 * gestito da un ConnectionClient di tipo Socket
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerSocket extends ConnectionManager {

    private final ArrayList<PlayerConnectionSocket> playerConnections;
    private PlayerConnectionSocket currentPlayer;
    private GameController gameController;
    private Thread thread;

    public ConnectionManagerSocket(ArrayList<PlayerConnectionSocket> playerConnection) {
        this.playerConnections = playerConnection;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void startThread() {
        currentPlayer = playerConnections.get(0);
        //gameController = null;
        gameController = new GameController(playerConnections.size(), this);
        gameController.start();
        //setNickName();
        //refreshGame4AllPlayer();
    }

    @Override
    public void startAction() {
        for (int i = 0; i < 3; i++) {
            if (!doAction()) {
                i--;
            }
        }
        nextPlayerConnections();
    }

    public boolean doAction() {
        wakeUpPlayer(currentPlayer);
        String actionToDo = currentPlayer.getNextLine();

        System.out.println("Mossa del client: " + actionToDo);

        boolean actionDo = false;
        if (actionToDo.equals("moveShepard")) {
            try {
                actionDo = moveShepard();
            } catch (CoinException ex) {
                currentPlayer.printLn("errorCoin");
                doAction();
            } catch (MoveException ex) {
                currentPlayer.printLn("errorMove");
                doAction();
            }
        } else if (actionToDo.equals("moveSheep")) {
            try {
                actionDo = moveSheep();
            } catch (MoveException ex) {
                currentPlayer.printLn("errorMove");
                doAction();
            }
        } else if (actionToDo.equals("buyCard")) {
            try {
                actionDo = buyCard();
            } catch (CoinException ex) {
                currentPlayer.printLn("errorCoin");
                doAction();
            }
        } else if (actionToDo.equals("killSheep")) {
            try {
                actionDo = killSheep();
            } catch (CoinException ex) {
                currentPlayer.printLn("errorCoin");
                doAction();
            } catch (MoveException ex) {
                currentPlayer.printLn("errorMove");
                doAction();
            } catch (WrongDiceNumberException ex) {
                currentPlayer.printLn("errorDice");
                doAction();
            }
        } else if (actionToDo.equals("joinSheep")) {
            try {
                actionDo = joinSheep();
            } catch (MoveException ex) {
                currentPlayer.printLn("errorMove");
                doAction();
            }
        }

        //TODO
        //refreshGame4AllPlayer();
        return actionDo;
    }

    public ArrayList<PlayerConnectionSocket> getPlayerConnections() {
        return playerConnections;
    }

    public void wakeUpPlayer(PlayerConnectionSocket pcs) {
        pcs.printLn("wakeUp");
    }

    public void refreshGame4AllPlayer() {
        System.out.println("Invio la mappa ai giocatori");
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
                System.out.println("Errore: " + ex.getMessage());
            }
        }
    }

    public void setNickName() {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            playerConnection.printLn("setNickname");
            gameController.getPlayerPool().getFirstPlayer().setNickName(playerConnection.getNextLine());
        }
    }

    public void nextPlayerConnections() {
        playerConnections.add(playerConnections.get(0));
        playerConnections.remove(0);
        currentPlayer = playerConnections.get(0);
    }

    public boolean moveShepard() throws MoveException, CoinException {
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

            return true;
        } else {
            PrintUncorectAction();
            return false;
        }
    }

    public boolean moveSheep() throws MoveException {
        //Riceve via socket l'ID della sheep
        String sheep = currentPlayer.getNextLine();
        Integer id = new Integer(sheep);
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(id);

        //Riceve via socket l'ID del Terrain
        String terrain = currentPlayer.getNextLine();
        id = new Integer(terrain);
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

    public boolean buyCard() throws CoinException {
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

    public boolean killSheep() throws CoinException, MoveException, WrongDiceNumberException {
        //Riceve via socket l'ID della sheep
        String sheep = currentPlayer.getNextLine();
        Integer id = new Integer(sheep);
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("killSheep")) {
            gameController.getPlayerPool().getFirstPlayer().killAnimal(s, gameController.getGameTable());
            PrintCorrectAction();
            return true;
        } else {
            PrintUncorectAction();
            return false;
        }
    }

    public boolean joinSheep() throws MoveException {
        //Riceve via socket l'ID del Terrain
        String terrain = currentPlayer.getNextLine();
        Integer id = new Integer(terrain);
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

    public void PrintCorrectAction() {
        currentPlayer.printLn("messageText");
        currentPlayer.printLn("Mossa effettua");
    }

    public void PrintUncorectAction() {
        currentPlayer.printLn("messageText");
        currentPlayer.printLn("Non è possibile fare questa mossa, ricorda di muovere il pastore");

    }
}
