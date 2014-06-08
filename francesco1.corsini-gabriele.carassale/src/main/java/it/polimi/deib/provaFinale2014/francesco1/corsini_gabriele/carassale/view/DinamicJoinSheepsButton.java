package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
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
 * Class for the dynamic button of the Join sheep
 *
 * @author Francesco Corsini
 */
public class DinamicJoinSheepsButton extends JPanel {

    private BufferedImage icon;
    
    private BufferedImage[] image = new BufferedImage[2];

    private GUIDinamic GUI;
    private final int terrain;
    private boolean isMouseOver;
    //serve come contatore per ciclare tra le due immagine nel mouseover
    private int cont;
    

    /**
     * Constructor
     *
     * @param gui GUI dynamic
     * @param ter terrain where is placed
     */
    public DinamicJoinSheepsButton(GUIDinamic gui, final int ter, BufferedImageContainer pool) {
        GUI = gui;
        this.terrain = ter;
        
        image[0] = pool.getJoinSheeps1();
        image[1] = pool.getJoinSheeps2();
        
        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
        this.setToolTipText("Accoppia Ovini");
        isMouseOver = false;
        
        icon = image[0];

        this.addMouseListener(new MouseListener() {

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseClicked(MouseEvent e) {

                activateAnimation();
                GUI.sendJoinSheeps(terrain);
                GUI.activateSubMenuSheep(terrain, false);
                GUI.activateSheepType(terrain, false, TypeAnimal.WHITE_SHEEP.toString());
                GUI.activateSheepType(terrain, false, TypeAnimal.RAM.toString());
                GUI.activateSheepType(terrain, false, TypeAnimal.LAMB.toString());
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mousePressed(MouseEvent e) {
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo metodo
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseReleased(MouseEvent e) {
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo metodo
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseEntered(MouseEvent e) {
                isMouseOver = true;
                cont = 1;
                Thread runner = new Thread(new DinamicJoinSheepsButtonCicle());
                runner.start();
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseExited(MouseEvent e) {
                isMouseOver = false;
            }
        });

    }

    private void activateAnimation() {

        GUI.animationJoinSheeps(this.getLocation().x, this.getLocation().y, terrain);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);

    }
    
    private class DinamicJoinSheepsButtonCicle implements Runnable {

        /**
         * Thread chiamata per animazione
         */
        public void run() {
            //cicla tra le due immagini
            while (isMouseOver) {
                icon = image[cont % 2];
                cont++;
                repaint();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DinamicMoveSheepButton.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //quando esco rimetto immagine standard
            icon = image[0];
            repaint();
        }

    }

}
