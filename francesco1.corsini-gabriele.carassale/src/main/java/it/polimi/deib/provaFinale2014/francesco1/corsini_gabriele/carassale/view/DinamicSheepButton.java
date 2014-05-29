/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Graphics;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Francesco Corsini
 */
public class DinamicSheepButton extends JButton {

    //private ImageIcon[] icon = new ImageIcon[6];
    private BufferedImage icon;
    private BufferedImage[] sheepImagesSmall = new BufferedImage[6];
    private BufferedImage[] sheepImagesBig = new BufferedImage[6];
    private GUIDinamic GUI;

    private int numSheeps = 0;

    public DinamicSheepButton() {
        for (int i = 0; i <= 5; i++) {
            try {
                //File file = new File(".\\properties\\files\\ListStopWords.txt");
                //URL file = DinamicSheepButton.class.getResource("src\\main\\resources\\sheep_small_0.png");

                //BufferedImage img = ImageIO.read(file);
                //BufferedImage img = ImageIO.read(this.getClass().getResource("src\\main\\resources\\sheep_small_" + i + ".png"));

                sheepImagesSmall[i] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\sheep_small_" + i + ".png"));
                sheepImagesBig[i] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\sheep_big_" + i + ".png"));
            } catch (IOException ex) {
                Logger.getLogger(DinamicSheepButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setBounds(0, 0, 100, 100);

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void mousePressed(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void mouseReleased(MouseEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void mouseEntered(MouseEvent e) {
                icon = sheepImagesBig[numSheeps];
                repaint();
            }

            public void mouseExited(MouseEvent e) {
                icon = sheepImagesSmall[numSheeps];
                repaint();
            }

        });

    }

    public void setnumber(int sheepNumber) {
        if (sheepNumber > 5) {
            sheepNumber = 5;
        }
        icon = sheepImagesSmall[sheepNumber];
        numSheeps = sheepNumber;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
    }

}
