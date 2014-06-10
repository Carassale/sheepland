package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Class for the RoadButton(it will show the shepherds too)
 *
 * @author Francesco Corsini
 */
public class DinamicRoadButton extends JPanel {

    private final GUIDinamic gui;
    private BufferedImage icon;
    private final int road;
    private int idShepard;
    private boolean isShepard;
    private boolean isMouseOver = false;
    private final BufferedImageContainer imagePool;
    //principalmente usato per dei test
    private boolean hasFence;

    /**
     * Constructor
     *
     * @param aThis GUI dynamic
     * @param num which road is it
     * @param imagePool Pool of the images
     */
    DinamicRoadButton(GUIDinamic aThis, int num, BufferedImageContainer imagePool) {
        road = num;
        this.gui = aThis;
        this.imagePool = imagePool;

        hasFence = false;
        isShepard = false;
        icon = imagePool.getTransparent();

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
        
        placeButtons();

        this.addMouseListener(new DinamicRoadButtonListener());
        repaint();
    }

    private void changeSize() {
        if (isMouseOver) {
            this.setSize(80, 80);
        } else {
            this.setSize(50, 50);
        }
        repaint();
    }
    
    private void changeOpacity(boolean opacity){
        if(opacity){
            this.setBackground(new Color(0,0,0,64));

        } else{
            this.setBackground(new Color(0,0,0,100));

        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);

    }

    /**
     * Setter with the id to image
     *
     * @param id is of the shepherd
     */
    public void setShepard(int id) {

        if (id == 0) {
            icon = imagePool.getRedShepard();
        } else if (id == 1) {
            icon = imagePool.getBlueShepard();
        } else if (id == 2) {
            icon = imagePool.getYellowShepard();
        } else if (id == 3) {
            icon = imagePool.getGreenShepard();
        }
        isShepard = true;
        idShepard = id;
        repaint();
    }

    /**
     * Setter to show the fence
     */
    public void setFence() {

        icon = imagePool.getFence();
        isShepard = false;
        idShepard = -1;
        hasFence = true;
        repaint();

    }

    /**
     * Method to ask if there is a shepherd on
     *
     * @return true if there is a shepherd
     */
    public boolean isIsShepard() {
        return isShepard;
    }

    /**
     * Setter to set the presence of a shepherd
     *
     * @param isShepard
     */
    public void setIsShepard(boolean isShepard) {
        this.isShepard = isShepard;
    }

    /**
     * Getter used mainly for testing purpose
     *
     * @return true if it has fence
     */
    public boolean isHasFence() {
        return hasFence;
    }

    /**
     * Setter used mainly for testing purpose
     *
     * @param val val to set
     */
    public void setHasFence(boolean val) {
        hasFence = val;
    }
    
    private void placeButtons(){
        int offsetx = -35;
        int offsety = -55;
        
            if (road == 0) {
                this.setLocation(298 + offsetx, 208 + offsety);
            } else if (road == 1) {
                this.setLocation(344 + offsetx, 257 + offsety);
            } else if (road == 2) {
                this.setLocation(233 + offsetx, 285 + offsety);
            } else if (road == 3) {
                this.setLocation(344 + offsetx, 314 + offsety);
            } else if (road == 4) {
                this.setLocation(344 + offsetx, 379 + offsety);
            } else if (road == 5) {
                this.setLocation(289 + offsetx, 412 + offsety);
            } else if (road == 6) {
                this.setLocation(352 + offsetx, 449 + offsety);
            } else if (road == 7) {
                this.setLocation(299 + offsetx, 556 + offsety);
            } else if (road == 8) {
                this.setLocation(398 + offsetx, 495 + offsety);
            } else if (road == 9) {
                this.setLocation(456 + offsetx, 513 + offsety);
            } else if (road == 10) {
                this.setLocation(398 + offsetx, 610 + offsety);
            } else if (road == 11) {
                this.setLocation(509 + offsetx, 535 + offsety);
            } else if (road == 12) {
                this.setLocation(568 + offsetx, 585 + offsety);
            } else if (road == 13) {
                this.setLocation(566 + offsetx, 509 + offsety);
            } else if (road == 14) {
                this.setLocation(613 + offsetx, 480 + offsety);
            } else if (road == 15) {
                this.setLocation(679 + offsetx, 526 + offsety);
            } else if (road == 16) {
                this.setLocation(669 + offsetx, 439 + offsety);
            } else if (road == 17) {
                this.setLocation(761 + offsetx, 413 + offsety);
            } else if (road == 18) {
                this.setLocation(696 + offsetx, 374 + offsety);
            } else if (road == 19) {
                this.setLocation(698 + offsetx, 320 + offsety);
            } else if (road == 20) {
                this.setLocation(782 + offsetx, 284 + offsety);
            } else if (road == 21) {
                this.setLocation(710 + offsetx, 269 + offsety);
            } else if (road == 22) {
                this.setLocation(734 + offsetx, 203 + offsety);
            } else if (road == 23) {
                this.setLocation(651 + offsetx, 251 + offsety);
            } else if (road == 24) {
                this.setLocation(604 + offsetx, 227 + offsety);
            } else if (road == 25) {
                this.setLocation(634 + offsetx, 151 + offsety);
            } else if (road == 26) {
                this.setLocation(552 + offsetx, 200 + offsety);
            } else if (road == 27) {
                this.setLocation(498 + offsetx, 145 + offsety);
            } else if (road == 28) {
                this.setLocation(491 + offsetx, 211 + offsety);
            } else if (road == 29) {
                this.setLocation(418 + offsetx, 234 + offsety);
            } else if (road == 30) {
                this.setLocation(470 + offsetx, 264 + offsety);
            } else if (road == 31) {
                this.setLocation(445 + offsetx, 324 + offsety);
            } else if (road == 32) {
                this.setLocation(393 + offsetx, 352 + offsety);
            } else if (road == 33) {
                this.setLocation(452 + offsetx, 377 + offsety);
            } else if (road == 34) {
                this.setLocation(461 + offsetx, 445 + offsety);
            } else if (road == 35) {
                this.setLocation(515 + offsetx, 399 + offsety);
            } else if (road == 36) {
                this.setLocation(566 + offsetx, 433 + offsety);
            } else if (road == 37) {
                this.setLocation(568 + offsetx, 374 + offsety);
            } else if (road == 38) {
                this.setLocation(618 + offsetx, 355 + offsety);
            } else if (road == 39) {
                this.setLocation(563 + offsetx, 323 + offsety);
            } else if (road == 40) {
                this.setLocation(584 + offsetx, 274 + offsety);
            } else if (road == 41) {
                this.setLocation(504 + offsetx, 298 + offsety);
            }
    }

    private class DinamicRoadButtonListener implements MouseListener {

        /**
         * Mouse event
         *
         * @param e event
         */
        public void mouseClicked(MouseEvent e) {
            if (gui.getGUIDinamicState() == GUIDinamicState.PLACESHEPARD) {

                if (!isShepard) {
                    gui.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                    gui.sendPlaceShepard(road);
                } else {
                    gui.updateText("C'è un altro pastore su questa Strada! ");
                }

            } else if (gui.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
                if (isShepard) {
                    for (ViewShepard ele : gui.getShepards()) {
                        if (ele.getIsOwned() && ele.getPostition() == road) {
                            gui.updateText("Selezionare strada dove spostarlo");
                            gui.setGUIDinamicState(GUIDinamicState.MOVESHEPARDTO);
                            gui.setTempRoad(road);
                            gui.setTempIdShepard(idShepard);
                        }
                    }
                }
            } else if (gui.getGUIDinamicState() == GUIDinamicState.MOVESHEPARDTO && road != gui.getTempRoad()) {
                if (!isShepard) {
                    gui.sendMoveShepard(road);
                    gui.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                } else {
                    gui.updateText("C'è un altro pastore su questa Strada!");
                }
            }
        }

        /**
         * Mouse event
         *
         * @param e event
         */
        public void mousePressed(MouseEvent e) {
            //not implemented
        }

        /**
         * Mouse event
         *
         * @param e event
         */
        public void mouseReleased(MouseEvent e) {
            //Ã¨ presente ma non utilizzato poichÃ¨ non mi serve ma sto implementando un interfaccia che ha questo metodo
        }

        /**
         * Mouse event
         *
         * @param e event
         */
        public void mouseEntered(MouseEvent e) {
            if (gui.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
                if (isShepard) {
                    for (ViewShepard ele : gui.getShepards()) {
                        if (ele.getIsOwned() && ele.getPostition() == road) {
                            isMouseOver = true;
                            changeSize();
                        }

                    }
                }
                else{
                    changeOpacity(true);
                }
                repaint();
            }
            if (gui.getGUIDinamicState() == GUIDinamicState.PLACESHEPARD || gui.getGUIDinamicState() == GUIDinamicState.MOVESHEPARDTO) {
                
                    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(imagePool.getCursor2(), new Point(0, 0), "Custom cursor2");
                    gui.setCursor(cursor);     
                
            }
        }

        /**
         * Mouse event
         *
         * @param e event
         */
        public void mouseExited(MouseEvent e) {
            if (isShepard) {
                isMouseOver = false;
                changeSize();
            }
            else{
                changeOpacity(false);
            }
            repaint();
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(imagePool.getCursor(), new Point(0, 0), "Custom cursor2");
            gui.setCursor(cursor);  
            
            
        }

    }

}
