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
public class GUIDinamicSheepSubMenuListener extends JFrame implements ActionListener{

    private GUIDinamic GUI;
    private int terrain;
    private String type;
    
    
    public GUIDinamicSheepSubMenuListener(GUIDinamic GUI, int terrain, String type){
        this.GUI = GUI;
        this.terrain = terrain;
        this.type = type;
    }
    
    public void actionPerformed(ActionEvent e) {
        GUI.activateSubMenuSheep(terrain, false);
        //in modo che nessun submenu sia aperto
        GUI.setSubMenuOpen(-1);
        
        if("MoveSheep".equals(type)){
            
        }
        else if("KillSheep".equals(type)){
            
        }
        else if("JoinSheeps".equals(type)){
            GUI.joinSheeps(terrain);
        }
    }

}
