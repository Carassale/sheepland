package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Class for the animation of the Sheep when the action MoveSheep is performed
 *
 * @author Francesco Corsini
 */
public class AnimationMoveSheep extends JPanel implements Runnable {

    private BufferedImage icon;

    /**
     * Standard contructor
     *
     * @param cont which image was displaying when the animation was started
     */
    public AnimationMoveSheep(int cont) {
        BufferedImage[] image = new BufferedImage[2];

        try {
            image[0] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_1.png"));
            image[1] = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_2.png"));
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setVisible(false);
        icon = image[cont];
        repaint();

    }

    /**
     * Run is called when the animation needs to be started
     */
    public void run() {
        //To change body of generated methods, choose Tools | Templates.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
    }

}
