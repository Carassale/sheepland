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
public enum GUIState {

    MOVESHEPARDSELECTION(0),MOVESHEEPFROM(1),KILLSHEEP(2),JOINSHEEPS(3),BUYCARD(4),
    WAITINGFOROTHERPLAYER(5),MOVESHEEPTO(6),MOVESHEEPSELECTION(7),KILLSHEEPSELECTION(8),
    PLACESHEPARD(9),MOVESHEPARDTO(10);
    
    private int index;
    
    private GUIState (int index){
        this.index = index;
    }
 
}
