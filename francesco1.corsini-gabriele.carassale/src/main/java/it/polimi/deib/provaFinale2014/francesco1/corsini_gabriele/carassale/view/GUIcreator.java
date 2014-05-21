/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import javax.swing.*;



/**
 *
 * @author Francesco Corsini
 */
public class GUIcreator{
    
    
    static JFrame mainJFrame; 

    public GUIcreator(){
        mainJFrame = new JFrame("SheepLand");
    }
    
    public void createGUI(){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        createAndShowGUI();
                    }
                });
    }
    
    private void createAndShowGUI(){
        setupWindow();
        
    }
    
    private void setupWindow(){
        
    }
}
