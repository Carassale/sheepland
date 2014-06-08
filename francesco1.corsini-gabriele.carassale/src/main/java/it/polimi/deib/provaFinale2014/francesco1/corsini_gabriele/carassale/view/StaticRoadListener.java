package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Classe per i Bottoni delle strade nella GUI Statica
 *
 * @author Francesco Corsini
 */
public class StaticRoadListener extends JFrame implements ActionListener {

    private final GUISwingStatic gui;
    private final int road;

    /**
     * Costruttore
     *
     * @param gui GUI Static
     * @param road id della strada
     */
    public StaticRoadListener(GUISwingStatic gui, int road) {
        this.gui = gui;
        this.road = road;

    }

    /**
     * action performed when a click button event is fired
     *
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {
        gui.activateRoads(false);

        if (gui.getGUIState() == GUIState.PLACESHEPARD) {
            gui.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
            gui.sendPlaceShepard(road);
        } else if (gui.getGUIState() == GUIState.MOVESHEPARDSELECTION) {
            gui.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
            gui.activateDropDown(false);
            gui.sendMoveShepard(road);
        }

    }

}
