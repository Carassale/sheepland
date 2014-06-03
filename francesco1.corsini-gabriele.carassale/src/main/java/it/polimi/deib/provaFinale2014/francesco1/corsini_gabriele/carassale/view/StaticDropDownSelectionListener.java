package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * Class for the Listener of the Drop Down menù
 * @author Francesco Corsini
 */
public class StaticDropDownSelectionListener extends JFrame implements ActionListener {

    private GUISwingStatic GUI;

    /**
     * Contrusctor
     * @param GUI GUI Static
     */
    public StaticDropDownSelectionListener(GUISwingStatic GUI) {
        this.GUI = GUI;

    }

    /**
     * action performed when a click button event is fired
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {

        JComboBox cb = (JComboBox) e.getSource();
        String command = (String) cb.getSelectedItem();

        if (GUI.getGUIState() == GUIState.MOVESHEEPSELECTION) {

            GUI.getLAction2().setText("Dove si vuole muovere l'Ovino");

            command = command.replaceAll("[^\\d.]", "");
            int num = Integer.parseInt(command);
            GUI.setSheepSelected(num);
        } else if (GUI.getGUIState() == GUIState.MOVESHEPARDSELECTION) {

            GUI.getLAction2().setText("Dove si vuole spostare il Pastore");

            command = command.replaceAll("[^\\d.]", "");
            int num = Integer.parseInt(command);
            GUI.setShepardSelected(num);
        }

    }

}
