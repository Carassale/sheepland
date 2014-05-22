/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClientSocket;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionInitializer;
import static it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUIconnection.connectionJFrame;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.*;

/**
 *
 * @author Francesco Corsini
 */
public class GUImain {
    
    static JFrame mainJFrame;
    private final ConnectionClient connection;

    
    public GUImain(ConnectionClientSocket connectionSocket){
        
        connection = connectionSocket;
        mainJFrame = new JFrame("SheepLand");
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                });
    }
    
    public GUImain(ConnectionClientRMI connectionRMI){
        
        connection = connectionRMI;
        mainJFrame = new JFrame("SheepLand");
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                });
    }
    
    private void createAndShowGUI(){
        setupWindow();
    }
    
    private void setupWindow(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFrame.getContentPane().setLayout(new FlowLayout());
        Container c = mainJFrame.getContentPane();
        
        createLabels();
        
        createButtons();
        
        //completare
        
        mainJFrame.setVisible(true);
    }
    
    private void createLabels(){
        
    }
    
    private void createButtons(){
        JButton b1 = new JButton("Muovi Pastore");
        b1.addActionListener(new GUIlistener(connection));
        b1.setActionCommand("MoveShepard");
        
        JButton b2 = new JButton("Muovi Pecora");
        b2.addActionListener(new GUIlistener(connection));
        b2.setActionCommand("MoveSheep");
        
        JButton b3 = new JButton("Compra Carta");
        b3.addActionListener(new GUIlistener(connection));
        b3.setActionCommand("BuyCard");
        
        JButton b4 = new JButton("Accoppia Ovini");
        b4.addActionListener(new GUIlistener(connection));
        b4.setActionCommand("JoinSheeps");
        
        JButton b5 = new JButton("Abbatti Ovino");
        b5.addActionListener(new GUIlistener(connection));
        b5.setActionCommand("KillSheep");
    }

}