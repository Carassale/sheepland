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
public class GUIDinamicCardListener extends JFrame implements ActionListener{
    
    private GUIDinamic GUI;
    private int number;
    private String type;
    
    public GUIDinamicCardListener(GUIDinamic GUI, int terrain, String type){
        
        this.type = type;
        this.GUI = GUI;
        this.number = terrain;
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
