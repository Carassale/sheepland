package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList; 


public class Terrain {

    //TODO make adjacentRoads Final
    private ArrayList<Road> adjacentRoads;

    
    private boolean sheepsbourg;


    private String terrainType;


    private ArrayList<Animal> animals;


    public Terrain () {
    }


    public ArrayList<Road> getAdjacentRoads () {
        return adjacentRoads;
    }


    public boolean isAdjacent (Terrain terrain) {
        return true;
    }

 
    public ArrayList<Animal> getAnimals () {
        return animals;
    }


    public void addAnimal ( Animal animal) {
        this.animals.add(animal);
    }

    public void  deleteAnimal( Animal animal ){
        this.animals.remove(this.animals.indexOf(animal));
    }

    
    public boolean isSheepsbourg () {
        return sheepsbourg;
    }


    public String getTerrainType () {
        return terrainType;
    }


    public void setTerrainType (String val) {
        this.terrainType = val;
    }

}

