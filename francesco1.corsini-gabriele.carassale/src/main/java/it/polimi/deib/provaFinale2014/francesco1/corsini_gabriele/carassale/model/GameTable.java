package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList;


/**
 * Classe che unisce tutti i componenti della plancia di gioco. Utile poichè minimizza le connessioni con il controllore
 * @author Francesco Corsini
 */
public class GameTable {
    
    private int fenceNumber;
    private ArrayList<Sheep> sheeps = new ArrayList<Sheep>();
    private final BlackSheep blacksheep;
    private final Wolf wolf;
    private final Map map;
    private ArrayList<TerrainCard> terrainCardPool = new ArrayList<TerrainCard>();
    private ArrayList<Shepard> shepards = new ArrayList<Shepard>();

    
    
    /**
     * inizializzazione che genera tutti gli elementi sulla tavola
     */
    public GameTable () {
        fenceNumber = 20;
        map = new Map();
        wolf = new Wolf(map.getTerrain().get(18));
        blacksheep = new BlackSheep(map.getTerrain().get(18));
        initializeSheeps();
        initializeTerrainCards();
    }


    public BlackSheep getBlacksheep () {
        return blacksheep;
    }


    public void decreaseFenceNumber () {
        fenceNumber--;
    }

    public int getFenceNumber () {
        return fenceNumber;
    }
        
    public Map getMap () {
        return map;
    }

    public ArrayList<Sheep> getSheeps () {
        return sheeps;
    }

    public ArrayList<Shepard> getShepards () {
        return shepards;
    }


    public ArrayList<TerrainCard> getTerrainCardPool () {
        return terrainCardPool;
    }


    public void addTerrainCardPool (TerrainCard val) {
        this.terrainCardPool.add(val);
    }


    public Wolf getWolf () {
        return wolf;
    }


    /**
     * crea una pecora per terreno
     */
    public void  initializeSheeps(  ){
        for(int i=0;i<17;i++){
            Sheep sheep = new Sheep(map.getTerrain().get(i), true);
            map.getTerrain().get(i).addAnimal(sheep);
            sheeps.add(sheep);
        }
            
    }
    
    /**
     * crea le carte terreno(non le distribuisce)
     */
    public void  initializeTerrainCards(  ){
        for(int i=0;i<29;i++){
            TerrainCard terCar = new TerrainCard();
            terCar.setId(i);
            if(i<=4){
                terCar.setTerrainType("Plain");
            }
            if(i<=9)
                terCar.setTerrainType("Forest");
            if(i<=14)
                terCar.setTerrainType("River");
            if(i<=19)
                terCar.setTerrainType("Desert");
            if(i<=24)
                terCar.setTerrainType("Mountain");
            if(i<=29)
                terCar.setTerrainType("Field");
            terrainCardPool.add(terCar);
        }
    }




}

