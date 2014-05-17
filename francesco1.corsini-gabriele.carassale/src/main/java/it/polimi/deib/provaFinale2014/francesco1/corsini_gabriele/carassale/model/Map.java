package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList; 
import java.util.Iterator;


public class Map {

    private ArrayList<Road> roads;
    private ArrayList<Terrain> terrain;

    //in questo costruttore viene creata la mappa da codice(hard coded)
    public Map () {
        for(int i=0;i<17;i++){
            terrain.add(generateTerrain(i));    
        }
        for(int i=0;i<41;i++){
            try{
            roads.add(generateRoad(i));                
            }
            catch(TerrainBoundariesExeption e){
                System.out.println("Errore nella creazione delle strade");
            }
        }
        for(int i=0;i<17;i++){
            connectRoadsToTerrains(i);
        }
        for(int i=0;i<41;i++){
            connectRoadsToRoads(i);
        }
    }


    public ArrayList<Road> getRoads () {
        return roads;
    }


    public ArrayList<Terrain> getTerrain () {
        return terrain;
    }
    
    private Terrain generateTerrain(int i){
        
            Terrain ter = new Terrain();
            ter.setID(i);
            ter.setSheepsbourg(false);
            if(i<=2)
                ter.setTerrainType("Plain");
            else if(i>=3 && i<=5)
                ter.setTerrainType("Forest");
            else if(i>=6 && i<=8)
                ter.setTerrainType("River");
            else if(i>=9 && i<=11)
                ter.setTerrainType("Desert");
            else if(i>=12 && i<=14)
                ter.setTerrainType("Mountain");
            else if(i>=15 && i<=17)
                ter.setTerrainType("Field");
            else{
                ter.setTerrainType("Sheepsbourg");
                ter.setSheepsbourg(true);
            }
            return ter;
    }
    
    private Road generateRoad(int id) throws TerrainBoundariesExeption{
        Road road = new Road(id);
        road.setId(id);
        road.setFence(false);
        if(id == 0){
            road.connectTerrainRoad(terrain.get(0));
            road.connectTerrainRoad(terrain.get(16)); 
            road.setRoadNumber(2);
        }
        else if(id == 1){
            road.connectTerrainRoad(terrain.get(0));
            road.connectTerrainRoad(terrain.get(17)); 
            road.setRoadNumber(3);            
        }
        else if(id == 2){
            road.connectTerrainRoad(terrain.get(0));
            road.connectTerrainRoad(terrain.get(17)); 
            road.setRoadNumber(1);            
        }
        else if(id == 3){
            road.connectTerrainRoad(terrain.get(1));
            road.connectTerrainRoad(terrain.get(17)); 
            road.setRoadNumber(2);            
        }
        else if(id == 4){
            road.connectTerrainRoad(terrain.get(1));
            road.connectTerrainRoad(terrain.get(3)); 
            road.setRoadNumber(2);            
        }
        else if(id == 5){
            road.connectTerrainRoad(terrain.get(1));
            road.connectTerrainRoad(terrain.get(3)); 
            road.setRoadNumber(4);            
        }
        else if(id == 6){
            road.connectTerrainRoad(terrain.get(3));
            road.connectTerrainRoad(terrain.get(2)); 
            road.setRoadNumber(2);            
        }
        else if(id == 7){
            road.connectTerrainRoad(terrain.get(3));
            road.connectTerrainRoad(terrain.get(4)); 
            road.setRoadNumber(1);            
        }
        else if(id == 8){
            road.connectTerrainRoad(terrain.get(2));
            road.connectTerrainRoad(terrain.get(4)); 
            road.setRoadNumber(4);            
        }
        else if(id == 9){
            road.connectTerrainRoad(terrain.get(4));
            road.connectTerrainRoad(terrain.get(5)); 
            road.setRoadNumber(3);            
        }
        else if(id == 10){
            road.connectTerrainRoad(terrain.get(4));
            road.connectTerrainRoad(terrain.get(6)); 
            road.setRoadNumber(2);            
        }
        else if(id == 11){
            road.connectTerrainRoad(terrain.get(5));
            road.connectTerrainRoad(terrain.get(6)); 
            road.setRoadNumber(1);            
        }
        else if(id == 12){
            road.connectTerrainRoad(terrain.get(6));
            road.connectTerrainRoad(terrain.get(7)); 
            road.setRoadNumber(5);            
        }
        else if(id == 13){
            road.connectTerrainRoad(terrain.get(5));
            road.connectTerrainRoad(terrain.get(7)); 
            road.setRoadNumber(2);            
        }
        else if(id == 14){
            road.connectTerrainRoad(terrain.get(7));
            road.connectTerrainRoad(terrain.get(8)); 
            road.setRoadNumber(6);            
        }
        else if(id == 15){
            road.connectTerrainRoad(terrain.get(7));
            road.connectTerrainRoad(terrain.get(9)); 
            road.setRoadNumber(1);            
        }
        else if(id == 16){
            road.connectTerrainRoad(terrain.get(8));
            road.connectTerrainRoad(terrain.get(9)); 
            road.setRoadNumber(4);            
        }
        else if(id == 17){
            road.connectTerrainRoad(terrain.get(10));
            road.connectTerrainRoad(terrain.get(9)); 
            road.setRoadNumber(5);            
        }
        else if(id == 18){
            road.connectTerrainRoad(terrain.get(8));
            road.connectTerrainRoad(terrain.get(10)); 
            road.setRoadNumber(2);            
        }
        else if(id == 19){
            road.connectTerrainRoad(terrain.get(11));
            road.connectTerrainRoad(terrain.get(10)); 
            road.setRoadNumber(6);            
        }
        else if(id == 20){
            road.connectTerrainRoad(terrain.get(10));
            road.connectTerrainRoad(terrain.get(12)); 
            road.setRoadNumber(1);            
        }
        else if(id == 21){
            road.connectTerrainRoad(terrain.get(11));
            road.connectTerrainRoad(terrain.get(12)); 
            road.setRoadNumber(3);            
        }
        else if(id == 22){
            road.connectTerrainRoad(terrain.get(12));
            road.connectTerrainRoad(terrain.get(13)); 
            road.setRoadNumber(2);            
        }
        else if(id == 23){
            road.connectTerrainRoad(terrain.get(11));
            road.connectTerrainRoad(terrain.get(13)); 
            road.setRoadNumber(5);            
        }
        else if(id == 24){
            road.connectTerrainRoad(terrain.get(13));
            road.connectTerrainRoad(terrain.get(14)); 
            road.setRoadNumber(3);            
        }
        else if(id == 25){
            road.connectTerrainRoad(terrain.get(13));
            road.connectTerrainRoad(terrain.get(15)); 
            road.setRoadNumber(1);            
        }
        else if(id == 26){
            road.connectTerrainRoad(terrain.get(14));
            road.connectTerrainRoad(terrain.get(15)); 
            road.setRoadNumber(2);            
        }
        else if(id == 27){
            road.connectTerrainRoad(terrain.get(15));
            road.connectTerrainRoad(terrain.get(16)); 
            road.setRoadNumber(3);            
        }
        else if(id == 28){
            road.connectTerrainRoad(terrain.get(14));
            road.connectTerrainRoad(terrain.get(16)); 
            road.setRoadNumber(6);            
        }
        else if(id == 29){
            road.connectTerrainRoad(terrain.get(16));
            road.connectTerrainRoad(terrain.get(17)); 
            road.setRoadNumber(4);            
        }
        else if(id == 30){
            road.connectTerrainRoad(terrain.get(14));
            road.connectTerrainRoad(terrain.get(17)); 
            road.setRoadNumber(5);            
        }
        else if(id == 31){
            road.connectTerrainRoad(terrain.get(17));
            road.connectTerrainRoad(terrain.get(18)); 
            road.setRoadNumber(6);            
        }
        else if(id == 32){
            road.connectTerrainRoad(terrain.get(2));
            road.connectTerrainRoad(terrain.get(17)); 
            road.setRoadNumber(1);            
        }
        else if(id == 33){
            road.connectTerrainRoad(terrain.get(2));
            road.connectTerrainRoad(terrain.get(18)); 
            road.setRoadNumber(5);            
        }
        else if(id == 34){
            road.connectTerrainRoad(terrain.get(2));
            road.connectTerrainRoad(terrain.get(5)); 
            road.setRoadNumber(6);            
        }
        else if(id == 35){
            road.connectTerrainRoad(terrain.get(5));
            road.connectTerrainRoad(terrain.get(18)); 
            road.setRoadNumber(4);            
        }
        else if(id == 36){
            road.connectTerrainRoad(terrain.get(5));
            road.connectTerrainRoad(terrain.get(8)); 
            road.setRoadNumber(5);            
        }
        else if(id == 37){
            road.connectTerrainRoad(terrain.get(8));
            road.connectTerrainRoad(terrain.get(18)); 
            road.setRoadNumber(3);            
        }
        else if(id == 38){
            road.connectTerrainRoad(terrain.get(8));
            road.connectTerrainRoad(terrain.get(11)); 
            road.setRoadNumber(1);            
        }
        else if(id == 39){
            road.connectTerrainRoad(terrain.get(11));
            road.connectTerrainRoad(terrain.get(18)); 
            road.setRoadNumber(2);            
        }
        else if(id == 40){
            road.connectTerrainRoad(terrain.get(11));
            road.connectTerrainRoad(terrain.get(14)); 
            road.setRoadNumber(4);            
        }
        else if(id == 41){
            road.connectTerrainRoad(terrain.get(14));
            road.connectTerrainRoad(terrain.get(18)); 
            road.setRoadNumber(1);            
        }
        return road;   
    }
    
    private void connectRoadsToTerrains(int id){

        Iterator<Road> itr = roads.iterator();
        while(itr.hasNext()) {
            Road ele = itr.next();
            if(ele.getAdjacentTerrain1().equals(terrain.get(id))){
                terrain.get(id).getAdjacentRoads().add(ele);
            }
            else if(ele.getAdjacentTerrain1().equals(terrain.get(id))){
                terrain.get(id).getAdjacentRoads().add(ele);
            }
            
        
        
        }
    }
    
    private void connectRoadsToRoads(int id){
        
        if(id == 0){
            roads.get(id).getAdjacentRoad().add(roads.get(1));
            roads.get(id).getAdjacentRoad().add(roads.get(29));
        }
        if(id == 1){
            roads.get(id).getAdjacentRoad().add(roads.get(0));
            roads.get(id).getAdjacentRoad().add(roads.get(2));
            roads.get(id).getAdjacentRoad().add(roads.get(3));
            roads.get(id).getAdjacentRoad().add(roads.get(29));
        }
        if(id == 2){
            roads.get(id).getAdjacentRoad().add(roads.get(1));
            roads.get(id).getAdjacentRoad().add(roads.get(3));
        }
        if(id == 3){
            roads.get(id).getAdjacentRoad().add(roads.get(1));
            roads.get(id).getAdjacentRoad().add(roads.get(2));
            roads.get(id).getAdjacentRoad().add(roads.get(4));
            roads.get(id).getAdjacentRoad().add(roads.get(32));
        }
        if(id == 4){
            roads.get(id).getAdjacentRoad().add(roads.get(3));
            roads.get(id).getAdjacentRoad().add(roads.get(5));
            roads.get(id).getAdjacentRoad().add(roads.get(6));
            roads.get(id).getAdjacentRoad().add(roads.get(32));
        }
        if(id == 5){
            roads.get(id).getAdjacentRoad().add(roads.get(4));
            roads.get(id).getAdjacentRoad().add(roads.get(6));
        }
        if(id == 6){
            roads.get(id).getAdjacentRoad().add(roads.get(4));
            roads.get(id).getAdjacentRoad().add(roads.get(5));
            roads.get(id).getAdjacentRoad().add(roads.get(7));
            roads.get(id).getAdjacentRoad().add(roads.get(8));
        }
        if(id == 7){
            roads.get(id).getAdjacentRoad().add(roads.get(6));
            roads.get(id).getAdjacentRoad().add(roads.get(8));
        }
        if(id == 8){
            roads.get(id).getAdjacentRoad().add(roads.get(6));
            roads.get(id).getAdjacentRoad().add(roads.get(7));
            roads.get(id).getAdjacentRoad().add(roads.get(9));
            roads.get(id).getAdjacentRoad().add(roads.get(34));
        }
        if(id == 9){
            roads.get(id).getAdjacentRoad().add(roads.get(9));
            roads.get(id).getAdjacentRoad().add(roads.get(10));
            roads.get(id).getAdjacentRoad().add(roads.get(12));
            roads.get(id).getAdjacentRoad().add(roads.get(13));
        }
        if(id == 10){
            roads.get(id).getAdjacentRoad().add(roads.get(9));
            roads.get(id).getAdjacentRoad().add(roads.get(11));
        }
        if(id == 11){
            roads.get(id).getAdjacentRoad().add(roads.get(9));
            roads.get(id).getAdjacentRoad().add(roads.get(10));
            roads.get(id).getAdjacentRoad().add(roads.get(12));
            roads.get(id).getAdjacentRoad().add(roads.get(13));
        }
        if(id == 12){
            roads.get(id).getAdjacentRoad().add(roads.get(11));
            roads.get(id).getAdjacentRoad().add(roads.get(13));
        }
        if(id == 13){
            roads.get(id).getAdjacentRoad().add(roads.get(11));
            roads.get(id).getAdjacentRoad().add(roads.get(12));
            roads.get(id).getAdjacentRoad().add(roads.get(14));
            roads.get(id).getAdjacentRoad().add(roads.get(36));
        }
        if(id == 14){
            roads.get(id).getAdjacentRoad().add(roads.get(13));
            roads.get(id).getAdjacentRoad().add(roads.get(15));
            roads.get(id).getAdjacentRoad().add(roads.get(16));
            roads.get(id).getAdjacentRoad().add(roads.get(36));
        }
        if(id == 15){
            roads.get(id).getAdjacentRoad().add(roads.get(14));
            roads.get(id).getAdjacentRoad().add(roads.get(16));
        }
        if(id == 16){
            roads.get(id).getAdjacentRoad().add(roads.get(14));
            roads.get(id).getAdjacentRoad().add(roads.get(15));
            roads.get(id).getAdjacentRoad().add(roads.get(17));
            roads.get(id).getAdjacentRoad().add(roads.get(18));
        }
        if(id == 17){
            roads.get(id).getAdjacentRoad().add(roads.get(16));
            roads.get(id).getAdjacentRoad().add(roads.get(18));
        }
        if(id == 18){
            roads.get(id).getAdjacentRoad().add(roads.get(16));
            roads.get(id).getAdjacentRoad().add(roads.get(17));
            roads.get(id).getAdjacentRoad().add(roads.get(19));
            roads.get(id).getAdjacentRoad().add(roads.get(38));
        }
        if(id == 19){
            roads.get(id).getAdjacentRoad().add(roads.get(18));
            roads.get(id).getAdjacentRoad().add(roads.get(20));
            roads.get(id).getAdjacentRoad().add(roads.get(21));
            roads.get(id).getAdjacentRoad().add(roads.get(38));
        }
        if(id == 20){
            roads.get(id).getAdjacentRoad().add(roads.get(19));
            roads.get(id).getAdjacentRoad().add(roads.get(21));
        }
        if(id == 21){
            roads.get(id).getAdjacentRoad().add(roads.get(19));
            roads.get(id).getAdjacentRoad().add(roads.get(20));
            roads.get(id).getAdjacentRoad().add(roads.get(22));
            roads.get(id).getAdjacentRoad().add(roads.get(23));
        }
        if(id == 22){
            roads.get(id).getAdjacentRoad().add(roads.get(21));
            roads.get(id).getAdjacentRoad().add(roads.get(23));
        }
        if(id == 23){
            roads.get(id).getAdjacentRoad().add(roads.get(21));
            roads.get(id).getAdjacentRoad().add(roads.get(22));
            roads.get(id).getAdjacentRoad().add(roads.get(24));
            roads.get(id).getAdjacentRoad().add(roads.get(40));
        }
        if(id == 24){
            roads.get(id).getAdjacentRoad().add(roads.get(23));
            roads.get(id).getAdjacentRoad().add(roads.get(25));
            roads.get(id).getAdjacentRoad().add(roads.get(26));
            roads.get(id).getAdjacentRoad().add(roads.get(40));
        }
        if(id == 25){
            roads.get(id).getAdjacentRoad().add(roads.get(24));
            roads.get(id).getAdjacentRoad().add(roads.get(26));
        }
        if(id == 26){
            roads.get(id).getAdjacentRoad().add(roads.get(24));
            roads.get(id).getAdjacentRoad().add(roads.get(25));
            roads.get(id).getAdjacentRoad().add(roads.get(27));
            roads.get(id).getAdjacentRoad().add(roads.get(28));
        }
        if(id == 27){
            roads.get(id).getAdjacentRoad().add(roads.get(26));
            roads.get(id).getAdjacentRoad().add(roads.get(28));
        }
        if(id == 28){
            roads.get(id).getAdjacentRoad().add(roads.get(26));
            roads.get(id).getAdjacentRoad().add(roads.get(27));
            roads.get(id).getAdjacentRoad().add(roads.get(29));
            roads.get(id).getAdjacentRoad().add(roads.get(30));
        }
        if(id == 29){
            roads.get(id).getAdjacentRoad().add(roads.get(0));
            roads.get(id).getAdjacentRoad().add(roads.get(1));
            roads.get(id).getAdjacentRoad().add(roads.get(28));
            roads.get(id).getAdjacentRoad().add(roads.get(30));
        }
        if(id == 30){
            roads.get(id).getAdjacentRoad().add(roads.get(28));
            roads.get(id).getAdjacentRoad().add(roads.get(29));
            roads.get(id).getAdjacentRoad().add(roads.get(31));
            roads.get(id).getAdjacentRoad().add(roads.get(41));
        }
        if(id == 31){
            roads.get(id).getAdjacentRoad().add(roads.get(30));
            roads.get(id).getAdjacentRoad().add(roads.get(32));
            roads.get(id).getAdjacentRoad().add(roads.get(33));
            roads.get(id).getAdjacentRoad().add(roads.get(41));
        }
        if(id == 32){
            roads.get(id).getAdjacentRoad().add(roads.get(3));
            roads.get(id).getAdjacentRoad().add(roads.get(4));
            roads.get(id).getAdjacentRoad().add(roads.get(31));
            roads.get(id).getAdjacentRoad().add(roads.get(33));
        }
        if(id == 33){
            roads.get(id).getAdjacentRoad().add(roads.get(31));
            roads.get(id).getAdjacentRoad().add(roads.get(32));
            roads.get(id).getAdjacentRoad().add(roads.get(34));
            roads.get(id).getAdjacentRoad().add(roads.get(35));
        }
        if(id == 34){
            roads.get(id).getAdjacentRoad().add(roads.get(8));
            roads.get(id).getAdjacentRoad().add(roads.get(9));
            roads.get(id).getAdjacentRoad().add(roads.get(33));
            roads.get(id).getAdjacentRoad().add(roads.get(35));
        }
        if(id == 35){
            roads.get(id).getAdjacentRoad().add(roads.get(33));
            roads.get(id).getAdjacentRoad().add(roads.get(34));
            roads.get(id).getAdjacentRoad().add(roads.get(36));
            roads.get(id).getAdjacentRoad().add(roads.get(37));
        }
        if(id == 36){
            roads.get(id).getAdjacentRoad().add(roads.get(13));
            roads.get(id).getAdjacentRoad().add(roads.get(14));
            roads.get(id).getAdjacentRoad().add(roads.get(35));
            roads.get(id).getAdjacentRoad().add(roads.get(37));
        }
        if(id == 37){
            roads.get(id).getAdjacentRoad().add(roads.get(35));
            roads.get(id).getAdjacentRoad().add(roads.get(36));
            roads.get(id).getAdjacentRoad().add(roads.get(38));
            roads.get(id).getAdjacentRoad().add(roads.get(39));
        }
        if(id == 38){
            roads.get(id).getAdjacentRoad().add(roads.get(18));
            roads.get(id).getAdjacentRoad().add(roads.get(19));
            roads.get(id).getAdjacentRoad().add(roads.get(37));
            roads.get(id).getAdjacentRoad().add(roads.get(39));
        }
        if(id == 39){
            roads.get(id).getAdjacentRoad().add(roads.get(37));
            roads.get(id).getAdjacentRoad().add(roads.get(38));
            roads.get(id).getAdjacentRoad().add(roads.get(40));
            roads.get(id).getAdjacentRoad().add(roads.get(41));
        }
        if(id == 40){
            roads.get(id).getAdjacentRoad().add(roads.get(23));
            roads.get(id).getAdjacentRoad().add(roads.get(24));
            roads.get(id).getAdjacentRoad().add(roads.get(29));
            roads.get(id).getAdjacentRoad().add(roads.get(41));
        }
        if(id == 41){
            roads.get(id).getAdjacentRoad().add(roads.get(30));
            roads.get(id).getAdjacentRoad().add(roads.get(31));
            roads.get(id).getAdjacentRoad().add(roads.get(39));
            roads.get(id).getAdjacentRoad().add(roads.get(40));
        }
    }
}
