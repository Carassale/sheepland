package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;

/**
 *
 * È unclasse abstract, serve a definire i metodi guida da utilizzare nei due
 * casi Socket o RMI
 *
 * @author Carassale Gabriele
 */
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

    public void startAction() {
    }

    public Road getPlacedShepard() {
        Road road = new Road(100);
        return road;
    }

}
