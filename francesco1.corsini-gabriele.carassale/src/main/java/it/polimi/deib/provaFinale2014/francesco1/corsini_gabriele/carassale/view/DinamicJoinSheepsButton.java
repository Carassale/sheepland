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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Francesco Corsini
 */
public class DinamicJoinSheepsButton extends JPanel {

    private BufferedImage icon;
    private GUIDinamic GUI;
    private final int terrain;
    private DinamicJoinSheepsButton panel; 

    public DinamicJoinSheepsButton(GUIDinamic gui, final int ter) {
        GUI = gui;
        this.terrain = ter;
        try {
            icon = ImageIO.read(new File(".\\src\\main\\resources\\joinSheeps.png"));
        } catch (IOException ex) {
            Logger.getLogger(DinamicJoinSheepsButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setLayout(null);
	this.setOpaque(false);
        this.setVisible(false);
        this.setToolTipText("Accoppia Ovini");
        
        this.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                
                activateAnimation();
                GUI.sendJoinSheeps(terrain);
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
    
    private void activateAnimation(){
        
        GUI.animationJoinSheeps( this.getLocation().x,this.getLocation().y, terrain);
    }
    
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
        
    }
    
    
    

}
