package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;



public abstract class Animal {

     
    Terrain position;


    public Animal () {
    }


    public Terrain getPosition () {
        return position;
    }


    public void setPosition (Terrain val) {
        this.position = val;
    }

}

