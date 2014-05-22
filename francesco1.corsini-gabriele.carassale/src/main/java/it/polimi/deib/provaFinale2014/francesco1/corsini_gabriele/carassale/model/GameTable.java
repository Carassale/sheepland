package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList;

/**
 * Classe che unisce tutti i componenti della plancia di gioco. Utile poichè
 * minimizza le connessioni con il controllore
 *
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
     * Costruttore che genera tutti gli elementi sulla tavola
     */
    public GameTable() {
        fenceNumber = 20;
        dice = new Dice();
        map = new Map();
        wolf = new Wolf(map.getTerrain().get(18));
        blacksheep = new BlackSheep(map.getTerrain().get(18));
        initializeSheeps();
        initializeTerrainCards();
    }

    /**
     * Restituisce la pecora nera
     *
     * @return BlackSheep
     */
    public BlackSheep getBlacksheep() {
        return blacksheep;
    }

    /**
     * Abbassa il numero di cancelli
     */
    public void decreaseFenceNumber() {
        fenceNumber--;
    }

    /**
     * Restituisce il numero di cancelli
     *
     * @return numero di cancelli
     */
    public int getFenceNumber() {
        return fenceNumber;
    }

    /**
     * Restituisce la mappa
     *
     * @return Mappa del gioco
     */
    public Map getMap() {
        return map;
    }

    /**
     * Restituisce il dado
     *
     * @return Dice
     */
    public Dice getDice() {
        return dice;
    }

    /**
     * Restituisce l'arreyList contenente le pecore del gioco
     *
     * @return ArrayList contenente pecore
     */
    public ArrayList<Sheep> getSheeps() {
        return sheeps;
    }

    /**
     * Restituisce l'arreyList contenente i pastori del gioco
     *
     * @return ArrayList contenente pastori
     */
    public ArrayList<Shepard> getShepards() {
        return shepards;
    }

    /**
     * Metodo che ritorna l'array della tipologia di TerrainCard desiderata
     *
     * @param string tipologia terreno della carta
     * @return ArrayList<TerrainCard> che si desidera
     */
    public ArrayList<TerrainCard> getTerrainCardPool(String string) {
        if ("Plain".equals(string)) {
            return terrainCardPool.get(0);
        } else if ("Forest".equals(string)) {
            return terrainCardPool.get(1);
        } else if ("River".equals(string)) {
            return terrainCardPool.get(2);
        } else if ("Desert".equals(string)) {
            return terrainCardPool.get(3);
        } else if ("Mountain".equals(string)) {
            return terrainCardPool.get(4);
        } else {
            return terrainCardPool.get(5);
        }
    }

    /**
     * Restituisce il lupo del gioco
     *
     * @return Wolf
     */
    public Wolf getWolf() {
        return wolf;
    }

    /**
     * crea una pecora per terreno
     */
    public void initializeSheeps() {
        for (int i = 0; i < 18; i++) {
            Sheep sheep = new Sheep(map.getTerrain().get(i), true, i);
            sheeps.add(sheep);
        }

    }

    /**
     * crea le carte terreno(non le distribuisce)
     */
    public void initializeTerrainCards() {

        for (int i = 0; i < 6; i++) {
            ArrayList<TerrainCard> list = new ArrayList<TerrainCard>();
            terrainCardPool.add(list);
        }

        for (int i = 0; i < 30; i++) {
            TerrainCard terCar = new TerrainCard();
            terCar.setId(i);
            if (i <= 4) {
                terCar.setTerrainType("Plain");
                terrainCardPool.get(0).add(terCar);
            }
            if (i >= 5 && i <= 9) {
                terCar.setTerrainType("Forest");
                terrainCardPool.get(1).add(terCar);
            }
            if (i >= 10 && i <= 14) {
                terCar.setTerrainType("River");
                terrainCardPool.get(2).add(terCar);
            }
            if (i >= 15 && i <= 19) {
                terCar.setTerrainType("Desert");
                terrainCardPool.get(3).add(terCar);
            }
            if (i >= 20 && i <= 24) {
                terCar.setTerrainType("Mountain");
                terrainCardPool.get(4).add(terCar);
            }
            if (i >= 25 && i <= 30) {
                terCar.setTerrainType("Field");
                terrainCardPool.get(5).add(terCar);
            }

        }
    }

    /**
     * Metodo per trasformare un id in un oggetto pastore
     *
     * @param id intero univoco per l'identificazione
     * @return oggetto identificato
     */
    public Shepard idToShepard(int id) {
        for (Shepard shepard : shepards) {
            if (shepard.getId() == id) {
                return shepard;
            }
        }
        return null;
    }

    /**
     * Metodo per trasformare un id in un oggetto Sheep
     *
     * @param id intero univoco per l'identificazione
     * @return oggetto identificato
     */
    public Sheep idToSheep(int id) {
        for (Sheep sheep : sheeps) {
            if (sheep.getId() == id) {
                return sheep;
            }
        }
        return null;

    }

    /**
     * Metodo per trasformare un id in un oggetto Road
     *
     * @param id intero univoco per l'identificazione
     * @return oggetto identificato
     */
    public Road idToRoad(int id) {
        for (int i = 0; i < map.getRoads().size(); i++) {
            if (map.getRoads().get(i).getId() == id) {
                return map.getRoads().get(i);
            }
        }
        return null;
    }

    /**
     * Metodo per trasformare un id in un oggetto Terrain
     *
     * @param id intero univoco per l'identificazione
     * @return oggetto identificato
     */
    public Terrain idToTerrain(int id) {
        for (int i = 0; i < map.getTerrain().size(); i++) {
            if (map.getTerrain().get(i).getID() == id) {
                return map.getTerrain().get(i);
            }
        }
        return null;
    }

}
