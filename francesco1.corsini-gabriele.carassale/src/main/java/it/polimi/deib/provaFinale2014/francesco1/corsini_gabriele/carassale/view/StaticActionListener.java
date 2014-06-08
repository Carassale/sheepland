package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Class to listen to the Static Action GUI
 *
 * @author Francesco Corsini
 */
public class StaticActionListener extends JFrame implements ActionListener {

    private final GUISwingStatic gui;

    /**
     * Constructor
     *
     * @param gui GUI Static
     */
    public StaticActionListener(GUISwingStatic gui) {
        this.gui = gui;

    }

    /**
     * action performed when a click button event is fired
     *
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        gui.activateActions(false);

        if (TypeAction.MOVE_SHEPARD.toString().equals(command)) {
            gui.getLAction2().setText("Seleziona quale Pastore muovere");
            gui.setGUIState(GUIState.MOVESHEPARDSELECTION);
            gui.activateShepardSelection(true);
            gui.activateRoads(true);
        } else if (TypeAction.MOVE_SHEEP.toString().equals(command)) {
            gui.getLAction2().setText("Selezione da quale territorio si vuole muovere l'ovino");
            gui.setGUIState(GUIState.MOVESHEEPFROM);
            gui.activateTerrains(true);

        } else if (TypeAction.BUY_CARD.toString().equals(command)) {
            gui.getLAction2().setText("Selezione la tipologia di carta da comprare");
            gui.setGUIState(GUIState.BUYCARD);
            gui.activateTerrainType(true);
        } else if (TypeAction.JOIN_SHEEP.toString().equals(command)) {
            gui.getLAction2().setText("Selezione il terreno dove accoppiare");
            gui.activateTerrains(true);
            gui.setGUIState(GUIState.JOINSHEEPS);

        } else if (TypeAction.KILL_SHEEP.toString().equals(command)) {
            gui.getLAction2().setText("Selezione il territorio dove è l'ovino da abbattere");
            gui.setGUIState(GUIState.KILLSHEEP);
            gui.activateTerrains(true);

        }
    }

}
