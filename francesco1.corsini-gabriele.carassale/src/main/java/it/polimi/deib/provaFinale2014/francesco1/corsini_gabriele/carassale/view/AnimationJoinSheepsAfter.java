package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * Class for the animation of the Sheep AFTER the action JoinSheeps is performed
 *
 * @author Francesco Corsini
 */
public class AnimationJoinSheepsAfter extends JPanel implements Runnable {

    private BufferedImage icon, icon2, icon3;

    private Thread runner;
    private final boolean isSuccesfull;

    /**
     * Constructor that already knows the outcome of the action already
     * performed
     *
     * @param isSuccesfull true or false change the type of the animation
     * @param image the Pool of the images
     */
    public AnimationJoinSheepsAfter(boolean isSuccesfull, BufferedImageContainer image) {
        BufferedImageContainer imagePool = image;
        this.isSuccesfull = isSuccesfull;

        icon = imagePool.getHeart();
        icon2 = imagePool.getLambPlus1();
        icon3 = imagePool.getHeartbroken();

        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        this.setSize(300, 300);

        repaint();
        runner = new Thread(this);

    }

    /**
     * Method called to start the animation
     */
    public void run() {
        try {
            Thread.sleep(4000);

            if (isSuccesfull) {
                icon = icon2;
                repaint();
            } else {
                icon = icon3;
                repaint();
            }
            Thread.sleep(2500);
        } catch (Exception ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        this.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(icon, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Method to get the Thread of the animation
     *
     * @return the thread
     */
    public Thread getRunner() {
        return runner;
    }

}
