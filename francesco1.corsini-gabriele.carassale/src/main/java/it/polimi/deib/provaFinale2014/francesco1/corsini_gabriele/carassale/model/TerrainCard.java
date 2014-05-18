package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;


/**
 * Carte Terreno. Utile avere classe per poter gestire il loro scambio tra giocatori
 * @author Francesco Corsini
 */
public class TerrainCard {


    private String terrainType;
    private int id;

    /**
     * costruttore di default utilizzato nella creazione iniziale del GameTable
     */
    public TerrainCard(){  
    }

    /**
     * costruttore usato dal Giocatore quando compra una carta terra
     * @param territoryType tipologia territorio che si vuole comprare
     */
    public TerrainCard (String territoryType) {
        terrainType = territoryType;
    }
    

    public String getTerrainType () {
        return terrainType;
    }


    public void setTerrainType (String val) {
        this.terrainType = val;
    }
    
    public int  getId(  ){
        return id;
    }
    
    public void  setId( int val ){
        id = val;
    }

}

