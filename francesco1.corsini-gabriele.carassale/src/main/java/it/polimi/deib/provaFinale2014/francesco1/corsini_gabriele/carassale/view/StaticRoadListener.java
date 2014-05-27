/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Francesco Corsini
 */
public class StaticRoadListener extends JFrame implements ActionListener {

    private GUISwingStatic GUI;
    private int road;

    public StaticRoadListener(GUISwingStatic GUI, int road) {
        this.GUI = GUI;
        this.road = road;

    }

    public void actionPerformed(ActionEvent e) {
        GUI.activateRoads(false);
        String command = e.getActionCommand();

        if (GUI.getGUIState() == GUIState.PLACESHEPARD) {
            GUI.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
            GUI.sendPlaceShepard(road);
        } else if (GUI.getGUIState() == GUIState.MOVESHEPARDTO) {
            GUI.setGUIState(GUIState.MOVESHEPARDTO);
            GUI.sendMoveShepard(road);
        }

    }

}
