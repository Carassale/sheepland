package model;

 
public class Road {


    private Terrain[] adjacentTerrain = new Terrain[2];
    private boolean fence;
    private boolean shepard;
    private final int roadNumber;

//inizializzo con il numero di strada. I territori confinanti saranno collegati solo quando tutti gli oggetti Terrain saranno costruiti
    public Road (int number) {
        roadNumber = number;
        adjacentTerrain[0] = null;
        adjacentTerrain[1] = null;
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
    //serve per collegare il terreno alla strada una volta creato il terreno
    public void connectTerrainRoad (Terrain terrain) {
        if(adjacentTerrain[0] == null)
            adjacentTerrain[0] = terrain;
        else
            adjacentTerrain[1] = terrain;
    }


}

