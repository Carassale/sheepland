/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Francesco Corsini
 */
public class GUIDinamicPanel extends JPanel {

    private BufferedImage image;

    public GUIDinamicPanel(String string) throws IOException {
        super();
        image = ImageIO.read(new File(string));
        setSize(image.getWidth(), image.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        

    }

    
}
