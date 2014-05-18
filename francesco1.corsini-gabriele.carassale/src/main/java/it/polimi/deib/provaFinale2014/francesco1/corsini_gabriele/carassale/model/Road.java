package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList;

 /**
  * Classe Strada, contiene i terreni adiacenti e le strade adiacenti
  * @author Francesco Corsini
  */
public class Road {

    private Terrain[] adjacentTerrain = new Terrain[2];
    private ArrayList<Road> adjacentRoad = new ArrayList<Road>();
    private boolean fence;
    private boolean shepard;
    private int roadNumber;
    private int id;

    
    /**
     * inizializzo con il numero di strada. I territori confinanti saranno collegati solo quando tutti gli oggetti Terrain saranno costruiti
     * @param number 
     */
    public Road (int number) {
        roadNumber = number;
        adjacentTerrain[0] = null;
        adjacentTerrain[1] = null;
        fence = false;
        shepard = false;
    }
    
    public Terrain getAdjacentTerrain1 () {
        return adjacentTerrain[0];
    }

    public Terrain  getAdjacentTerrain2(  ){
        return adjacentTerrain[1];
        
    }
        
    public boolean hasFence () {
        return fence;
    }

    public void setFence (boolean val) {
        this.fence = val;
    }

    public boolean hasShepard () {
        return shepard;
    }

    public void setShepard (boolean val) {
        this.shepard = val;
    }

    public int getRoadNumber () {
        return roadNumber;
    }
    
    
    /**
     * serve per collegare il terreno alla strada una volta creato il terreno. 
     * @param terrain terreno da connettere
     * @throws TerrainBoundariesExeption viene lanciata se si prova ad aggiungere il 3° terreno
     */
    public void connectTerrainRoad (Terrain terrain) throws TerrainBoundariesExeption{
        
        if(adjacentTerrain[0] == null)
            adjacentTerrain[0] = terrain;
        else if(adjacentTerrain[1] == null)
            adjacentTerrain[1] = terrain;
        else 
            throw new TerrainBoundariesExeption();
    }
  
    public int  getId(  ){
        return id;
    }
    
    public void  setId( int val ){
        id = val;
    }
    
    public void  setRoadNumber( int val ){
        roadNumber = val;
    }
    
    public ArrayList<Road>  getAdjacentRoad(  ){
        return adjacentRoad;
    }
}

