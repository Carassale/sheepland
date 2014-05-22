package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList;

/**
 * Classe Strada, contiene i terreni adiacenti e le strade adiacenti,
 * rappresenta presenza di Shepards, Fences , il numero proprio e ha un id
 *
 * @author Francesco Corsini
 */
public class Road {

    private Terrain[] adjacentTerrain = new Terrain[2];
    private ArrayList<Road> adjacentRoad = new ArrayList<Road>();
    private boolean hasFence;
    private boolean hasShepard;
    private int roadNumber;
    private int id;
    private Shepard shepard;

    /**
     * inizializzo con il numero di strada. I territori confinanti saranno
     * collegati solo quando tutti gli oggetti Terrain saranno costruiti
     *
     * @param number
     */
    public Road(int number) {
        roadNumber = number;
        adjacentTerrain[0] = null;
        adjacentTerrain[1] = null;
        hasFence = false;
        hasShepard = false;
    }

    /**
     * Metodo per ritornare il 1° territorio annesso
     *
     * @return Terrain adiacente
     */
    public Terrain getAdjacentTerrain1() {
        return adjacentTerrain[0];
    }

    /**
     * Metodo per ritornare il 2° territorio annesso
     *
     * @return Terrain adiacente
     */
    public Terrain getAdjacentTerrain2() {
        return adjacentTerrain[1];

    }

    /**
     * Metodo per vedere se la strada è coperta da Fence
     *
     * @return true se c'è la fence
     */
    public boolean hasFence() {
        return hasFence;
    }

    /**
     * Metodo per mettere una Fence sulla Road
     *
     * @param val true se si vuole la fence
     */
    public void setFence(boolean val) {
        this.hasFence = val;
    }

    /**
     * Metodo per vedere se la strada è occupata da uno Shepard
     *
     * @return true se c'è shepard
     */
    public boolean hasShepard() {
        return hasShepard;
    }

    /**
     * Metodo per settare la presenza dello Shepard
     *
     * @param val true se si vuole shepard
     */
    public void setHasShepard(boolean val) {
        this.hasShepard = val;
    }

    /**
     * Metodo per prendere il numero presente sulla strada(quello che si vede
     * nella plancia, non l'id)
     *
     * @return int
     */
    public int getRoadNumber() {
        return roadNumber;
    }

    /**
     * serve per collegare il terreno alla strada una volta creato il terreno.
     *
     * @param terrain terreno da connettere
     * @throws TerrainBoundariesExeption viene lanciata se si prova ad
     * aggiungere il 3° terreno
     */
    public void connectTerrainRoad(Terrain terrain) throws TerrainBoundariesExeption {
        if (adjacentTerrain[0] == null) {
            adjacentTerrain[0] = terrain;
        } else if (adjacentTerrain[1] == null) {
            adjacentTerrain[1] = terrain;
        } else {
            throw new TerrainBoundariesExeption();
        }
    }

    /**
     * Metodo per prendere l'id proprio della strada
     *
     * @return int id strada
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo per settare l'id strada
     *
     * @param val id che si vuole settare
     */
    public void setId(int val) {
        id = val;
    }

    /**
     * Metodo per settare il numero della strada, quello visibile poi sul mappa
     * del gioco
     *
     * @param val numero che si vuole settare
     */
    public void setRoadNumber(int val) {
        roadNumber = val;
    }

    /**
     * Ritorna le strade adiacenti
     *
     * @return ArrayList contenente strade
     */
    public ArrayList<Road> getAdjacentRoad() {
        return adjacentRoad;
    }

    /**
     * Resituisce lo schepard situato sulla strada
     *
     * @return Shepard
     */
    public Shepard getShepard() {
        return shepard;
    }

    /**
     * Setta lo Shepard desiderato sulla strada
     *
     * @param shepard Shepard da settare
     */
    public void setShepard(Shepard shepard) {
        this.shepard = shepard;
    }
}
