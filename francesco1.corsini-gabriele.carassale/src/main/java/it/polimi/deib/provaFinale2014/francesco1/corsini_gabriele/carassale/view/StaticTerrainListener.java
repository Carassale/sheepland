/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Francesco Corsini
 */
public class StaticTerrainListener extends JFrame implements ActionListener{

    private GUISwingStatic GUI;
    private int terrain;
    
    
    
    public StaticTerrainListener(GUISwingStatic GUI, int terrain){
        this.GUI = GUI;
        this.terrain = terrain;
    
    }
    
    public void actionPerformed(ActionEvent e) {
        
        GUI.activateTerrains(false);
        String command = e.getActionCommand();
        
        if(GUI.getGUIState() == GUIState.MOVESHEEPFROM){
            GUI.setGUIState((GUIState.MOVESHEEPSELECTION));
            GUI.activateSheepSelection(true,terrain);
            GUI.getLAction2().setText("Seleziona quale ovino si vuole muovere");
            //TODO finire sto metodo deve aggiornare nella GUI tempIdSheep
        }
        else if(GUI.getGUIState() == GUIState.KILLSHEEP){
            GUI.setGUIState((GUIState.KILLSHEEPSELECTION));
            GUI.activateSheepSelection(true,terrain);
            GUI.getLAction2().setText("Seleziona quale ovino vuoi abbatere");
            //TODO finire sto metodo deve aggiornare nella GUI tempIdSheep
        }
        else if(GUI.getGUIState() == GUIState.JOINSHEEPS){
            GUI.setGUIState((GUIState.KILLSHEEPSELECTION));
            GUI.sendJoinSheeps(terrain);
        }
        else if(GUI.getGUIState() == GUIState.MOVESHEEPTO){
            GUI.setGUIState((GUIState.WAITINGFOROTHERPLAYER));
            GUI.sendMoveSheep(terrain);
        }
            
    }

}
