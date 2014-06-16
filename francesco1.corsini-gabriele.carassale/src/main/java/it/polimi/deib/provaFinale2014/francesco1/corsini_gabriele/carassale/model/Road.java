package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Strada, contiene i terreni adiacenti e le strade adiacenti,
 * rappresenta presenza di Shepherds, Fences , il numero proprio e ha un id
 *
 * @author Francesco Corsini
 */
public class Road {

    private Terrain[] adjacentTerrain = new Terrain[2];
    private List<Road> adjacentRoad = new ArrayList<Road>();
    private boolean hasFence;
    private boolean hasShepherd;
    private int roadNumber;
    private int id;
    private Shepherd shepherd;

    /**
     * inizializzo con il numero di strada. I territori confinanti saranno
     * collegati solo quando tutti gli oggetti Terrain saranno costruiti
     *
     * @param id
     */
    public Road(int id) {
        this.id = id;
        adjacentTerrain[0] = null;
        adjacentTerrain[1] = null;
        hasFence = false;
        hasShepherd = false;
    }

    /**
     * Method per ritornare il 1° territorio annesso
     *
     * @return Terrain adiacente
     */
    public Terrain getAdjacentTerrain1() {
        return adjacentTerrain[0];
    }

    /**
     * Method per ritornare il 2° territorio annesso
     *
     * @return Terrain adiacente
     */
    public Terrain getAdjacentTerrain2() {
        return adjacentTerrain[1];

    }

    /**
     * Method per vedere se la strada è coperta da Fence
     *
     * @return true se c'è la fence
     */
    public boolean hasFence() {
        return hasFence;
    }

    /**
     * Method per mettere una Fence sulla Road
     *
     * @param val true se si vuole la fence
     */
    public void setFence(boolean val) {
        this.hasFence = val;
    }

    /**
     * Method per vedere se la strada è occupata da uno Shepherd
     *
     * @return true se c'è shepherd
     */
    public boolean hasShepherd() {
        return hasShepherd;
    }

    /**
     * Method per settare la presenza dello Shepherd
     *
     * @param val true se si vuole shepherd
     */
    public void setHasShepherd(boolean val) {
        this.hasShepherd = val;
    }

    /**
     * Method per prendere il numero presente sulla strada(quello che si vede
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
     * Method per prendere l'id proprio della strada
     *
     * @return int id strada
     */
    public int getId() {
        return id;
    }

    /**
     * Method per settare l'id strada
     *
     * @param val id che si vuole settare
     */
    public void setId(int val) {
        id = val;
    }

    /**
     * Method per settare il numero della strada, quello visibile poi sul mappa
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
    public List<Road> getAdjacentRoad() {
        return adjacentRoad;
    }

    /**
     * Resituisce lo schepard situato sulla strada
     *
     * @return Shepherd
     */
    public Shepherd getShepherd() {
        return shepherd;
    }

    /**
     * Setta lo Shepherd desiderato sulla strada
     *
     * @param shepherd Shepherd da settare
     */
    public void setShepherd(Shepherd shepherd) {
        this.shepherd = shepherd;
    }
}
