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
public class StaticActionListener extends JFrame implements ActionListener{

    private GUISwingStatic GUI;
    
    
    
    public StaticActionListener(GUISwingStatic GUI){
        this.GUI = GUI;
        
    }
    
    public void actionPerformed(ActionEvent e) {
        
        String command = e.getActionCommand();
        GUI.activateActions(false);
        
        if("MoveShepard".equals(command)){
            GUI.getLAction2().setText("Seleziona quale Pastore muovere");
            GUI.setGUIState((GUIState.MOVESHEPARDSELECTION));
            GUI.activateShepardSelection(true);
            GUI.activateRoads(true);
        }
        else if("MoveSheep".equals(command)){
            GUI.getLAction2().setText("Selezione da quale territorio si vuole muovere l'ovino");
            GUI.setGUIState((GUIState.MOVESHEEPFROM));
            GUI.activateTerrains(true);
            
            
        }
        else if("BuyCard".equals(command)){
            GUI.getLAction2().setText("Selezione la tipologia di carta da comprare");
            GUI.setGUIState((GUIState.BUYCARD));
            GUI.activateTerrainType(true);
        }
        else if("JoinSheeps".equals(command)){
            GUI.getLAction2().setText("Selezione il terreno dove accoppiare");
            GUI.activateTerrains(true);
            GUI.setGUIState((GUIState.JOINSHEEPS));
            
        }
        else if("KillSheep".equals(command)){
            GUI.getLAction2().setText("Selezione il territorio dove è l'ovino da abbattere");
            GUI.setGUIState((GUIState.KILLSHEEP));
            GUI.activateTerrains(true);
            
        }
    }

}
