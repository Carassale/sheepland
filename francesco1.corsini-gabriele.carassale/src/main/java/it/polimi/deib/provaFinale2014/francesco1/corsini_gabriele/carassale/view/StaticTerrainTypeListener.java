package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 * Classe listener che ascolta le tipologie di terreni
 * @author Francesco Corsini
 */
public class StaticTerrainTypeListener extends JFrame implements ActionListener {

    private GUISwingStatic GUI;

    /**
     * Costruttore
     * @param GUI GUI statica
     */
    public StaticTerrainTypeListener(GUISwingStatic GUI) {
        this.GUI = GUI;

    }

    /**
     * action performed when a click button event is fired
     * @param e event to handle
     */
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();
        GUI.activateTerrainType(false);

        if (GUI.getGUIState() == GUIState.BUYCARD) {
            GUI.sendBuyCard(command);
        }

    }

}
