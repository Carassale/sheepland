package model;

import java.util.ArrayList; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.332BBFA5-8021-F977-8476-59DB2284577F]
// </editor-fold> 
public class Map {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.39EBD817-8EE6-D6B0-4919-ECF2AFEF1FD4]
    // </editor-fold> 
    private final ArrayList<ArrayList<Road>> roads;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8ED81C72-807E-2B0E-34EC-91C4B065F747]
    // </editor-fold> 
    private final ArrayList<ArrayList<Terrain>> terrain;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3F263BFF-727C-6742-0974-32CD335BD40D]
    // </editor-fold> 
    public Map () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.14EC1017-B402-0E95-548E-70A26DEC813C]
    // </editor-fold> 
    public ArrayList<ArrayList<Road>> getRoads () {
        return roads;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.EEA10A16-BF09-FC36-08E7-8F14CD8DF69C]
    // </editor-fold> 
    public ArrayList<ArrayList<Terrain>> getTerrain () {
        return terrain;
    }

}

