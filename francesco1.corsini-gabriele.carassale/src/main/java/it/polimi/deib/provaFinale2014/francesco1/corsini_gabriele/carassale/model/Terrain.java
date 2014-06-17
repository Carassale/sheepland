package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Classe che modelizza il territorio singolo
 *
 * @author Francesco Corsini
 */
public class Terrain {

    private List<Road> adjacentRoads = new ArrayList<Road>();
    private boolean sheepsbourg;
    private String terrainType;
    private List<Animal> animals = new ArrayList<Animal>();
    private int id;

    /**
     * Crea un terreno
     */
    public Terrain() {
    }

    /**
     * Restituisce l'arrey contenente le strade adiacenti
     *
     * @return ArrayList con le strade adiacenti
     */
    public List<Road> getAdjacentRoads() {
        return adjacentRoads;
    }

    /**
     * Restituisce la lista degli animali contenuti
     *
     * @return ArrayList contenente gli animali
     */
    public List<Animal> getAnimals() {
        return animals;
    }

    /**
     * Aggiunge un animale alla lista
     *
     * @param animal Animale da aggiungere
     */
    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }

    /**
     * Cancella un animale dalla lista
     *
     * @param animal Animale da cancellare
     */
    public void deleteAnimal(Animal animal) {
        try {
            this.animals.remove(this.animals.indexOf(animal));
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger.getLogger(DebugLogger.class.getName())
                    .log(DebugLogger.getLevel(), "Errore eliminazione animale dal territorio(usato per spostare o uccidere animali", e);
        }
    }

    /**
     * Controllo se questo territorio è la capitale Sheepsbourg
     *
     * @return True se è Sheepsbourg
     */
    public boolean isSheepsbourg() {
        return sheepsbourg;
    }

    /**
     * Setta se questo territorio è o no Sheepsbourg
     *
     * @param val True se deve diventare Sheepsbourg
     */
    public void setSheepsbourg(boolean val) {
        sheepsbourg = val;
    }

    /**
     * Restituisce il tipo di terreno
     *
     * @return Stringa contenente il tipo di terreno
     */
    public String getTerrainType() {
        return terrainType;
    }

    /**
     * Setta il tipo di terreno
     *
     * @param terrainType Tipo da settare
     */
    public void setTerrainType(String terrainType) {
        this.terrainType = terrainType;
    }

    /**
     * Restituisce l'Id del terreno
     *
     * @return int id del terreno
     */
    public int getID() {
        return id;
    }

    /**
     * Setta l'Id del terreno
     *
     * @param id int da settare
     */
    public void setID(int id) {
        this.id = id;
    }
}
