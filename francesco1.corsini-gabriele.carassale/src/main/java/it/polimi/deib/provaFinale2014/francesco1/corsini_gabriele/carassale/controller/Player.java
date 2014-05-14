package controller;

import connection.PlayerConnection; 
import java.util.ArrayList; 
import model.Animal; 
import model.Map; 
import model.Sheep; 
import model.Terrain_Card; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.81A1811B-6B1C-340A-3043-D9E722D5D21B]
// </editor-fold> 
public class Player {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.63BBC801-1431-F16B-3A76-9691BF887CAB]
    // </editor-fold> 
    private ArrayList<Shepard> shepards;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BDED2F78-378C-9508-61D7-69CAD4071A53]
    // </editor-fold> 
    private Terrain_Card terrainCardsOwned;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.94A25F32-1884-2220-6F19-2C67DFC4F909]
    // </editor-fold> 
    private int coins;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.D76320A4-9F2A-0804-B13C-4B431CC89C18]
    // </editor-fold> 
    private string nickName;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A398DDE5-5959-772E-AF97-EC81B53419B9]
    // </editor-fold> 
    public Player (PlayerConnection playerConnection) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B6AB55B4-9A92-1521-A275-1804B12765C5]
    // </editor-fold> 
    public Player (RMI rmi) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.89FCCA08-E0E0-6EC1-6714-28289DFF83A8]
    // </editor-fold> 
    public ArrayList<Shepard> getShepards () {
        return shepards;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C4B97B77-C482-88D7-0E02-2AC71CE187E9]
    // </editor-fold> 
    public Terrain_Card getTerrainCardsOwned () {
        return terrainCardsOwned;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6D0116D7-3E8B-6D1E-CE8D-54D2369901DD]
    // </editor-fold> 
    public boolean buyTerrainCard (string terrainKind, ArrayList<Integer> terrainCardPool) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C902BEBF-2547-C676-06CF-20D566F832AE]
    // </editor-fold> 
    public boolean moveShepard (Map map, Road destination, Shepard shepard, int fenceNumber) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F1C36706-5459-CA33-2C2A-4E5EF9CE2EE8]
    // </editor-fold> 
    public boolean moveSheep (Map map, Animal animal, Terrain destination) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.3D0B20EB-33C7-39E5-B585-36CD1821AC9F]
    // </editor-fold> 
    public boolean joinSheeps (Map map, Terrain terrain, ArrayList<Sheep> sheeps) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.6C2F8EE0-61B9-110C-353C-3B2A1B4AAD8D]
    // </editor-fold> 
    public int getCoins () {
        return coins;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.A1608848-C7F4-7009-765A-9C762C171E8E]
    // </editor-fold> 
    public void setCoins (int val) {
        this.coins = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5EAD9522-9B4D-1C92-A614-0989AC4B5388]
    // </editor-fold> 
    public boolean killAnimal (Map map, ArrayList<Sheep> sheeps, Sheep sheepToKill) {
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.FD2FDDFC-8D9F-C943-09FD-2E8256C2995C]
    // </editor-fold> 
    public string getNickName () {
        return nickName;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.AD428177-5C90-9244-1B42-8B0E08EF0C1F]
    // </editor-fold> 
    public void setNickName (string val) {
        this.nickName = val;
    }

}

