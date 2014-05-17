package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import java.util.ArrayList;

public class ConnectionManagerSockets extends ConnectionManager {

    private final ArrayList<PlayerConnectionSocket> playerConnections;
    private GameController gameController;
    private Thread thread;

    public ConnectionManagerSockets(ArrayList<PlayerConnectionSocket> playerConnection) {
        this.playerConnections = playerConnection;
        gameController = null;
        thread = new Thread(this);
        thread.start();
    }

    public ArrayList<PlayerConnectionSocket> getPlayerConnections() {
        return playerConnections;
    }
    
    @Override
    public void startThread() {
        gameController = new GameController(playerConnections);
    }
    

}
