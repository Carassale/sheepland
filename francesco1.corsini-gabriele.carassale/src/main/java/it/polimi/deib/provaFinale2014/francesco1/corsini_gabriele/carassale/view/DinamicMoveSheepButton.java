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
 * Class for the MoveSheep Button
 *
 * @author Francesco Corsini
 */
public class DinamicMoveSheepButton extends JPanel {

    private BufferedImage icon;
    private BufferedImage[] image = new BufferedImage[2];

    private GUIDinamic gui;
    private int terrain;
    private boolean isMouseOver;
    //serve come contatore per ciclare tra le due immagine nel mouseover
    private int cont;

    /**
     * Standard Constructor
     *
     * @param guiD GUI Dynamic
     * @param terr terrain where the button is
     */
    public DinamicMoveSheepButton(GUIDinamic guiD, int terr, BufferedImageContainer pool) {

        this.gui = guiD;
        this.terrain = terr;

        image[0] = pool.getMoveSheep1();
        image[1] = pool.getMoveSheep2();

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
        icon = image[0];
        repaint();
        this.setToolTipText("Muovi Pecora");

        this.addMouseListener(new MouseListener() {

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseClicked(MouseEvent e) {
                gui.updateText("Selezionare territorio dove spostare la pecora");
                gui.setGUIDinamicState(GUIDinamicState.MOVESHEEP);
                gui.activateSubMenuSheep(terrain, false);
                gui.activateSheepType(terrain, false, TypeAnimal.WHITE_SHEEP.toString());
                gui.activateSheepType(terrain, false, TypeAnimal.RAM.toString());
                gui.activateSheepType(terrain, false, TypeAnimal.LAMB.toString());
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
                Thread runner = new Thread(new DinamicMoveSheepButtonCicle());
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);

    }

    private class DinamicMoveSheepButtonCicle implements Runnable {

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
