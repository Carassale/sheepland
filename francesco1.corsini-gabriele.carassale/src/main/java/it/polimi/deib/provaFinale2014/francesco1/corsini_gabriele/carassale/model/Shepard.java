package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.Iterator;



public class Shepard {

    private Road position;

    public Shepard (Road road) {
        position = road;
        position.setShepard(true);
    }

    public Road getPosition () {
        return position;
    }

    //Muove il pastore verso una nuova posizione. Piazza la fence cambiando posizione
    public void setPosition (Road destination) {
        position.setFence(true);
        position.setShepard(false);
        destination.setShepard(true);
    }
    
    //ritorna true se la mossa che si sta per compiere costa un denaro. Devo vederlo dal Player
    public boolean  isExpensiveMove( Road destination ){
        
        Iterator<Road> itr = position.getAdjacentRoad().iterator();
        while(itr.hasNext()) {
            Road ele = itr.next();
            if(ele.equals(destination))
                return false;            
        }
        return true;
    }

}

