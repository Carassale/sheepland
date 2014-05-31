/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Francesco Corsini
 */
public class BufferedImageContainer {

    private BufferedImage fence, redShepard, greenShepard, blueShepard, yellowShepard,transparent;

    public BufferedImageContainer() {
        
    }

    public BufferedImage getFence() {
        try {
            fence = ImageIO.read(new File(".\\src\\main\\resources\\fence.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fence;
    }

    public BufferedImage getRedShepard() {
        try {
            redShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardRedSmall.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return redShepard;
    }

    public BufferedImage getGreenShepard() {
        try {
            greenShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardGreenSmall.png"));
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return greenShepard;
    }

    public BufferedImage getBlueShepard() {
        try {
            blueShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardBlueSmall.png"));
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blueShepard;
    }

    public BufferedImage getYellowShepard() {
        try {
            yellowShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardYellowSmall.png"));
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return yellowShepard;
    }

    public BufferedImage getTransparent() {
        try {
            transparent = ImageIO.read(new File(".\\src\\main\\resources\\transparent.png"));
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transparent;
    }
    
    
    
    
}
