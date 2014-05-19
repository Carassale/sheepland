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
    private ArrayList<ArrayList<TerrainCard>> terrainCardPool = new ArrayList<ArrayList<TerrainCard>>();
    private ArrayList<Shepard> shepards = new ArrayList<Shepard>();
    private Dice dice;

    
    
    /**
     * inizializzazione che genera tutti gli elementi sulla tavola
     */
    public GameTable () {
        fenceNumber = 20;
        dice = new Dice();
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
    
    public Dice getDice () {
        return dice;
    }

    public ArrayList<Sheep> getSheeps () {
        return sheeps;
    }

    public ArrayList<Shepard> getShepards () {
        return shepards;
    }


    public ArrayList<TerrainCard> getTerrainCardPool (String string) {
        if("Plain".equals(string))
            return terrainCardPool.get(0);
        else if("Forest".equals(string))
            return terrainCardPool.get(1);
        else if("River".equals(string))
            return terrainCardPool.get(2);
        else if("Desert".equals(string))
            return terrainCardPool.get(3);
        else if("Mountain".equals(string))
            return terrainCardPool.get(4);
        else 
            return terrainCardPool.get(5);
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
            sheeps.add(sheep);
        }
            
    }
    
    /**
     * crea le carte terreno(non le distribuisce)
     */
    public void  initializeTerrainCards(  ){
        
        for(int i=0;i<6;i++){
            ArrayList<TerrainCard> list = new ArrayList<TerrainCard>();
            terrainCardPool.add(list);
        }
        
        for(int i=0;i<29;i++){
            TerrainCard terCar = new TerrainCard();
            terCar.setId(i);
            if(i<=4){
                terCar.setTerrainType("Plain");
                terrainCardPool.get(0).add(terCar);
            }
            if(i>=5 && i<=9){
                terCar.setTerrainType("Forest");
                terrainCardPool.get(1).add(terCar);
            }
            if(i>=10 && i<=14){
                terCar.setTerrainType("River");
                terrainCardPool.get(2).add(terCar);
            }
            if(i>=15 && i<=19){
                terCar.setTerrainType("Desert");
                terrainCardPool.get(3).add(terCar);
            }
            if(i>= 20 && i<=24){
                terCar.setTerrainType("Mountain");
                terrainCardPool.get(4).add(terCar);
            }
            if(i>= 25 && i<=29){
                terCar.setTerrainType("Field");
                terrainCardPool.get(5).add(terCar);
            }

        }
    }
    



}

