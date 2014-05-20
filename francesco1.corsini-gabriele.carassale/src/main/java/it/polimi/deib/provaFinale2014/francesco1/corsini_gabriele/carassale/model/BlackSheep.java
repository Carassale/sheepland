package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.WrongDiceNumberException;
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
     * Metodo che controlla se bisogna muoversi, viene invocato dal Turn
     * @param num numero uscito dal lancio del dado
     * @return Road la ritorna se ne trova una con il num sopra
     * @throws WrongDiceNumberException se non esiste strada con num sopra
     */
    public Road hasToMove (int num) throws WrongDiceNumberException{
        int movement = num;
        boolean hasRoad = false;
        
        
        Iterator<Road> itr = position.getAdjacentRoads().iterator();
        while(itr.hasNext()) {
         Road element = itr.next();
         if(element.getRoadNumber() == movement && element.hasFence() == false)
             return element;
        }
        throw new WrongDiceNumberException(num);
    }
    
    /**
     * Metodo che muove la pecora alla destinazione
     * @param moveToRoad è la strada dove deve andare
     */
    public void move(Road moveToRoad) {
        Terrain terrain1 = moveToRoad.getAdjacentTerrain1();
        Terrain terrain2 = moveToRoad.getAdjacentTerrain2();
        
        if(position == terrain1){
            position.deleteAnimal(this);
            position = terrain2;
            position.addAnimal(this);            
        }
        else if(position == terrain2) {
            position.deleteAnimal(this);
            position = terrain1;
            position.addAnimal(this);  
        }    
    }
    
}



