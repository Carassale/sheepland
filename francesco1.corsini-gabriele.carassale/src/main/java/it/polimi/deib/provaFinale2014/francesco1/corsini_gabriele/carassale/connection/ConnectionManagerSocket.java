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
        gameController = new GameController(playerConnections);
        setNickName();
        refreshGame4AllPlayer();
        startGame();
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

    private void setNickName() {
        for (PlayerConnectionSocket playerConnection : playerConnections) {
            playerConnection.getOutSocket().println("setNickname");
            playerConnection.getOutSocket().flush();
            gameController.getPlayerPool().getFirstPlayer().setNickName(playerConnection.getScanner().nextLine());
        }
    }

    private void nextPlayerConnections() {
        playerConnections.add(playerConnections.get(0));
        playerConnections.remove(0);
    }

    private void startGame() {
        while (true) {
            for (int i = 0; i < 3; i++) {
                doAction();
            }
            nextPlayerConnections();
        }
    }

    private void doAction() {
        wakeUpPlayer(playerConnections.get(0));
        String actionToDo = playerConnections.get(0).getScanner().nextLine();
        if (actionToDo.equals("moveShepard")) {
            try {
                moveShepard();
            } catch (CoinException ex) {
                playerConnections.get(0).getOutSocket().println("errorCoin");
                doAction();
            } catch (MoveException ex) {
                playerConnections.get(0).getOutSocket().println("errorMove");
                doAction();
            }
        } else if (actionToDo.equals("moveSheep")) {
            try {
                moveSheep();
            } catch (MoveException ex) {
                playerConnections.get(0).getOutSocket().println("errorMove");
                doAction();
            }
        } else if (actionToDo.equals("buyCard")) {
            try {
                buyCard();
            } catch (CoinException ex) {
                playerConnections.get(0).getOutSocket().println("errorCoin");
                doAction();
            }
        } else if (actionToDo.equals("killSheep")) {
            try {
                killSheep();
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
                joinSheep();
            } catch (MoveException ex) {
                playerConnections.get(0).getOutSocket().println("errorMove");
                doAction();
            }
        }
    }

    private void moveShepard() throws MoveException, CoinException {
        String shepard = playerConnections.get(0).getScanner().nextLine();
        //Converte shepard nell'oggetto Shepard associato
        String road = playerConnections.get(0).getScanner().nextLine();
        //Converte road nell'oggetto Road associato

        Shepard s = null;
        Road r = null;
        gameController.getPlayerPool().getFirstPlayer().moveShepard(r, s, gameController.getGameTable());
    }

    private void moveSheep() throws MoveException {
        String sheep = playerConnections.get(0).getScanner().nextLine();
        //Converte sheep nell'oggetto Sheep associato
        String terrain = playerConnections.get(0).getScanner().nextLine();
        //Converte terrain nell'oggetto Terrain associato

        Sheep s = null;
        Terrain t = null;
        gameController.getPlayerPool().getFirstPlayer().moveSheep(s, t, gameController.getGameTable());
    }

    private void buyCard() throws CoinException {
        String kind = playerConnections.get(0).getScanner().nextLine();
        gameController.getPlayerPool().getFirstPlayer().buyTerrainCard(kind, gameController.getGameTable());
    }

    private void killSheep() throws CoinException, MoveException, WrongDiceNumberException {
        String sheep = playerConnections.get(0).getScanner().nextLine();
        //Converte sheep nell'oggetto Sheep associato

        Sheep s = null;
        gameController.getPlayerPool().getFirstPlayer().killAnimal(s, gameController.getGameTable());
    }

    private void joinSheep() throws MoveException {
        String terrain = playerConnections.get(0).getScanner().nextLine();
        //Converte terrain nell'oggetto Terrain associato

        Terrain t = null;
        gameController.getPlayerPool().getFirstPlayer().joinSheeps(t, gameController.getGameTable());
    }

}
