package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

/**
 * Classe astratta da cui derivano tutti gli animali
 *
 * @author Francesco Corsini
 */
public abstract class Animal {

    Terrain position;

    /**
     * Costruttore vuoto, viene implementato nelle classi che estendono questa
     * abstract
     */
    public Animal() {
    }

    /**
     * Method in comune a tutti gli animali
     *
     * @return è il Terrain dove si trova l'animale
     */
    public Terrain getPosition() {
        return position;
    }

    /**
     * Method in comune a tutti gli animali, setta il terreno passato come
     * parametro
     *
     * @param val è il Terrain da settare
     */
    public void setPosition(Terrain val) {
        this.position = val;
    }

}
