package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author Francesco Corsini
 */
public class GUIDinamicSheepSubMenuListener extends JFrame implements ActionListener {

    private GUIDinamic GUI;
    private int terrain;
    private String type;

    public GUIDinamicSheepSubMenuListener(GUIDinamic GUI, int terrain, String type) {
        this.GUI = GUI;
        this.terrain = terrain;
        this.type = type;
    }

    public void actionPerformed(ActionEvent e) {
        GUI.activateSubMenuSheep(terrain, false);
        //in modo che nessun submenu sia aperto
        GUI.setSubMenuOpen(-1);

        if (TypeAction.moveSheep.toString().equals(type)) {

        } else if (TypeAction.killSheep.toString().equals(type)) {

        } else if (TypeAction.joinSheep.toString().equals(type)) {
            GUI.joinSheeps(terrain);
        }
    }

}
