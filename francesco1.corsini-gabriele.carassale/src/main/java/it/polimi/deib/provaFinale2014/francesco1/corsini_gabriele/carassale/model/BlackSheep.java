package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.Iterator;



public class BlackSheep extends Animal {


    public BlackSheep () {
    }

    //lancia il dado e controlla se c'è una strada confinante con quel valore
    public void hasToMove () {
        Dice dice = new Dice();
        int movement = dice.getRandom();
        boolean hasRoad = false;
        
        Iterator<Road> itr = position.getAdjacentRoads().iterator();
        while(itr.hasNext()) {
         Road element = itr.next();
         if(element.getRoadNumber() == movement)
             move(element);
        }

    }
    
    //sposta realmente la pecoraNera nel territorio connesso dalla strada sorteggiata che non è il territorio in cui è ora
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



