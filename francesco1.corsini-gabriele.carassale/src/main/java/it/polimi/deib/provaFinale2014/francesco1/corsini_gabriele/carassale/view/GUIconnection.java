/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionInitializer;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Francesco Corsini
 */
public class GUIconnection {

    static JFrame connectionJFrame;
    static ConnectionInitializer connectionClient;

    public GUIconnection(ConnectionInitializer connection) {
        connectionJFrame = new JFrame("Connessione");
        connectionClient = connection;
    }

    public void createGUI() {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                });
    }

    private void createAndShowGUI() {
        setupWindow();
        createConnection();

    }

    private void setupWindow() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        connectionJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        connectionJFrame.getContentPane().setLayout(new FlowLayout());
        connectionJFrame.setVisible(true);

    }

    private void createConnection() {
        JButton b = new JButton("Connetti a Server tramite Socket");
        b.addActionListener(new connectionButtonListener());
        b.setActionCommand("Socket");
        JButton b2 = new JButton("Connetti a Server tramite RMI");
        b2.addActionListener(new connectionButtonListener());
        b2.setActionCommand("RMI");

        connectionJFrame.getContentPane().add(b);
        connectionJFrame.getContentPane().add(b2);
        connectionJFrame.pack();
    }

    public class connectionButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String string = e.getActionCommand();
            if ("Socket".equals(string)) {
                connectionClient.connectToServer("Socket");
            } else {
                connectionClient.connectToServer("RMI");
            }

        }

    }
}
