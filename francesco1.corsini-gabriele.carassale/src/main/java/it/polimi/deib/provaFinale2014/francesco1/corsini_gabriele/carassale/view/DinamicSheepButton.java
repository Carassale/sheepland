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
import javax.swing.JButton;

/**
 *
 * @author Francesco Corsini
 */
public class DinamicSheepButton extends JButton {

    private BufferedImage icon;
    private final BufferedImage[] sheepImagesSmall = new BufferedImage[6];
    private GUIDinamic GUI;

    private boolean isBig = false;
    private boolean isInvisible;
    private int numSheeps = 0;

    public DinamicSheepButton(GUIDinamic gui) {
        GUI = gui;
        for (int i = 0; i <= 5; i++) {
            try {
                sheepImagesSmall[i] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\sheep_small_" + i + ".png"));

            } catch (IOException ex) {
                Logger.getLogger(DinamicSheepButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setBounds(0, 0, 100, 100);

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
                if(GUI.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER){
                    isBig = true;
                    repaint();
                }
            }

            public void mouseExited(MouseEvent e) {
                isBig = false;
                repaint();
            }

        });

    }

    public void setnumber(int sheepNumber) {
        if (sheepNumber > 5) {
            sheepNumber = 5;
        }
        if(sheepNumber == 0){
            try {
                icon = ImageIO.read(new File(".\\src\\main\\resources\\transparent.png"));
                isInvisible = true;
            } catch (IOException ex) {
                Logger.getLogger(DinamicSheepButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            icon = sheepImagesSmall[sheepNumber];
            numSheeps = sheepNumber;
            isInvisible = false;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isBig == true) {
            g.drawImage(icon, 0, 0, 80, 80, this);
        } else {
            g.drawImage(icon, 0, 0, 60, 60, this);
        }
    }

    public boolean isIsInvisible() {
        return isInvisible;
    }

    public void setIsInvisible(boolean isInvisible) {
        this.isInvisible = isInvisible;
    }
    
    

}
