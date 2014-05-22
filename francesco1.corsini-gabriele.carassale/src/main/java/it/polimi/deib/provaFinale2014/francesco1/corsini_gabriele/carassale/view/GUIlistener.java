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

/**
 *
 * @author Francesco Corsini
 */
public class GUIlistener implements ActionListener{
    
    ConnectionClient connection;
    
    public GUIlistener(ConnectionClient connectionClient){
        connection = connectionClient;
    }

    public void actionPerformed(ActionEvent e) {
        String string = e.getActionCommand();
        
        if ("MoveShepard".equals(string)) {
                
            }
        else if ("MoveSheep".equals(string)) {
                
            }
        else if ("BuyCard".equals(string)) {
                
            }
        else if ("JoinSheeps".equals(string)) {
                
            }
        else if ("KillSheep".equals(string)) {
                
            }
    }

}
