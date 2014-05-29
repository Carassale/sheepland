package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Francesco Corsini
 */
public class StaticActionListener extends JFrame implements ActionListener {

    private GUISwingStatic GUI;

    public StaticActionListener(GUISwingStatic GUI) {
        this.GUI = GUI;

    }

    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        GUI.activateActions(false);

        if (TypeAction.moveShepard.toString().equals(command)) {
            GUI.getLAction2().setText("Seleziona quale Pastore muovere");
            GUI.setGUIState((GUIState.MOVESHEPARDSELECTION));
            GUI.activateShepardSelection(true);
            GUI.activateRoads(true);
        } else if (TypeAction.moveSheep.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione da quale territorio si vuole muovere l'ovino");
            GUI.setGUIState((GUIState.MOVESHEEPFROM));
            GUI.activateTerrains(true);

        } else if (TypeAction.buyCard.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione la tipologia di carta da comprare");
            GUI.setGUIState((GUIState.BUYCARD));
            GUI.activateTerrainType(true);
        } else if (TypeAction.joinSheep.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione il terreno dove accoppiare");
            GUI.activateTerrains(true);
            GUI.setGUIState((GUIState.JOINSHEEPS));

        } else if (TypeAction.killSheep.toString().equals(command)) {
            GUI.getLAction2().setText("Selezione il territorio dove è l'ovino da abbattere");
            GUI.setGUIState((GUIState.KILLSHEEP));
            GUI.activateTerrains(true);

        }
    }

}
