package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.Player;

/**
 * Classe Pastore che poi sarà assegnato ad ogni giocatore
 *
 * @author Francesco Corsini
 */
public class Shepherd {

    private Player owner;
    private Road position;
    private int id;

    /**
     * inizializza lo shepherd e lo posiziona sulla plancia
     *
     * @param road posizione
     * @param player giocatore che possiede il pastore
     * @param idShepherd id del pastore(serve per serializzazione)
     */
    public Shepherd(Road road, Player player, int idShepherd) {
        position = road;
        position.setHasShepherd(true);
        position.setShepherd(this);
        owner = player;
        id = idShepherd;
    }

    /**
     * inizializza lo shepherd e lo posiziona sulla plancia(costruttore
     * utilizzato nei test)
     *
     * @param road posizione
     */
    public Shepherd(Road road) {
        position = road;
        position.setHasShepherd(true);
        position.setShepherd(this);
    }

    /**
     * Method per prendere la posizione
     *
     * @return posizione shepherd
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
        position.setHasShepherd(false);
        position.setShepherd(null);
        position = destination;
        position.setHasShepherd(true);
        position.setShepherd(this);
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
            if (adjacentRoad.getId() == destination.getId()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Setta il player proprietario
     *
     * @param player Player da settare
     */
    public void setOwner(Player player) {
        owner = player;
    }

    /**
     * Ritorna il player proprietario
     *
     * @return Player proprietario
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * Preleva l'id dello Shepherd
     *
     * @return int ID dello Shepherd
     */
    public int getId() {
        return id;
    }

    /**
     * Imposta un id allo Shepherd
     *
     * @param idShepherd Id da settare
     */
    public void setId(int idShepherd) {
        id = idShepherd;
    }

}
