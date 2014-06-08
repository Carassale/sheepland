package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
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
    //principalmente usato per i test
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
            if (gui.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
                if (isShepard) {
                    for (ViewShepard ele : gui.getShepards()) {
                        if (ele.getIsOwned() && ele.getPostition() == road) {
                            isMouseOver = true;
                            changeSize();
                        }

                    }
                }
                repaint();
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
            repaint();
        }

    }

}
