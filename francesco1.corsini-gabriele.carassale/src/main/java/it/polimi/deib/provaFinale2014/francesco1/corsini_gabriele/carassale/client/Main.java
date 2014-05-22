/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;


import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUIconnection;

/**
 *
 * @author Francesco Corsini
 */
public class Main {
    public static void main(String arg[]) {
        
        ConnectionInitializer cc = new ConnectionInitializer();
        GUIconnection GUI = new GUIconnection(cc);
        
        GUI.createGUI();
        
    }

}
