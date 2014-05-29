package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Francesco Corsini
 */
public class AnimationJoinSheeps implements Runnable {

    private Thread runner;
    private GUIDinamic GUI;
    private int max;
    private JPanel panel;
    private boolean isOver = false;

    public AnimationJoinSheeps(GUIDinamic gui, JPanel panel) {
        GUI = gui;
        runner = new Thread(this);
        runner.start();
        this.panel = panel;
        Dimension x = panel.getSize();
        max = x.height * 5;
    }

    public void run() {
        while (!isOver) {
            panel.getSize();
            int newx = panel.getSize().width + 1;
            int newy = panel.getSize().height + 1;
            panel.setSize(newx, newy);
            if (newx >= max) {
                isOver = true;
            }
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }
    }
}
