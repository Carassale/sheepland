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
 * Class for the animation of the Sheep when the action JoinSheeps is performed
 *
 * @author Francesco Corsini
 */
public class AnimationJoinSheeps extends JPanel implements Runnable {

    private BufferedImage heart;
    private static final int MAX_DIM = 100, MIN_DIM = 50;
    private Thread runner;

    /**
     * Standard constructor initializer
     */
    public AnimationJoinSheeps() {
        try {
            heart = ImageIO.read(new File(".\\src\\main\\resources\\heart.png"));
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
        }
        this.setLayout(null);
        this.setOpaque(false);
        this.setLocation(this.getLocation().x + 100, this.getLocation().y + 100);

        runner = new Thread(this);
    }

    /**
     * Run is called when the animation needs to be started
     */
    public void run() {
        for (int i = 0; i <= MAX_DIM; i++) {
            this.setSize(i * 2, i * 2);
            this.setLocation(this.getLocation().x - 1, this.getLocation().y - 1);
            repaint();
            try {
                Thread.sleep(10);
            } catch (Exception ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            }
        }
        for (int j = 0; j <= 2; j++) {
            for (int i = MAX_DIM; i >= MIN_DIM; i--) {
                this.setSize(i * 2, i * 2);
                this.setLocation(this.getLocation().x + 1, this.getLocation().y + 1);
                repaint();
                try {
                    Thread.sleep(15);
                } catch (Exception ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
                }
            }
            for (int i = MIN_DIM; i <= MAX_DIM; i++) {
                this.setSize(i * 2, i * 2);
                this.setLocation(this.getLocation().x - 1, this.getLocation().y - 1);
                repaint();
                try {
                    Thread.sleep(15);
                } catch (Exception ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
                }
            }
        }
        for (int i = MAX_DIM; i >= 0; i--) {
            this.setSize(i * 2, i * 2);
            this.setLocation(this.getLocation().x + 1, this.getLocation().y + 1);
            repaint();
            try {
                Thread.sleep(10);
            } catch (Exception ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            }
        }
        this.setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(heart, 0, 0, getWidth(), getHeight(), this);
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
