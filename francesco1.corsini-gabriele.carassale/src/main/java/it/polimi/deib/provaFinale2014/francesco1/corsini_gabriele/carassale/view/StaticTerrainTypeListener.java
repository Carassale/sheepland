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
public class StaticTerrainTypeListener extends JFrame implements ActionListener{
    
    private GUISwingStatic GUI;
    
    
    
    public StaticTerrainTypeListener(GUISwingStatic GUI){
        this.GUI = GUI;
        
    }

    public void actionPerformed(ActionEvent e) {
     
        String command = e.getActionCommand();
        GUI.activateTerrainType(false);
        
        if(GUI.getGUIState() == GUIState.BUYCARD){
            GUI.sendBuyCard(command);
        }
        
    }

}
