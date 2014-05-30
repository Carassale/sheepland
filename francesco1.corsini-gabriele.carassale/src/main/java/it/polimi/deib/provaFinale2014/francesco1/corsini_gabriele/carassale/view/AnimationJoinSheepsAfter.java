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
public class AnimationJoinSheepsAfter extends JPanel implements Runnable {

    private BufferedImage icon, icon2, icon3;

    private Thread runner;
    private boolean isSuccesfull;

    public AnimationJoinSheepsAfter(boolean isSuccesfull) {

        this.isSuccesfull = isSuccesfull;
        try {
            icon = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\heart.png"));
            icon2 = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\lambPlus1.png"));
            icon3 = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\heartbroken.png"));
        } catch (IOException ex) {
            Logger.getLogger(AnimationJoinSheepsAfter.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        this.setSize(300, 300);

        repaint();
        runner = new Thread(this);

    }

    public void run() {
        try {
            Thread.sleep(1500);

            if (isSuccesfull) {
                icon = icon2;
                repaint();
            } else {
                icon = icon3;
                repaint();
            }
            Thread.sleep(1500);
        } catch (Exception e) {
        }
        this.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
    }

    public Thread getRunner() {
        return runner;
    }

    public void setRunner(Thread runner) {
        this.runner = runner;
    }
}
