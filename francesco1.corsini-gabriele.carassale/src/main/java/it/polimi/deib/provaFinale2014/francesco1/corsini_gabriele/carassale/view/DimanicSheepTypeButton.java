/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Francesco Corsini
 */
public class DimanicSheepTypeButton extends JPanel{
    
    private int terrain;
    private GUIDinamic GUI;
    private BufferedImage icon; 
    private BufferedImageContainer imagePool;
    

    public DimanicSheepTypeButton(GUIDinamic gui, int terr, String type, BufferedImageContainer im) {
        terrain = terr;
        GUI = gui;
        imagePool = im;
        
        if("WhiteSheep".equals(type)){
            icon = imagePool.getWhiteSheep();
        } else if("Lamb".equals(type)){
            icon = imagePool.getLamb();
        }else {
            icon = imagePool.getRam();
        }
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
        
    }
    
    

}
