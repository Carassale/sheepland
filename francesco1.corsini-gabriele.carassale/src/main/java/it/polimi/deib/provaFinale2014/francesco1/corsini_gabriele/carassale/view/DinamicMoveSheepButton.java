/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class DinamicMoveSheepButton extends JPanel {

    private BufferedImage icon;
    private BufferedImage[] image = new BufferedImage[2];
    
    private GUIDinamic GUI;
    private int terrain;
    private DinamicJoinSheepsButton panel;
    private boolean isMouseOver;
    //serve come contatore per ciclare tra le due immagine nel mouseover
    private int cont;

    public DinamicMoveSheepButton(GUIDinamic GUI, int terrain) {

        this.GUI = GUI;
        this.terrain = terrain;
        try {
            image[0] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_1.png"));
            image[1] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_2.png"));
        } catch (IOException ex) {
            Logger.getLogger(DinamicMoveSheepButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
        icon = image[0];
        repaint();
        this.setToolTipText("Muovi Pecora");

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                //activateAnimation(); No perchè è il server che deve chimare refreshMoveSheep()
            }

            public void mousePressed(MouseEvent e) {
                
            }

            public void mouseReleased(MouseEvent e) {
                
            }

            public void mouseEntered(MouseEvent e) {
                isMouseOver = true;
                cont = 1;
                Thread runner = new Thread(new DinamicMoveSheepButtonCicle());
                runner.start();
            }

            public void mouseExited(MouseEvent e) {
                isMouseOver = false;

            }

        });

    }
    
    private void activateAnimation(){
        
        GUI.animationMoveSheep(this.getLocation().x, this.getLocation().y, terrain, cont);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);

    }

    private class DinamicMoveSheepButtonCicle implements Runnable {

        public void run() {
            //cicla tra le due immagini
            while (isMouseOver) {
                icon = image[cont % 2];
                cont++;
                repaint();
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                }

            }

            //quando esco rimetto immagine standard
            icon = image[0];
            repaint();
        }

    }
    

}
