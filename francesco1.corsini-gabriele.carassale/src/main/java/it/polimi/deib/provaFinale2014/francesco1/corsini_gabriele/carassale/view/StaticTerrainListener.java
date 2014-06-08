package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Classe per listener della GUI Statica dei terreni
 *
 * @author Francesco Corsini
 */
public class StaticTerrainListener extends JFrame implements ActionListener {

    private final GUISwingStatic gui;
    private final int terrain;

    /**
     * Costruttore
     *
     * @param gui GUI Static
     * @param terrain terreno che deve ascoltare
     */
    public StaticTerrainListener(GUISwingStatic gui, int terrain) {
        this.gui = gui;
        this.terrain = terrain;

    }

    /**
     * action performed when a click button event is fired
     *
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {
        gui.activateTerrains(false);

        if (gui.getGUIState() == GUIState.MOVESHEEPFROM) {
            gui.activateTerrains(true);
            gui.setGUIState(GUIState.MOVESHEEPSELECTION);
            gui.activateSheepSelection(true, terrain);
            gui.getLAction2().setText("Seleziona quale ovino si vuole muovere");
            //TODO finire questo metodo: deve aggiornare nella GUI tempIdSheep
        } else if (gui.getGUIState() == GUIState.KILLSHEEP) {
            gui.setGUIState(GUIState.KILLSHEEPSELECTION);
            gui.activateSheepSelection(true, terrain);
            gui.getLAction2().setText("Seleziona quale ovino vuoi abbatere");
            //TODO finire questo metodo: deve aggiornare nella GUI tempIdSheep
        } else if (gui.getGUIState() == GUIState.JOINSHEEPS) {
            gui.setGUIState(GUIState.KILLSHEEPSELECTION);
            gui.sendJoinSheeps(terrain);
        } else if (gui.getGUIState() == GUIState.MOVESHEEPTO) {
            gui.activateDropDown(false);
            gui.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
            gui.sendMoveSheep(terrain);
        }

    }

}
