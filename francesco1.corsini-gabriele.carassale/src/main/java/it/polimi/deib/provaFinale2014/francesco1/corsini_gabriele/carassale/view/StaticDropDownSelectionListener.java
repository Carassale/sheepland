/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 *
 * @author Francesco Corsini
 */
public class StaticDropDownSelectionListener extends JFrame implements ActionListener {
    
    private GUISwingStatic GUI;    
    
    public StaticDropDownSelectionListener(GUISwingStatic GUI) {
        this.GUI = GUI;
        
    }
    
    public void actionPerformed(ActionEvent e) {
        
        GUI.activateDropDown(false);
        JComboBox cb = (JComboBox) e.getSource();
        String command = (String) cb.getSelectedItem();
        
        if (GUI.getGUIState() == GUIState.MOVESHEEPSELECTION) {
            GUI.setGUIState((GUIState.MOVESHEEPTO));
            GUI.activateTerrains(true);
            GUI.getLAction2().setText("Dove si vuole muovere l'Ovino");
            
            command = command.replaceAll("[^\\d.]", "");
            int num = Integer.parseInt(command);
            GUI.setTempIdSheep(num);
        }
        else if (GUI.getGUIState() == GUIState.MOVESHEPARDSELECTION) {
            GUI.setGUIState((GUIState.MOVESHEPARDTO));
            GUI.activateRoads(true);
            GUI.getLAction2().setText("Dove si vuole spostare il Pastore");
            
            command = command.replaceAll("[^\\d.]", "");
            int num = Integer.parseInt(command);
            GUI.setTempIdShepard(num);
        }

    }
    
}
