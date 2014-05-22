/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import static it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUIconnection.connectionClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Francesco Corsini
 */
public class GUIlistener implements ActionListener{
    
    ConnectionClient connection;
    GUImain GUI;
    
    public GUIlistener(ConnectionClient connectionClient, GUImain gui){
        connection = connectionClient;
        GUI = gui;
    }

    public void actionPerformed(ActionEvent e) {
        String string = e.getActionCommand();
        
        if ("MoveShepard".equals(string)) {
            try{
                connection.moveSheep();
                
            }
            catch(IOException ex){
                Logger.getLogger(GUIlistener.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        else if ("MoveSheep".equals(string)) {
            try {
                connection.moveSheep();
            } catch (IOException ex) {
                Logger.getLogger(GUIlistener.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        else if ("BuyCard".equals(string)) {
            try {
                connection.buyCard();
            } catch (IOException ex) {
                Logger.getLogger(GUIlistener.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        else if ("JoinSheeps".equals(string)) {
            try {
                connection.joinSheep();
            } catch (IOException ex) {
                Logger.getLogger(GUIlistener.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
        else if ("KillSheep".equals(string)) {
            try {
                connection.killSheep();
            } catch (IOException ex) {
                Logger.getLogger(GUIlistener.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
    }

}
