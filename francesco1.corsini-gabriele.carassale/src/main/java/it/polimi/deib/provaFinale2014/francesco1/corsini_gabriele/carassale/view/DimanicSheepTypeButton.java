/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Class for the buttons representing the types of the sheeps(lamb,ram,white sheep)
 * @author Francesco Corsini
 */
public class DimanicSheepTypeButton extends JPanel {

    private int terrain;
    private GUIDinamic GUI;
    private final BufferedImage icon;
    private final BufferedImageContainer imagePool;
    private final String type;
    private boolean isVisible;
    private ViewAnimal selectedAnimal;

    /**
     * Constructor
     * @param aThis GUI dinamic
     * @param terr terrain where the button has to be created
     * @param t type of the button
     * @param pool imagePool
     */
    public DimanicSheepTypeButton(GUIDinamic aThis, int terr, String t, BufferedImageContainer pool) {
        terrain = terr;
        GUI = aThis;
        imagePool = pool;
        type = t;

        if (TypeAnimal.WHITE_SHEEP.toString().equals(type)) {
            icon = imagePool.getWhiteSheep();
            this.setToolTipText("Pecora");
        } else if (TypeAnimal.LAMB.toString().equals(type)) {
            icon = imagePool.getLamb();
            this.setToolTipText("Agnello");
        } else {
            icon = imagePool.getRam();
            this.setToolTipText("Montone");
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);

        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                GUI.setSheepSelected(selectedAnimal);
                GUI.activateSubMenuSheep(terrain, true);
                GUI.setSubMenuOpen(terrain);
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });
        repaint();
    }

    /**
     * Method to calculate the types of the sheeps present on the terrain
     */
    public void activateTypeButton() {
        for (ViewAnimal ele : GUI.getAnimals()) {
            if (TypeAnimal.WHITE_SHEEP.toString().equals(type)) {
                if (ele.getPosition() == terrain) {
                    if (ele.getType().equals(TypeAnimal.WHITE_SHEEP.toString())) {
                        selectedAnimal = ele;
                        GUI.activateSheepType(terrain, true, TypeAnimal.WHITE_SHEEP.toString());
                        isVisible = true;
                    }
                }
            } else if (TypeAnimal.LAMB.toString().equals(type)) {
                if (ele.getPosition() == terrain) {
                    if (ele.getType().equals(TypeAnimal.LAMB.toString())) {
                        selectedAnimal = ele;
                        GUI.activateSheepType(terrain, true, TypeAnimal.LAMB.toString());
                        isVisible = true;;
                    }
                }
            } else if (TypeAnimal.RAM.toString().equals(type)){
                if (ele.getPosition() == terrain) {
                    if (ele.getType().equals(TypeAnimal.RAM.toString())) {
                        selectedAnimal = ele;
                        GUI.activateSheepType(terrain, true,TypeAnimal.RAM.toString());
                        isVisible = true;
                    }
                }
            }
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);

    }

}
