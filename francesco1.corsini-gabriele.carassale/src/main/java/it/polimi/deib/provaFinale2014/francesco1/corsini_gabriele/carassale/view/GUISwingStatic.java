/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.TypeOfInteraction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Francesco Corsini
 */
public class GUISwingStatic extends JFrame implements TypeOfInteraction {

    private ConnectionClient connectionClient;
    //private ConnectionInitializer init;
    private GUISwingStatic GUI;

    private GUIState state;

    private JPanel PMainEast, PMainWest, PMain, PNorth, PSounth, PTerrain, PRoads, PActions, PTerrainType, PLabelAction, PLabelStatus;
    private JLabel LAction1, LAction2, LCoins, LShepard1, LShepard2;
    private JLabel[] LTerrainCards = new JLabel[6];
    private JMenu file, connection;
    private JMenuItem eMenuconnectionItemSocket, eMenuconnectionItemRMI;
    private JButton BMoveShepard, BMoveSheep, BBuyCard, BJoinSheeps, BKillSheep;
    private JButton BPlain, BForest, BRiver, BDesert, BMountain, BField;
    private ArrayList<JButton> BTerrain = new ArrayList<JButton>();
    private ArrayList<JButton> BRoad = new ArrayList<JButton>();

    private ArrayList<ViewSheep> sheeps = new ArrayList<ViewSheep>();
    private ArrayList<ViewShepard> shepards = new ArrayList<ViewShepard>();
    private int[] cards = new int[6];
    private int coins = 20;

    public GUISwingStatic() {
        GUI = this;
        initUI();

    }

    private void initUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        setLayout(new BorderLayout());

        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");

        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        connection = new JMenu("Connection");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuFileItem = new JMenuItem("Exit", icon);
        eMenuFileItem.setMnemonic(KeyEvent.VK_E);
        eMenuFileItem.setToolTipText("Exit application");
        eMenuFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        add(PMainEast = new JPanel(), BorderLayout.EAST);
        add(PMainWest = new JPanel(), BorderLayout.WEST);
        PMainWest.add(PMain = new JPanel(), BorderLayout.NORTH);
        PMainWest.add(PSounth = new JPanel(), BorderLayout.SOUTH);
        PMain.setLayout(new BorderLayout());
        PMain.add(PNorth = new JPanel(), BorderLayout.NORTH);
        PNorth.setLayout(new BorderLayout());
        PNorth.add(PTerrain = new JPanel(), BorderLayout.NORTH);
        PTerrain.setLayout(new GridLayout(3, 7));
        PNorth.add(PTerrainType = new JPanel(), BorderLayout.SOUTH);
        PTerrainType.setLayout(new FlowLayout());
        PMain.add(PLabelAction = new JPanel(), BorderLayout.WEST);
        PLabelAction.setLayout(new GridLayout(2, 1));
        PMain.add(PActions = new JPanel(), BorderLayout.CENTER);
        PActions.setLayout(new GridLayout(2, 3));
        PMain.add(PLabelStatus = new JPanel(), BorderLayout.EAST);
        PLabelStatus.setLayout(new GridLayout(6, 1));
        PMain.add(PRoads = new JPanel(), BorderLayout.SOUTH);
        PRoads.setLayout(new GridLayout(6, 7));
        PMainEast.setLayout(new GridLayout(6, 1));

        PLabelAction.add(LAction1 = new JLabel("Non è il tuo turno"));
        PLabelAction.add(LAction2 = new JLabel("Attendi..."));

        PMainEast.add(LCoins = new JLabel("Monete :" + coins));
        PMainEast.add(LShepard1 = new JLabel("Posizione 1° pastore: non posizionato"));
        PMainEast.add(LShepard2 = new JLabel("Posizione 2° pastore: non posizionato"));

        for (int i = 0; i < 6; i++) {
            cards[i] = 0;

            if (i == 0) {
                PLabelStatus.add(LTerrainCards[i] = new JLabel("Carte Pianura: " + cards[i]));
            } else if (i == 1) {
                PLabelStatus.add(LTerrainCards[i] = new JLabel("Carte Foresta: " + cards[i]));
            } else if (i == 2) {
                PLabelStatus.add(LTerrainCards[i] = new JLabel("Carte Fiume: " + cards[i]));
            } else if (i == 3) {
                PLabelStatus.add(LTerrainCards[i] = new JLabel("Carte Deserto: " + cards[i]));
            } else if (i == 4) {
                PLabelStatus.add(LTerrainCards[i] = new JLabel("Carte Montagna: " + cards[i]));
            } else if (i == 5) {
                PLabelStatus.add(LTerrainCards[i] = new JLabel("Carte Campi: " + cards[i]));
            }
        }

        /*
         eMenuconnectionItemSocket = new JMenuItem("Connect with Socket", icon);
         eMenuconnectionItemSocket.setMnemonic(KeyEvent.VK_E);
         eMenuconnectionItemSocket.addActionListener(new GUIConnectionListener("Socket", this));

         eMenuconnectionItemRMI = new JMenuItem("Connect with RMI", icon);
         eMenuconnectionItemRMI.setMnemonic(KeyEvent.VK_E);
         eMenuconnectionItemRMI.addActionListener(new GUIConnectionListener("RMI", this));
         */
        BMoveShepard = new JButton("Muovi Pastore");
        BMoveShepard.addActionListener(new GUIlistener());
        BMoveShepard.setActionCommand("MoveShepard");
        PActions.add(BMoveShepard);
        BMoveShepard.setEnabled(false);

        BMoveSheep = new JButton("Muovi Pecora");
        BMoveSheep.addActionListener(new GUIlistener());
        BMoveSheep.setActionCommand("MoveSheep");
        PActions.add(BMoveSheep);
        BMoveSheep.setEnabled(false);

        BBuyCard = new JButton("Compra Carta");
        BBuyCard.addActionListener(new GUIlistener());
        BBuyCard.setActionCommand("BuyCard");
        PActions.add(BBuyCard);
        BBuyCard.setEnabled(false);

        BJoinSheeps = new JButton("Accoppia Ovini");
        BBuyCard.addActionListener(new GUIlistener());
        BJoinSheeps.setActionCommand("JoinSheeps");
        PActions.add(BJoinSheeps);
        BJoinSheeps.setEnabled(false);

        BKillSheep = new JButton("Abbatti Ovino");
        BKillSheep.addActionListener(new GUIlistener());
        BKillSheep.setActionCommand("KillSheep");
        PActions.add(BKillSheep);
        BKillSheep.setEnabled(false);

        for (int i = 0; i <= 18; i++) {
            JButton B = new JButton("Terreno" + i);
            B.addActionListener(new GUITerrainListener());
            B.setActionCommand("" + i);
            BTerrain.add(B);
            PTerrain.add(B);
            B.setEnabled(false);
        }

        for (int i = 0; i <= 41; i++) {
            JButton B = new JButton("Strada" + i);
            B.addActionListener(new GUITerrainListener());
            B.setActionCommand("" + i);
            BRoad.add(B);
            PRoads.add(B);
            B.setEnabled(false);
        }

        BPlain = new JButton("Pianura");
        BForest = new JButton("Foresta");
        BRiver = new JButton("Fiume");
        BDesert = new JButton("Deserto");
        BMountain = new JButton("Montagna");
        BField = new JButton("Campi");
        BPlain.addActionListener(new GUIlistener());
        BForest.addActionListener(new GUIlistener());
        BRiver.addActionListener(new GUIlistener());
        BDesert.addActionListener(new GUIlistener());
        BMountain.addActionListener(new GUIlistener());
        BField.addActionListener(new GUIlistener());
        BPlain.setActionCommand("Plain");
        BForest.setActionCommand("Forest");
        BRiver.setActionCommand("River");
        BDesert.setActionCommand("Desert");
        BMountain.setActionCommand("Mountain");
        BField.setActionCommand("Field");
        PTerrainType.add(BPlain);
        PTerrainType.add(BForest);
        PTerrainType.add(BRiver);
        PTerrainType.add(BDesert);
        PTerrainType.add(BMountain);
        PTerrainType.add(BField);
        BPlain.setEnabled(false);
        BForest.setEnabled(false);
        BRiver.setEnabled(false);
        BDesert.setEnabled(false);
        BMountain.setEnabled(false);
        BField.setEnabled(false);

        file.add(eMenuFileItem);
        //connection.add(eMenuconnectionItemSocket);
        //connection.add(eMenuconnectionItemRMI);

        menubar.add(file);
        menubar.add(connection);

        setJMenuBar(menubar);

        state = state.WAITINGFOROTHERPLAYER;

        setTitle("SheepLand");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        generateTockens();

    }

    private void generateTockens() {
        for (int i = 0; i < 18; i++) {
            sheeps.add(new ViewSheep(i, i));
        }

    }

    public void refreshCards(String typeOfTerrain, boolean isSold) {

        if ("Plain".equals(typeOfTerrain)) {
            serviceRefreshCards(0, isSold);
            LTerrainCards[0].setText("Carte Pianura: " + cards[0]);
        } else if ("Forest".equals(typeOfTerrain)) {
            serviceRefreshCards(1, isSold);
            LTerrainCards[1].setText("Carte Foresta: " + cards[1]);
        } else if ("River".equals(typeOfTerrain)) {
            serviceRefreshCards(2, isSold);
            LTerrainCards[2].setText("Carte Fiume: " + cards[2]);
        } else if ("Desert".equals(typeOfTerrain)) {
            serviceRefreshCards(3, isSold);
            LTerrainCards[3].setText("Carte Deserto: " + cards[3]);
        } else if ("Mountain".equals(typeOfTerrain)) {
            serviceRefreshCards(4, isSold);
            LTerrainCards[4].setText("Carte Montagna: " + cards[4]);
        } else if ("Field".equals(typeOfTerrain)) {
            serviceRefreshCards(5, isSold);
            LTerrainCards[5].setText("Carte Campi: " + cards[5]);
        }
    }

    private void serviceRefreshCards(int typeOfTerrain, boolean isSold) {
        if (isSold) {
            cards[typeOfTerrain]--;
        } else {
            cards[typeOfTerrain]++;
        }
    }

    public void clickAction() {
        BMoveShepard.setEnabled(true);
        BMoveSheep.setEnabled(true);
        BBuyCard.setEnabled(true);
        BJoinSheeps.setEnabled(true);
        BKillSheep.setEnabled(true);
    }

    public void refreshCoins(int coins, boolean addCoins) {
        if (addCoins) {
            this.coins = this.coins + coins;
        } else {
            this.coins = this.coins - coins;
        }

        LCoins.setText("Monete : " + this.coins);
    }

    public void MoveAnimal(int id, int terrain) {
        LAction2.setText("Animale " + id + "è stato mosso in terreno " + terrain);
        //TODO cambiare label nel caso di BlackSheep e Wolf

    }

    private ViewSheep idToViewSheep(int id) {
        ViewSheep sheep = null;
        for (ViewSheep ele : sheeps) {
            if (ele.getId() == id) {
                sheep = ele;
            }
        }
        return sheep;
    }

    private ViewShepard idToViewShepard(int id) {
        ViewShepard shepard = null;
        for (ViewShepard ele : shepards) {
            if (ele.getId() == id) {
                shepard = ele;
            }
        }
        return shepard;
    }

    public void print(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setGameTable(GameTable gameTable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class GUIConnectionListener extends JFrame implements ActionListener {

        String connectionKind;
        JFrame frame;

        public GUIConnectionListener(String string, JFrame jframe) {
            connectionKind = string;
            frame = jframe;
        }

        public void actionPerformed(ActionEvent e) {

            eMenuconnectionItemSocket.setEnabled(false);
            eMenuconnectionItemRMI.setEnabled(false);

            //init = new ConnectionInitializer(connectionKind);
            //connectionClient = init.getConnectionClient(GUI);
            Thread t = new Thread((Runnable) connectionClient);
            t.start();

        }
    }

    public class GUIlistener extends JFrame implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String string = e.getActionCommand();

            BMoveShepard.setEnabled(false);
            BMoveSheep.setEnabled(false);
            BBuyCard.setEnabled(false);
            BJoinSheeps.setEnabled(false);
            BKillSheep.setEnabled(false);
            if ("MoveShepard".equals(string)) {
                
                    connectionClient.moveShepard();
                    for (int i = 0; i <= 18; i++) {
                        BRoad.get(i).setEnabled(true);
                    }

//               
            } else if ("MoveSheep".equals(string)) {

                
                    connectionClient.moveSheep();
                    for (int i = 0; i <= 41; i++) {
                        BTerrain.get(i).setEnabled(true);
                    }
                    

            } else if ("BuyCard".equals(string)) {
                
                    connectionClient.buyCard();
                  
            } else if ("JoinSheeps".equals(string)) {
                
                    connectionClient.joinSheep();
                    for (int i = 0; i <= 41; i++) {
                        BTerrain.get(i).setEnabled(true);
                    }

                    
            } else if ("KillSheep".equals(string)) {

                connectionClient.killSheep();
                for (int i = 0; i <= 41; i++) {
                    BTerrain.get(i).setEnabled(true);

                }
            }
        }

    }

    public class GUITerrainListener extends JFrame implements ActionListener {

        public void actionPerformed(ActionEvent e) {

        }

    }

}
