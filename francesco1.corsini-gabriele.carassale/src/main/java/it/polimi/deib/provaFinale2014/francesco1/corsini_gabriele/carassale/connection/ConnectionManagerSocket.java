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
    private GameController gameController;
    private Thread thread;

    public ConnectionManagerSocket(ArrayList<PlayerConnectionSocket> playerConnection) {
        this.playerConnections = playerConnection;
        gameController = null;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void startThread() {
        gameController = new GameController(this);
        setNickName();
        refreshGame4AllPlayer();
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
        wakeUpPlayer(playerConnections.get(0));
        String actionToDo = playerConnections.get(0).getScanner().nextLine();
        boolean actionDo = false;
        if (actionToDo.equals("moveShepard")) {
            try {
                actionDo = moveShepard();
            } catch (CoinException ex) {
                playerConnections.get(0).getOutSocket().println("errorCoin");
                doAction();
            } catch (MoveException ex) {
                playerConnections.get(0).getOutSocket().println("errorMove");
                doAction();
            }
        } else if (actionToDo.equals("moveSheep")) {
            try {
                actionDo = moveSheep();
            } catch (MoveException ex) {
                playerConnections.get(0).getOutSocket().println("errorMove");
                doAction();
            }
        } else if (actionToDo.equals("buyCard")) {
            try {
                actionDo = buyCard();
            } catch (CoinException ex) {
                playerConnections.get(0).getOutSocket().println("errorCoin");
                doAction();
            }
        } else if (actionToDo.equals("killSheep")) {
            try {
                actionDo = killSheep();
            } catch (CoinException ex) {
                playerConnections.get(0).getOutSocket().println("errorCoin");
                doAction();
            } catch (MoveException ex) {
                playerConnections.get(0).getOutSocket().println("errorMove");
                doAction();
            } catch (WrongDiceNumberException ex) {
                playerConnections.get(0).getOutSocket().println("errorDice");
                doAction();
            }
        } else if (actionToDo.equals("joinSheep")) {
            try {
                actionDo = joinSheep();
            } catch (MoveException ex) {
                playerConnections.get(0).getOutSocket().println("errorMove");
                doAction();
            }
        }

        refreshGame4AllPlayer();
        return actionDo;
    }

    public ArrayList<PlayerConnectionSocket> getPlayerConnections() {
        return playerConnections;
    }

    public void wakeUpPlayer(PlayerConnectionSocket pcs) {
        pcs.getOutSocket().println("wakeUp");
        pcs.getOutSocket().flush();
    }

    public void refreshGame4AllPlayer() {
        for (PlayerConnectionSocket playerConnection : playerConnections) {

            playerConnection.getOutSocket().println("refresh");
            playerConnection.getOutSocket().flush();

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
            playerConnection.getOutSocket().println("setNickname");
            playerConnection.getOutSocket().flush();
            gameController.getPlayerPool().getFirstPlayer().setNickName(playerConnection.getScanner().nextLine());
        }
    }

    public void nextPlayerConnections() {
        playerConnections.add(playerConnections.get(0));
        playerConnections.remove(0);
    }

    public boolean moveShepard() throws MoveException, CoinException {
        //Riceve via socket l'ID dello shepard
        String shepard = playerConnections.get(0).getScanner().nextLine();
        Integer id = new Integer(shepard);
        //Converte shepard nell'oggetto Shepard associato
        Shepard s = gameController.getGameTable().idToShepard(id);

        //Riceve via socket l'ID della strada
        String road = playerConnections.get(0).getScanner().nextLine();
        id = new Integer(road);
        //Converte road nell'oggetto Road associato
        Road r = gameController.getGameTable().idToRoad(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("moveShepard")) {
            gameController.getPlayerPool().getFirstPlayer().moveShepard(r, s, gameController.getGameTable());
            return true;
        } else {
            return false;
        }
    }

    public boolean moveSheep() throws MoveException {
        //Riceve via socket l'ID della sheep
        String sheep = playerConnections.get(0).getScanner().nextLine();
        Integer id = new Integer(sheep);
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(id);

        //Riceve via socket l'ID del Terrain
        String terrain = playerConnections.get(0).getScanner().nextLine();
        id = new Integer(terrain);
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("moveSheep")) {
            gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
            return true;
        } else {
            return false;
        }
    }

    public boolean buyCard() throws CoinException {
        //Riceve via socket il tipo di TerrainCard
        String kind = playerConnections.get(0).getScanner().nextLine();

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("buyCard")) {
            gameController.getPlayerPool().getFirstPlayer().buyTerrainCard(kind, gameController.getGameTable());
            return true;
        } else {
            return false;
        }
    }

    public boolean killSheep() throws CoinException, MoveException, WrongDiceNumberException {
        //Riceve via socket l'ID della sheep
        String sheep = playerConnections.get(0).getScanner().nextLine();
        Integer id = new Integer(sheep);
        //Converte sheep nell'oggetto Sheep associato
        Sheep s = gameController.getGameTable().idToSheep(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("killSheep")) {
            gameController.getPlayerPool().getFirstPlayer().killAnimal(s, gameController.getGameTable());
            return true;
        } else {
            return false;
        }
    }

    public boolean joinSheep() throws MoveException {
        //Riceve via socket l'ID del Terrain
        String terrain = playerConnections.get(0).getScanner().nextLine();
        Integer id = new Integer(terrain);
        //Converte terrain nell'oggetto Terrain associato
        Terrain t = gameController.getGameTable().idToTerrain(id);

        if (gameController.getPlayerPool().getFirstPlayer().isPossibleAction("joinSheep")) {
            gameController.getPlayerPool().getFirstPlayer().joinSheeps(t, gameController.getGameTable());
            return true;
        } else {
            return false;
        }
    }

}
