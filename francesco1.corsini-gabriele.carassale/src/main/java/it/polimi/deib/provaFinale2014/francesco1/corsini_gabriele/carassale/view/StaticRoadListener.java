package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Classe per i Bottoni delle strade nella GUI Statica
 * @author Francesco Corsini
 */
public class StaticRoadListener extends JFrame implements ActionListener {

    private GUISwingStatic GUI;
    private int road;

    /**
     * Costruttore 
     * @param GUI GUI Static
     * @param road id della strada
     */
    public StaticRoadListener(GUISwingStatic GUI, int road) {
        this.GUI = GUI;
        this.road = road;

    }

    /**
     * action performed when a click button event is fired
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {
        GUI.activateRoads(false);
        String command = e.getActionCommand();

        if (GUI.getGUIState() == GUIState.PLACESHEPARD) {
            GUI.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
            GUI.sendPlaceShepard(road);
        } else if (GUI.getGUIState() == GUIState.MOVESHEPARDSELECTION) {
            GUI.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
            GUI.activateDropDown(false);
            GUI.sendMoveShepard(road);
        }

    }

}
