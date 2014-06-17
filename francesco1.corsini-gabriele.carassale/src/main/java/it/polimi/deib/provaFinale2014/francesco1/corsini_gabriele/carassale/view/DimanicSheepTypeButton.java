package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Class for the buttons representing the types of the sheeps(lamb,ram,white
 * sheep)
 *
 * @author Francesco Corsini
 */
public class DimanicSheepTypeButton extends JPanel {

    private int terrain;
    private GUIDinamic gui;
    private final BufferedImage icon;
    private final String type;
    private ViewAnimal selectedAnimal;

    /**
     * Constructor
     *
     * @param aThis GUI dinamic
     * @param terr terrain where the button has to be created
     * @param t type of the button
     * @param pool imagePool
     */
    public DimanicSheepTypeButton(GUIDinamic aThis, int terr, String t, BufferedImageContainer pool) {
        BufferedImageContainer imagePool = pool;

        terrain = terr;
        gui = aThis;
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

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseClicked(MouseEvent e) {
                gui.setSheepSelected(selectedAnimal);
                gui.activateSubMenuSheep(terrain, true);
                gui.setSubMenuOpen(terrain);
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
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo method
            }

            /**
             * Mouse event
             *
             * @param e event
             */
            public void mouseExited(MouseEvent e) {
                //è presente ma non utilizzato poichè non mi serve ma sto implementando un interfaccia che ha questo method
            }
        });
        repaint();
    }

    /**
     * Method to calculate the types of the sheep present on the terrain
     */
    public void activateTypeButton() {

        for (ViewAnimal ele : gui.getAnimals()) {
            if (TypeAnimal.WHITE_SHEEP.toString().equals(type) && ele.getPosition() == terrain && ele.getType().equals(TypeAnimal.WHITE_SHEEP.toString())) {
                selectedAnimal = ele;
                gui.activateSheepType(terrain, true, TypeAnimal.WHITE_SHEEP.toString());
            } else if (TypeAnimal.LAMB.toString().equals(type) && ele.getPosition() == terrain && ele.getType().equals(TypeAnimal.LAMB.toString())) {
                selectedAnimal = ele;
                gui.activateSheepType(terrain, true, TypeAnimal.LAMB.toString());
            } else if (TypeAnimal.RAM.toString().equals(type) && ele.getPosition() == terrain && ele.getType().equals(TypeAnimal.RAM.toString())) {
                selectedAnimal = ele;
                gui.activateSheepType(terrain, true, TypeAnimal.RAM.toString());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);

    }

}
