package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Classe listener che ascolta le tipologie di terreni
 *
 * @author Francesco Corsini
 */
public class StaticTerrainTypeListener extends JFrame implements ActionListener {

    private final GUISwingStatic gui;

    /**
     * Costruttore
     *
     * @param gui GUI statica
     */
    public StaticTerrainTypeListener(GUISwingStatic gui) {
        this.gui = gui;

    }

    /**
     * action performed when a click button event is fired
     *
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        gui.activateTerrainType(false);

        if (gui.getGUIState() == GUIState.BUYCARD) {
            gui.sendBuyCard(command);
        }

    }

}
