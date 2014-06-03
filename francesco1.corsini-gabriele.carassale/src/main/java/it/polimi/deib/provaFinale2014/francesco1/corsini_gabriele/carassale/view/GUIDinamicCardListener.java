package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Class Listener for the cards
 * @author Francesco Corsini
 */
public class GUIDinamicCardListener extends JFrame implements ActionListener {

    private GUIDinamic GUI;
    private int number;
    private String type;

    /**
     * Constructor
     * @param GUI GUI Dynaic
     * @param num the number of card
     * @param type type of card
     */
    public GUIDinamicCardListener(GUIDinamic GUI, int num, String type) {

        this.type = type;
        this.GUI = GUI;
        this.number = num;
    }

    public void actionPerformed(ActionEvent e) {
        if(GUI.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER || (GUI.getGUIDinamicState() == GUIDinamicState.INITIALIZATION))
            GUI.sendBuyCard(type);
    }
    
    

}
