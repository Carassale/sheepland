package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
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
import javax.swing.JButton;

/**
 * Class for the SheepButton in the middle of the terrain
 *
 * @author Francesco Corsini
 */
public class DinamicSheepButton extends JButton {

    private BufferedImage icon;
    private final BufferedImage[] sheepImagesSmall = new BufferedImage[6];
    private GUIDinamic gui;

    private boolean isBig = false;
    private boolean isInvisible;
    private int terrain;

    /**
     * Constructor
     *
     * @param gui GUI Dynamic
     * @param terr territory where to be placed
     */
    public DinamicSheepButton(GUIDinamic gui, int terr) {
        this.gui = gui;
        this.terrain = terr;
        for (int t = 0; t <= 5; t++) {
            try {
                sheepImagesSmall[t] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\sheep_small_" + t + ".png"));

            } catch (IOException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setBounds(0, 0, 100, 100);

        placeButton();

        this.addMouseListener(new DinamicSheepButtonListener());
    }

    /**
     * Method that refresh the icon displaying the correct number of sheeps
     *
     * @param sheepNumber new number of sheeps
     */
    public void setnumber(int sheepNumber) {
        if (sheepNumber == 0) {
            try {
                icon = ImageIO.read(new File(".\\src\\main\\resources\\transparent.png"));
                isInvisible = true;
            } catch (IOException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            if (sheepNumber > 5) {
                icon = sheepImagesSmall[5];
            } else {
                icon = sheepImagesSmall[sheepNumber];
            }
            isInvisible = false;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isBig) {
            g.drawImage(icon, 0, 0, 80, 80, this);
        } else {
            g.drawImage(icon, 0, 0, 60, 60, this);
        }
    }

    /**
     * Method to understand if there are no sheeps on the terrain
     *
     * @return true if yes
     */
    public boolean isIsInvisible() {
        return isInvisible;
    }

    private void placeButton() {
        // la prima è la x, la seconda è la y(parte dall'alto)
        int offset = 20;
        if (terrain == 0) {
            this.setLocation(165 + offset, 110 + offset);
        } else if (terrain == 1) {
            this.setLocation(220 + offset, 280 + offset);
        } else if (terrain == 2) {
            this.setLocation(340 + offset, 350 + offset);
        } else if (terrain == 3) {
            this.setLocation(220 + offset, 410 + offset);
        } else if (terrain == 4) {
            this.setLocation(320 + offset, 460 + offset);
        } else if (terrain == 5) {
            this.setLocation(450 + offset, 380 + offset);
        } else if (terrain == 6) {
            this.setLocation(440 + offset, 500 + offset);
        } else if (terrain == 7) {
            this.setLocation(570 + offset, 460 + offset);
        } else if (terrain == 8) {
            this.setLocation(570 + offset, 330 + offset);
        } else if (terrain == 9) {
            this.setLocation(660 + offset, 380 + offset);
        } else if (terrain == 10) {
            this.setLocation(690 + offset, 270 + offset);
        } else if (terrain == 11) {
            this.setLocation(585 + offset, 220 + offset);
        } else if (terrain == 12) {
            this.setLocation(690 + offset, 165 + offset);
        } else if (terrain == 13) {
            this.setLocation(600 + offset, 120 + offset);
        } else if (terrain == 14) {
            this.setLocation(465 + offset, 165 + offset);
        } else if (terrain == 15) {
            this.setLocation(500 + offset, 60 + offset);
        } else if (terrain == 16) {
            this.setLocation(330 + offset, 100 + offset);
        } else if (terrain == 17) {
            this.setLocation(340 + offset, 200 + offset);
        } else if (terrain == 18) {
            this.setLocation(450 + offset, 270 + offset);
        }
    }

    private class DinamicSheepButtonListener implements MouseListener {

        /**
         * Mouse event
         *
         * @param e event
         */
        public void mouseClicked(MouseEvent e) {

            if (gui.getGUIDinamicState() == GUIDinamicState.MOVESHEEP) {
                gui.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                gui.sendMoveSheep(terrain);
            } else if (gui.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
                gui.activateSheepTypeButton(terrain);
                gui.setSubMenuOpen(terrain);
                gui.setGUIDinamicState(GUIDinamicState.SUBMENUOPEN);
            } else if (gui.getGUIDinamicState() == GUIDinamicState.SUBMENUOPEN) {
                int terr = gui.getSubMenuOpen();
                if (terr == terr) {
                    gui.activateSubMenuSheep(terr, false);
                    gui.activateSheepType(terr, false, TypeAnimal.WHITE_SHEEP.toString());
                    gui.activateSheepType(terr, false, TypeAnimal.RAM.toString());
                    gui.activateSheepType(terr, false, TypeAnimal.LAMB.toString());
                } else {
                    gui.activateSubMenuSheep(terr, false);
                    gui.activateSheepType(terr, false, TypeAnimal.WHITE_SHEEP.toString());
                    gui.activateSheepType(terr, false, TypeAnimal.RAM.toString());
                    gui.activateSheepType(terr, false, TypeAnimal.LAMB.toString());
                    gui.setSubMenuOpen(terr);
                }
                gui.setGUIDinamicState(GUIDinamicState.WAITINGFORPLAYER);
            } else if (gui.getGUIDinamicState() == GUIDinamicState.MOVESHEEP) {
                gui.activateSubMenuSheep(terrain, false);
                gui.activateSheepType(terrain, false, TypeAnimal.WHITE_SHEEP.toString());
                gui.activateSheepType(terrain, false, TypeAnimal.RAM.toString());
                gui.activateSheepType(terrain, false, TypeAnimal.LAMB.toString());
                gui.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                gui.sendMoveSheep(terrain);
            }
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
            if (gui.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER
                    || gui.getGUIDinamicState() == GUIDinamicState.SUBMENUOPEN
                    || gui.getGUIDinamicState() == GUIDinamicState.MOVESHEEP) {
                isBig = true;
                repaint();
            }
        }

        /**
         * Mouse event
         *
         * @param e event
         */
        public void mouseExited(MouseEvent e) {
            isBig = false;
            repaint();
        }

    }

}
