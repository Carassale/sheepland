/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
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
    
    
    public StaticTerrainListener(GUISwingStatic GUI, int terrain, ConnectionClient connectionClient){
        this.GUI = GUI;
        this.terrain = terrain;
    }
    
    public void actionPerformed(ActionEvent e) {
        
        GUI.activateTerrains(false);
        String command = e.getActionCommand();
        
        if(GUI.getGUIState().equals(GUIState.MOVESHEEPFROM)){
            GUI.setGUIState((GUIState.MOVESHEEPSELECTION));
            GUI.activateSheepSelection(true,terrain);
            GUI.getLAction2().setText("Selezione quale ovino si vuole muovere");
        }
        else if(GUI.getGUIState().equals(GUIState.KILLSHEEP)){
            GUI.setGUIState((GUIState.WAITINGFOROTHERPLAYER));
            
        }
        else if(GUI.getGUIState().equals(GUIState.JOINSHEEPS)){
            
        }
        else if(GUI.getGUIState().equals(GUIState.MOVESHEEPTO)){
            
        }
            
    }

}
