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
import javax.swing.JButton;

/**
 * Class for the SheepButton in the middle of the terrain
 * @author Francesco Corsini
 */
public class DinamicSheepButton extends JButton {

    private BufferedImage icon;
    private final BufferedImage[] sheepImagesSmall = new BufferedImage[6];
    private GUIDinamic GUI;

    private boolean isBig = false;
    private boolean isInvisible;
    private int terrain;

    /**
     * Constructor
     * @param gui GUI Dynamic
     * @param terr territory where to be placed
     */
    public DinamicSheepButton(GUIDinamic gui, int terr) {
        GUI = gui;
        this.terrain = terr;
        for (int i = 0; i <= 5; i++) {
            try {
                sheepImagesSmall[i] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\sheep_small_" + i + ".png"));

            } catch (IOException ex) {
                Logger.getLogger(DinamicSheepButton.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setBounds(0, 0, 100, 100);

        this.addMouseListener(new DinamicSheepButtonListener());
    }

    /**
     * Method that refresh the icon displaying the correct number of sheeps
     * @param sheepNumber new number of sheeps
     */
    public void setnumber(int sheepNumber) {
        if (sheepNumber > 5) {
            sheepNumber = 5;
        }
        if (sheepNumber == 0) {
            try {
                icon = ImageIO.read(new File(".\\src\\main\\resources\\transparent.png"));
                isInvisible = true;
            } catch (IOException ex) {
                Logger.getLogger(DinamicSheepButton.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            icon = sheepImagesSmall[sheepNumber];
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
     * @return true if yes
     */
    public boolean isIsInvisible() {
        return isInvisible;
    }
    
    private class DinamicSheepButtonListener implements MouseListener{
        
            /**
             * Mouse event
             * @param e event
             */
            public void mouseClicked(MouseEvent e) {

                if (GUI.getGUIDinamicState() == GUIDinamicState.MOVESHEEP) {
                    GUI.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                    GUI.sendMoveSheep(terrain);
                } else if (GUI.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
                    GUI.activateSheepTypeButton(terrain);
                    GUI.setSubMenuOpen(terrain);
                    GUI.setGUIDinamicState(GUIDinamicState.SUBMENUOPEN);
                } else if (GUI.getGUIDinamicState() == GUIDinamicState.SUBMENUOPEN) {
                    int i = GUI.getSubMenuOpen();
                    if (i == terrain) {
                        GUI.activateSubMenuSheep(terrain, false);
                        GUI.activateSheepType(terrain, false, TypeAnimal.WHITE_SHEEP.toString());
                        GUI.activateSheepType(terrain, false, TypeAnimal.RAM.toString());
                        GUI.activateSheepType(terrain, false, TypeAnimal.LAMB.toString());
                    } else {
                        GUI.activateSubMenuSheep(i, false);
                        GUI.activateSheepType(i, false, TypeAnimal.WHITE_SHEEP.toString());
                        GUI.activateSheepType(i, false, TypeAnimal.RAM.toString());
                        GUI.activateSheepType(i, false, TypeAnimal.LAMB.toString());
                        GUI.setSubMenuOpen(terrain);
                    }
                    GUI.setGUIDinamicState(GUIDinamicState.WAITINGFORPLAYER);
                } else if (GUI.getGUIDinamicState() == GUIDinamicState.MOVESHEEP) {
                    GUI.activateSubMenuSheep(terrain, false);
                    GUI.activateSheepType(terrain, false, TypeAnimal.WHITE_SHEEP.toString());
                    GUI.activateSheepType(terrain, false, TypeAnimal.RAM.toString());
                    GUI.activateSheepType(terrain, false, TypeAnimal.LAMB.toString());
                    GUI.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                    GUI.sendMoveSheep(terrain);
                    //TODO GUI.animation
                }
            }

            /**
             * Mouse event
             * @param e event
             */
            public void mousePressed(MouseEvent e) {
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo metodo
            }

            /**
             * Mouse event
             * @param e event
             */
            public void mouseReleased(MouseEvent e) {
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo metodo
            }

            /**
             * Mouse event
             * @param e event
             */
            public void mouseEntered(MouseEvent e) {
                if (GUI.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER || 
                        GUI.getGUIDinamicState() == GUIDinamicState.SUBMENUOPEN ||
                        GUI.getGUIDinamicState() == GUIDinamicState.MOVESHEEP) {
                    isBig = true;
                    repaint();
                }
            }

            /**
             * Mouse event
             * @param e event
             */
            public void mouseExited(MouseEvent e) {
                isBig = false;
                repaint();
            }

        }
    


}
