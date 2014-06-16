package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 * Class for the Listener of the Drop Down menù
 *
 * @author Francesco Corsini
 */
public class StaticDropDownSelectionListener extends JFrame implements ActionListener {

    private final GUISwingStatic gui;

    /**
     * Contrusctor
     *
     * @param gui GUI Static
     */
    public StaticDropDownSelectionListener(GUISwingStatic gui) {
        this.gui = gui;

    }

    /**
     * action performed when a click button event is fired
     *
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {

        JComboBox cb = (JComboBox) e.getSource();
        String command = (String) cb.getSelectedItem();

        if (gui.getGUIState() == GUIState.MOVESHEEPSELECTION) {

            gui.getLAction2().setText("Dove si vuole muovere l'Ovino");

            command = replaceNull(command);
            int num = Integer.parseInt(command);
            gui.setSheepSelected(num);
        } else if (gui.getGUIState() == GUIState.MOVESHEPARDSELECTION) {

            gui.getLAction2().setText("Dove si vuole spostare il Pastore");

            command = replaceNull(command);
            int num = Integer.parseInt(command);
            gui.setShepherdSelected(num);
        } else {
            command = replaceNull(command);
            int num = Integer.parseInt(command);
            gui.setShepherdSelected(num);
        }

    }

    private String replaceNull(String str) {
        return str.replaceAll("[^\\d.]", "");
    }

}
