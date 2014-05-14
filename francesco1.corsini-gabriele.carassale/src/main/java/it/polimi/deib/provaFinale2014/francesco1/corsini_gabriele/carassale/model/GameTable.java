package model;

import java.util.ArrayList;



public class GameTable {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5F8C876B-0C6C-8913-ACBE-764A91679CFE]
    // </editor-fold> 
    private int fenceNumber;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.44B5290E-0100-C374-57C5-2D22A0B48731]
    // </editor-fold> 
    private ArrayList<Sheep> sheeps;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5938AD07-7421-6825-75BD-7A7D4BCEBF94]
    // </editor-fold> 
    private BlackSheep blacksheep;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.854EF9BC-CB95-7643-F31E-28D3FAC449AB]
    // </editor-fold> 
    private Wolf wolf;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.131483A9-F58E-F098-0506-243A62A0B04E]
    // </editor-fold> 
    private Map map;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.08F4968C-843E-FE34-8990-2AB5BBE462A6]
    // </editor-fold> 
    private ArrayList<TerrainCard> terrainCardPool;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FB6DAE3E-10B7-C1C2-CE24-D18079B8DEEC]
    // </editor-fold> 
    private ArrayList<Shepard> shepards;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.E3C98048-B31F-732F-8125-80775907558C]
    // </editor-fold> 
    private Dice dice;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B9F23F11-8940-BFDE-981B-8E24F4D237B3]
    // </editor-fold> 
    public GameTable () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.73AD25D8-8B7E-888F-5BA7-B45FA600D76E]
    // </editor-fold> 
    public BlackSheep getBlacksheep () {
        return blacksheep;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.CBAF199E-15A6-EF5D-5F80-D83D910A0593]
    // </editor-fold> 
    public void setBlacksheep (BlackSheep val) {
        this.blacksheep = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.35AA5F0B-EA8D-490E-24A3-6039CEEB6B4E]
    // </editor-fold> 
    public Dice getDiceNumber () {
        return dice;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.EF3A8694-E058-AF07-6C68-1D85A9819999]
    // </editor-fold> 
    public void setFenceNumber (int val) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.9181B3D6-B865-80B8-7A81-E4E2B344FA67]
    // </editor-fold> 
    public Map getMap () {
        return map;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.C3F54A99-7C3E-405F-04AC-3243B0B1513A]
    // </editor-fold> 
    public void setMap (Map val) {
        this.map = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.2BA1F550-317D-19D0-0FD6-3C15F8299F46]
    // </editor-fold> 
    public ArrayList<Sheep> getSheeps () {
        return sheeps;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.0156B772-49D7-9B2F-E9B0-81D57F03DA33]
    // </editor-fold> 
    public void setSheeps (ArrayList<Sheep> val) {
        this.sheeps = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.258B5A02-3CE7-E6A3-12CF-D8DDF8ADBE1D]
    // </editor-fold> 
    public ArrayList<Shepard> getShepards () {
        return shepards;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.1F1B1E88-7808-5D9A-4E5B-34F5B4680D16]
    // </editor-fold> 
    public void setShepards (ArrayList<Shepard> val) {
        this.shepards = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.512DF908-E5AE-D827-3D83-A84974EA4F6C]
    // </editor-fold> 
    public ArrayList<TerrainCard> getTerrainCardPool () {
        return terrainCardPool;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.0122552E-5B8F-734F-CB8A-233D91C43FFA]
    // </editor-fold> 
    public void setTerrainCardPool (ArrayList<TerrainCard> val) {
        this.terrainCardPool = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.DDAB719C-E817-16B5-0949-1B816E6C6ED0]
    // </editor-fold> 
    public Wolf getWolf () {
        return wolf;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.30BCEB44-F9E1-34F8-FF91-F62E533C75BD]
    // </editor-fold> 
    public void setWolf (Wolf val) {
        this.wolf = val;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BE17BE16-20A4-B7AD-FCD7-26DB8F5962A9]
    // </editor-fold> 
    public int getFenceNumber () {
        return 0;
    }

}

