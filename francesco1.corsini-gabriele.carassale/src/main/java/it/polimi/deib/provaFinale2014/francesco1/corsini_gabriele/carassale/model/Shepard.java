package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.Player;

/**
 * Classe Pastore che poi sarà assegnato ad ogni giocatore
 *
 * @author Francesco Corsini
 */
public class Shepard {

    private Player owner;
    private Road position;
    private int id;

    /**
     * inizializza lo shepard e lo posiziona sulla plancia
     *
     * @param road posizione
     * @param player giocatore che possiede il pastore
     * @param idShepard id del pastore(serve per serializzazione)
     */
    public Shepard(Road road, Player player, int idShepard) {
        position = road;
        position.setHasShepard(true);
        position.setShepard(this);
        owner = player;
        id = idShepard;
    }

    /**
     * inizializza lo shepard e lo posiziona sulla plancia(costruttore
     * utilizzato nei test)
     *
     * @param road posizione
     */
    public Shepard(Road road) {
        position = road;
        position.setHasShepard(true);
        position.setShepard(this);
    }

    /**
     * Metodo per prendere la posizione
     *
     * @return posizione shepard
     */
    public Road getPosition() {
        return position;
    }

    /**
     * Muove il pastore verso una nuova posizione. Piazza la fence cambiando
     * posizione
     *
     * @param destination dove verrà messo
     */
    public void setPosition(Road destination) {
        position.setHasShepard(false);
        position.setShepard(null);
        position = destination;
        position.setHasShepard(true);
        position.setShepard(this);
    }

    /**
     * ritorna true se la mossa che si sta per compiere costa un denaro. Serve
     * al Player per capire se può fare la mossa
     *
     * @param destination strada dove andare
     * @return true se costa 1 denaro, false altrimenti
     */
    public boolean isExpensiveMove(Road destination) {
        for (Road adjacentRoad : position.getAdjacentRoad()) {
            if (adjacentRoad.equals(destination)) {
                return false;
            }
        }
        return true;
    }

    public void setOwner(Player player) {
        owner = player;
    }

    public Player getOwner() {
        return owner;
    }

    /**
     * Preleva l'id dello Shepard
     *
     * @return int ID dello Shepard
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta un id allo Shepard
     *
     * @param idShepard Id da settare
     */
    public void setId(int idShepard) {
        id = idShepard;
    }

}
