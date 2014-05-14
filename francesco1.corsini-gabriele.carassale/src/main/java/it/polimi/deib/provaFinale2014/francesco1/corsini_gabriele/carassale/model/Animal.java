package model;



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

