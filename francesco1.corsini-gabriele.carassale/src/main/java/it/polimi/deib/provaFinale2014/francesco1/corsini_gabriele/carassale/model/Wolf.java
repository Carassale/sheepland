package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.Iterator;

 /**
  * Modello del Lupo
  * @author Francesco Corsini
  */
public class Wolf extends Animal {

 
    public Wolf (Terrain terrain) {
        position = terrain;
        position.addAnimal(this);
    }
    
    /**
     * prima controlla se tutte le strade sono chiuse da cancello, poi controlla se c'è una strada uguale al tiro dado. Esegue poi il move()
     */
    public Road hasToMove (int num) throws NullPointerException{

        int movement = num;
        boolean hasRoad = false;
        boolean allFence = true;
        
        //controlla se tutte le strade sono chiuse da cancello
        Iterator<Road> itr = position.getAdjacentRoads().iterator();
        while(itr.hasNext()) {
         Road element = itr.next();
         if(element.hasFence() == false)
             allFence = false;
        }
        
        //va nella strada del tiro dado se non c'è la Fence, se c'è la Fence ci va solo se tutte le strade sono chiuse da Fence
        Iterator<Road> itr2 = position.getAdjacentRoads().iterator();
        while(itr2.hasNext()) {
         Road element = itr2.next();
         if(element.getRoadNumber() == movement && element.hasFence() == false)
             return element;
         if(element.getRoadNumber() == movement && allFence == true)
             return element;
        }
        throw new NullPointerException();
    }
    
    /**
     * muove realmente il lupo
     * @param roadToMove strada dove si deve muovere
     */
    public void move (Road roadToMove) {
        Terrain terrain1 = roadToMove.getAdjacentTerrain1();
        Terrain terrain2 = roadToMove.getAdjacentTerrain2();
        
        if(position == terrain1){
            position.deleteAnimal(this);
            position = terrain2;
            position.addAnimal(this);            
        }
        else if(position == terrain2){
            position.deleteAnimal(this);
            position = terrain1;
            position.addAnimal(this);
        }
    }
    
}

