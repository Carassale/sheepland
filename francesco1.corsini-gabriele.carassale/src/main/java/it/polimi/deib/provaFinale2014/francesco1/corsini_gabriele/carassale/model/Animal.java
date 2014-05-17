package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;


/**
 * Classe astratta da cui derivano tutti gli animali
 * @author Francesco Corsini
 */
public abstract class Animal {

     
    Terrain position = new Terrain();


    public Animal () {
    }


    public Terrain getPosition () {
        return position;
    }


    public void setPosition (Terrain val) {
        this.position = val;
    }

}

