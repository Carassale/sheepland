package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.TypeOfInteraction;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.*;

public class GUIStatic implements TypeOfInteraction {

    static JFrame mainJFrame;

    public GUIStatic() {
        mainJFrame = new JFrame("SheepLand");
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        setupWindow();
    }

    private void setupWindow() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainJFrame.getContentPane().setLayout(new FlowLayout());
        Container c = mainJFrame.getContentPane();

        createLabels();

        createButtons(c);
        mainJFrame.pack();
        //completare

        mainJFrame.setVisible(true);
    }

    private void createLabels() {

    }

    private void createButtons(Container c) {
    }

    public void print(String string) {
    }

    public String read() {
        return "";
    }

}
