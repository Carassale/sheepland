package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;

public abstract class ConnectionManager implements Runnable {

    private GameController gameController;

    public ConnectionManager() {
    }

    @Override
    public void run() {
        startThread();
    }

    public void startThread() {
    }

}
