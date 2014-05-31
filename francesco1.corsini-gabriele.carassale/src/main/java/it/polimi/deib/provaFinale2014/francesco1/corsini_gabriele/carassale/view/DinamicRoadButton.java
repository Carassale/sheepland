/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.swing.JPanel;

/**
 *
 * @author Francesco Corsini
 */
public class DinamicRoadButton extends JPanel {

    private final GUIDinamic GUI;
    private BufferedImage icon;
    private BufferedImage fence, redShepard, greenShepard, blueShepard, yellowShepard;
    private final int road;
    private int idShepard;
    private boolean isShepard;
    private boolean isMouseOver = false;
    private final BufferedImageContainer imagePool;

    DinamicRoadButton(GUIDinamic aThis, int num, BufferedImageContainer imagePool) {
        road = num;
        this.GUI = aThis;
        this.imagePool = imagePool;

        isShepard = false;
        icon = imagePool.getTransparent();

        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                if (GUI.getGUIDinamicState() == GUIDinamicState.PLACESHEPARD) {

                    if (isShepard == false) {
                        GUI.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                        GUI.sendPlaceShepard(road);
                    } else {
                        GUI.updateText("C'è un altro pastore su questa Strada!");
                    }

                } else if (GUI.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
                    if (isShepard) {
                        for (ViewShepard ele : GUI.getShepards()) {
                            if (ele.getIsOwned()) {
                                GUI.updateText("Selezionare strada dove spostarlo");
                                GUI.setGUIDinamicState(GUIDinamicState.MOVESHEPARDTO);
                                GUI.setTempRoad(road);
                                GUI.setTempIdShepard(idShepard);
                            }
                        }
                    }
                } else if (GUI.getGUIDinamicState() == GUIDinamicState.MOVESHEPARDTO) {
                    if (road != GUI.getTempRoad()) {
                        if (isShepard == false) {
                            GUI.sendMoveShepard(road);
                            GUI.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
                        } else {
                            GUI.updateText("C'è un altro pastore su questa Strada!");
                        }
                    }
                }
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                if (GUI.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
                    if (isShepard) {
                        for (ViewShepard ele : GUI.getShepards()) {

                                if(ele.getIsOwned()){
                                isMouseOver = true;
                                changeSize();
                                }
                            
                        }
                    }
                    repaint();
                }
            }

            public void mouseExited(MouseEvent e) {
                if (isShepard) {
                    isMouseOver = false;
                    changeSize();
                }
                repaint();
            }

        });
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

    public void setFence() {

        icon = imagePool.getFence();
        isShepard = false;
        idShepard = -1;
        repaint();

    }

    public boolean isIsShepard() {
        return isShepard;
    }

    public void setIsShepard(boolean isShepard) {
        this.isShepard = isShepard;
    }

}
