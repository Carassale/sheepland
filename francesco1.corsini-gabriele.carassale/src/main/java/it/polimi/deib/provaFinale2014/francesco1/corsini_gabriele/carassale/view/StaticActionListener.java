package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Class to listen to the Static Action GUI
 * @author Francesco Corsini
 */
public class StaticActionListener extends JFrame implements ActionListener {

    private final GUISwingStatic GUI;

    /**
     * Constructor
     * @param gui GUI Static
     */
    public StaticActionListener(GUISwingStatic gui) {
        this.GUI = gui;

    }

    /**
     * action performed when a click button event is fired
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        GUI.activateActions(false);

        if (TypeAction.MOVE_SHEPARD.toString().equals(command)) {
            GUI.getLAction2().setText("Seleziona quale Pastore muovere");
            GUI.setGUIState((GUIState.MOVESHEPARDSELECTION));
            GUI.activateShepardSelection(true);
            GUI.activateRoads(true);
        } else if (TypeAction.MOVE_SHEEP.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione da quale territorio si vuole muovere l'ovino");
            GUI.setGUIState(GUIState.MOVESHEEPFROM);
            GUI.activateTerrains(true);

        } else if (TypeAction.BUY_CARD.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione la tipologia di carta da comprare");
            GUI.setGUIState(GUIState.BUYCARD);
            GUI.activateTerrainType(true);
        } else if (TypeAction.JOIN_SHEEP.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione il terreno dove accoppiare");
            GUI.activateTerrains(true);
            GUI.setGUIState(GUIState.JOINSHEEPS);

        } else if (TypeAction.KILL_SHEEP.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione il territorio dove è l'ovino da abbattere");
            GUI.setGUIState(GUIState.KILLSHEEP);
            GUI.activateTerrains(true);

        }
    }

}
