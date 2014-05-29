package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Francesco Corsini
 */
public class GUIDinamicSheepListener extends JFrame implements ActionListener {

    private GUIDinamic GUI;
    private int terrain;

    public GUIDinamicSheepListener(GUIDinamic GUI, int terrain) {
        this.GUI = GUI;
        this.terrain = terrain;
    }

    public void actionPerformed(ActionEvent e) {
        GUI.activateSubMenuSheep(terrain, true);

        if (GUI.getGUIDinamicState() == GUIDinamicState.WAITINGFORPLAYER) {
            GUI.setSubMenuOpen(terrain);
            GUI.setGUIDinamicState((GUIDinamicState.SUBMENUOPEN));
        } //nel caso ci sia un submenù già aperto allora lo chiudo
        else if (GUI.getGUIDinamicState() == GUIDinamicState.SUBMENUOPEN) {
            int i = GUI.getSubMenuOpen();
            if (i == terrain) {
                GUI.activateSubMenuSheep(terrain, false);
            } else {
                GUI.activateSubMenuSheep(i, false);
                GUI.setSubMenuOpen(terrain);
            }
        }
    }

}
