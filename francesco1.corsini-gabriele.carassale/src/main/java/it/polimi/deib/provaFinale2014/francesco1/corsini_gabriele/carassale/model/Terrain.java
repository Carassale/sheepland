package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Classe che modelizza il territorio singolo
 *
 * @author Francesco Corsini
 */
public class Terrain {

    private ArrayList<Road> adjacentRoads = new ArrayList<Road>();
    private boolean sheepsbourg;
    private String terrainType;
    private ArrayList<Animal> animals = new ArrayList<Animal>();
    private int ID;

    public Terrain() {
    }

    public ArrayList<Road> getAdjacentRoads() {
        return adjacentRoads;
    }

    public boolean isAdjacent(Terrain terrain) {
        return true;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

    public void deleteAnimal(Animal animal) {
        try {
            this.animals.remove(this.animals.indexOf(animal));
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.getLogger(Terrain.class.getName()).fine("Errore eliminazione animale dal territorio(usato per spostare o uccidere animali");
        }
    }

    public boolean isSheepsbourg() {
        return sheepsbourg;
    }

    public void setSheepsbourg(boolean val) {
        sheepsbourg = val;
    }

    public String getTerrainType() {
        return terrainType;
    }

    public void setTerrainType(String val) {
        this.terrainType = val;
    }

    public int getID() {
        return ID;
    }

    public void setID(int val) {
        ID = val;
    }
}
