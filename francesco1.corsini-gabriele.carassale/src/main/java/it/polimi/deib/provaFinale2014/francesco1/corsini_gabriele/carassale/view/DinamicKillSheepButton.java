
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
 * Class for the KillSheep Button
 * @author Francesco Corsini
 */
public class DinamicKillSheepButton extends JPanel{
    
     private BufferedImage icon;
    private GUIDinamic GUI;
    private final  int terrain;
    private DinamicJoinSheepsButton panel; 

    /**
     * Standard Constructor
     * @param aThis GUI Dynamic
     * @param i the terrain where the button is
     */
    DinamicKillSheepButton(GUIDinamic aThis, int i) {
       this.terrain = i;
       this.GUI = aThis;
        
        try {
            icon = ImageIO.read(new File(".\\src\\main\\resources\\killSheep.png"));
        } catch (IOException ex) {
            Logger.getLogger(DinamicJoinSheepsButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLayout(null);
	this.setOpaque(false);
        this.setVisible(false);
        this.setToolTipText("Abbatti Ovino");
        
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                
                //activateAnimation();
                GUI.sendKillSheep();
                GUI.activateSubMenuSheep(terrain, false);
                GUI.activateSheepType(terrain, false, TypeAnimal.WHITE_SHEEP.toString());
                GUI.activateSheepType(terrain, false, TypeAnimal.RAM.toString());
                GUI.activateSheepType(terrain, false, TypeAnimal.LAMB.toString());
                GUI.setGUIDinamicState(GUIDinamicState.WAITINGFORSERVER);
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
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
        
    }

}
