package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnection;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Animal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Map;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.TerrainCard;
import java.util.ArrayList;

public class Player {

    private ArrayList<Shepard> shepards;
    private TerrainCard terrainCardsOwned;
    private int coins;
    private String nickName;

    public Player(PlayerConnection playerConnection) {
    }

//    public Player(RMI rmi) {
//    }
    public ArrayList<Shepard> getShepards() {
        return shepards;
    }

    public TerrainCard getTerrainCardsOwned() {
        return terrainCardsOwned;
    }

    public boolean buyTerrainCard(String terrainKind, ArrayList<Integer> terrainCardPool) {
        return true;
    }

    public boolean moveShepard(Map map, Road destination, Shepard shepard, int fenceNumber) {
        return true;
    }

    public boolean moveSheep(Map map, Animal animal, Terrain destination) {
        return true;
    }

    public boolean joinSheeps(Map map, Terrain terrain, ArrayList<Sheep> sheeps) {
        return true;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int val) {
        this.coins = val;
    }

    public boolean killAnimal(Map map, ArrayList<Sheep> sheeps, Sheep sheepToKill) {
        return true;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String val) {
        this.nickName = val;
    }

}
