package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.Iterator;


/**
 * Classe Pecora Nera
 * @author Francesco Corsini
 */
public class BlackSheep extends Animal {


    public BlackSheep (Terrain terr) {
        position = terr;
        position.addAnimal(this);
    }

    /**
     * metodo che lancia un dado e controlla se bisogna muoversi, nel caso poi chiama il move()
     */
    public void hasToMove () {
        Dice dice = new Dice();
        int movement = dice.getRandom();
        boolean hasRoad = false;
        
        Iterator<Road> itr = position.getAdjacentRoads().iterator();
        while(itr.hasNext()) {
         Road element = itr.next();
         if(element.getRoadNumber() == movement && element.hasFence() == false)
             move(element);
        }

    }
    
    /**
     * metodo che muove la pecora alla destinazione uscita dal tiro dado
     * @param moveToRoad è la strada di cui è uscito il valore dal dato
     */
    private void move(Road moveToRoad) {
        Terrain terrain1 = moveToRoad.getAdjacentTerrain1();
        Terrain terrain2 = moveToRoad.getAdjacentTerrain2();
        
        if(position == terrain1){
            position.deleteAnimal(this);
            position = terrain2;
            position.addAnimal(this);            
        }
        else if(position == terrain2)
            position.deleteAnimal(this);
            position = terrain1;
            position.addAnimal(this);  
    }
    
}



