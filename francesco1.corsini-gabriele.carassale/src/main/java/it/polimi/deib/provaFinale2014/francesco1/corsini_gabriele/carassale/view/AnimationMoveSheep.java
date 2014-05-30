/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Francesco Corsini
 */
public class AnimationMoveSheep extends JPanel implements Runnable{

    private BufferedImage icon;
    private BufferedImage[] image = new BufferedImage[2];
    
    public AnimationMoveSheep(int cont) {
        try {
            image[0] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_1.png"));
            image[1] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_2.png"));
        } catch (IOException ex) {
            Logger.getLogger(DinamicMoveSheepButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
        icon = image[cont];
        repaint();
        
    }

    
    
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
    }

}
