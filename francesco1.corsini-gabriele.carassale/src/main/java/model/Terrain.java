package model;

import java.util.ArrayList; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.B8B9826A-21A7-47B5-F10A-CAB3ADA0C9D5]
// </editor-fold> 
public class Terrain {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.37C2E28F-B800-90A0-7C33-69B45DBBD045]
    // </editor-fold> 
    private final ArrayList<Road> adjacentRoads;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.36D897EF-5E82-6FBC-3FB4-763177E7730C]
    // </editor-fold> 
    private boolean sheepsbourg;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.10B9CF75-00A2-C713-166D-7CA617BEDFFF]
    // </editor-fold> 
    private String terrainType;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F19CB4EE-ECE8-527F-4AAB-3DC29BD4AFC2]
    // </editor-fold> 
    private ArrayList<Animal> animals;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1E2F0B71-3E52-7F52-ADCE-3DE42979CFB0]
    // </editor-fold> 
    public Terrain () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.AEC6FC3E-B634-CE81-6007-969A3A92E277]
    // </editor-fold> 
    public ArrayList<Road> getAdjacentRoads () {
        return adjacentRoads;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E0381078-5881-9083-AD1F-AE5CD0189A85]
    // </editor-fold> 
    public boolean isAdjacent (Terrain terrain) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.0B63CA4F-2617-5320-BA55-6E3FE03157AD]
    // </editor-fold> 
    public ArrayList<Animal> getAnimals () {
        return animals;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.A5B3B52C-45A3-A1D0-DD27-A08F82AE2A2A]
    // </editor-fold> 
    public void setAnimals (ArrayList<Animal> val) {
        this.animals = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.1220A0CC-6BAF-2E62-06C0-203C62044BC1]
    // </editor-fold> 
    public boolean isSheepsbourg () {
        return sheepsbourg;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.8DA81509-E067-AB54-D745-D641F188E034]
    // </editor-fold> 
    public String getTerrainType () {
        return terrainType;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.BBBD3C58-48B1-8119-2B85-C441FE1541B5]
    // </editor-fold> 
    public void setTerrainType (String val) {
        this.terrainType = val;
    }

}

