/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 *
 * @author Francesco Corsini
 */
public enum GUIDinamicState {

    SUBMENUOPEN(0),WAITINGFORPLAYER(1);
    
    private int index;
    
    private GUIDinamicState (int index){
        this.index = index;
    }
 
}