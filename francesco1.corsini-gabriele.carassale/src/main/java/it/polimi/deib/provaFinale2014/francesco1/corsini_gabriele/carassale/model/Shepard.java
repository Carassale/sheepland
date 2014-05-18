package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.Iterator;


/**
 * Classe Pastore che poi sarà assegnato ad ogni giocatore
 * @author Francesco Corsini
 */
public class Shepard {

    private Road position;

    /**
     * inizializza lo shepard e lo posiziona sulla plancia
     * @param road posizione
     */
    public Shepard (Road road) {
        position = road;
        position.setHasShepard(true);
        position.setShepard(this);
    }

    public Road getPosition () {
        return position;
    }

    
    /**
     * Muove il pastore verso una nuova posizione. Piazza la fence cambiando posizione
     * @param destination dove verrà messo
     */
    public void setPosition (Road destination) {
        position.setHasShepard(false);
        position.setShepard(null);
        position = destination;
        position.setHasShepard(true);
        position.setShepard(this);
    }
    
    //ritorna true se la mossa che si sta per compiere costa un denaro. Devo vederlo dal Player
    /**
     * ritorna true se la mossa che si sta per compiere costa un denaro. Serve al Player per capire se può fare la mossa
     * @param destination strada dove andare
     * @return true se costa 1 denaro, false altrimenti
     */
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

