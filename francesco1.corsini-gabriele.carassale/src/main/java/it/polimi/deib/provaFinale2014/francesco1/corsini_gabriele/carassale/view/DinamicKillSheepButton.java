package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
;
import javax.swing.JPanel;

/**
 * Class for the KillSheep Button
 *
 * @author Francesco Corsini
 */


public class DinamicKillSheepButton extends JPanel {

    private BufferedImage icon;
    private BufferedImage[] image = new BufferedImage[2];
    private GUIDinamic gui;
    private final int terrain;
    private boolean isMouseOver;
    //serve come contatore per ciclare tra le due immagine nel mouseover
    private int cont;

    /**
     * Standard Constructor
     *
     * @param aThis GUI Dynamic
     * @param i the terrain where the button is
     */
    DinamicKillSheepButton(GUIDinamic aThis, int i, BufferedImageContainer pool) {
        this.terrain = i;
        this.gui = aThis;
        isMouseOver = false;

        image[0] = pool.getKillSheep1();
        image[1] = pool.getKillSheep2();

        icon = image[0];
        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
        this.setToolTipText("Abbatti Ovino");

        this.addMouseListener(new MouseListener() {

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseClicked(MouseEvent e) {

                activateAnimation();
                gui.sendKillSheep();
                gui.activateSubMenuSheep(terrain, false);
                gui.activateSheepType(terrain, false, TypeAnimal.WHITE_SHEEP.toString());
                gui.activateSheepType(terrain, false, TypeAnimal.RAM.toString());
                gui.activateSheepType(terrain, false, TypeAnimal.LAMB.toString());
                gui.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mousePressed(MouseEvent e) {
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo method
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseReleased(MouseEvent e) {
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo method
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseEntered(MouseEvent e) {
                isMouseOver = true;
                cont = 1;
                Thread runner = new Thread(new DinamicKillSHeepButtonCicle());
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
        gui.animationKillSheep(this.getLocation().x, this.getLocation().y, terrain);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);

    }

    private class DinamicKillSHeepButtonCicle implements Runnable {

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
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }

            }

            //quando esco rimetto immagine standard
            icon = image[0];
            repaint();
        }

    }

}
