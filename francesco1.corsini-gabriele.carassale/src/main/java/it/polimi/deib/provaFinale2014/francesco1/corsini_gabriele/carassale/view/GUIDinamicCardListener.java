package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Class Listener for the cards
 *
 * @author Francesco Corsini
 */
public class GUIDinamicCardListener extends JFrame implements ActionListener {

    private final GUIDinamic gui;
    private final String type;

    /**
     * Constructor
     *
     * @param gui GUI Dynaic
     * @param type type of card
     */
    public GUIDinamicCardListener(GUIDinamic gui, String type) {
        this.type = type;
        this.gui = gui;
    }

    /**
     * azione eseguita quando il bottone è cliccato
     *
     * @param e evento
     */
    public void actionPerformed(ActionEvent e) {
        if (gui.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER
                || gui.getGUIDinamicState() == GUIDinamicState.INITIALIZATION) {
            gui.sendBuyCard(type);
        }
    }

}
