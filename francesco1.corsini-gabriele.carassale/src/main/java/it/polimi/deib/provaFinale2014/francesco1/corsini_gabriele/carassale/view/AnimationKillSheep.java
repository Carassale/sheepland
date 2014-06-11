package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Francesco Corsini
 */
public class AnimationKillSheep extends JPanel implements Runnable {

    private Thread runner;
    private BufferedImage drop, splat;

    //stati per il disegno
    private int state;

    public AnimationKillSheep(BufferedImageContainer pool, int sizex, int sizey) {

        drop = pool.getBloodDrop();
        splat = pool.getSplat();

        state = 0;

        this.setLayout(null);
        this.setOpaque(false);
        this.setSize(sizex, sizey);

        runner = new Thread(this);

    }

    public void run() {
        while (state < 8) {
            repaint();
            try {
                if (state == 0) {
                    Thread.sleep(2000);
                } else {
                    Thread.sleep(300);
                }
            } catch (Exception ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            state++;
        }
        this.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (state == 0) {
            g.drawImage(drop, 0, 0, getWidth(), getHeight() / 3, this);
        } else {
            //prendo posizione a caso
            int x = (int) ((Math.random() * getWidth()) - 200);
            int y = (int) ((Math.random() * getHeight()) - 200);
            g.drawImage(splat, x, y, 200, 200, this);
        }
    }

    /**
     * Getter of the thread of the animation
     *
     * @return the thread
     */
    public Thread getRunner() {
        return runner;
    }
}
