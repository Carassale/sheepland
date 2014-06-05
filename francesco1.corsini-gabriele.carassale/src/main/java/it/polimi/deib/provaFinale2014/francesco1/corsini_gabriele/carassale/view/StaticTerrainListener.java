package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Classe per listener della GUI Statica dei terreni
 * @author Francesco Corsini
 */
public class StaticTerrainListener extends JFrame implements ActionListener {

    private final GUISwingStatic GUI;
    private final int terrain;

    /**
     * Costruttore
     * @param gui GUI Static
     * @param terrain terreno che deve ascoltare
     */
    public StaticTerrainListener(GUISwingStatic gui, int terrain) {
        this.GUI = gui;
        this.terrain = terrain;

    }

    /**
     * action performed when a click button event is fired
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {

        GUI.activateTerrains(false);
        String command = e.getActionCommand();

        if (GUI.getGUIState() == GUIState.MOVESHEEPFROM) {
            GUI.activateTerrains(true);
            GUI.setGUIState(GUIState.MOVESHEEPSELECTION);
            GUI.activateSheepSelection(true, terrain);
            GUI.getLAction2().setText("Seleziona quale ovino si vuole muovere");
            //TODO finire sto metodo deve aggiornare nella GUI tempIdSheep
        } else if (GUI.getGUIState() == GUIState.KILLSHEEP) {
            GUI.setGUIState(GUIState.KILLSHEEPSELECTION);
            GUI.activateSheepSelection(true, terrain);
            GUI.getLAction2().setText("Seleziona quale ovino vuoi abbatere");
            //TODO finire sto metodo deve aggiornare nella GUI tempIdSheep
        } else if (GUI.getGUIState() == GUIState.JOINSHEEPS) {
            GUI.setGUIState(GUIState.KILLSHEEPSELECTION);
            GUI.sendJoinSheeps(terrain);
        } else if (GUI.getGUIState() == GUIState.MOVESHEEPTO) {
            GUI.activateDropDown(false);
            GUI.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
            GUI.sendMoveSheep(terrain);
        }

    }

}
