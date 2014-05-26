/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.TypeOfInteraction;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Francesco Corsini
 */
public class GUISwingStatic extends JFrame implements TypeOfInteraction {

    private ConnectionClient connectionClient;
    private GUISwingStatic GUI;

    private GUIState state;

    private JPanel PMainEast, PMainEastSouth, PMainEastNorth, PMainWest, PMain, PNorth, PSounth, PTerrain, PRoads, PActions, PTerrainType, PLabelAction, PLabelStatus;
    private JLabel LAction1, LAction2, LAction3, LCoins, LShepard1, LShepard2;
    private JLabel[] LTerrainCards = new JLabel[6];
    private JMenu file, connection;
    private JMenuItem eMenuconnectionItemSocket, eMenuconnectionItemRMI;
    private JButton BMoveShepard, BMoveSheep, BBuyCard, BJoinSheeps, BKillSheep;
    private JButton BPlain, BForest, BRiver, BDesert, BMountain, BField;
    private ArrayList<JButton> BTerrain = new ArrayList<JButton>();
    private ArrayList<JButton> BRoad = new ArrayList<JButton>();
    private ArrayList<ViewAnimal> animalsInDropDown = new ArrayList<ViewAnimal>();
    private JComboBox sheepDropDown;

    private ArrayList<ViewAnimal> animals = new ArrayList<ViewAnimal>();
    private ArrayList<ViewShepard> shepards = new ArrayList<ViewShepard>();
    private int[] cards = new int[6];
    private boolean[] roadsWithFence = new boolean[42];
    private int coins = 0;
    private int tempTerrain, tempRoad, tempIdShepard, tempIdSheep;
    
    public GUISwingStatic(ConnectionClient connectionClient) {
        GUI = this;
        this.connectionClient = connectionClient;
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
        PLabelAction.setLayout(new GridLayout(3, 1));
        PMain.add(PActions = new JPanel(), BorderLayout.CENTER);
        PActions.setLayout(new GridLayout(2, 3));
        PMain.add(PLabelStatus = new JPanel(), BorderLayout.EAST);
        PLabelStatus.setLayout(new GridLayout(6, 1));
        PMain.add(PRoads = new JPanel(), BorderLayout.SOUTH);
        PRoads.setLayout(new GridLayout(6, 7));
        PMainEast.setLayout(new BorderLayout());
        PMainEast.add(PMainEastNorth = new JPanel(), BorderLayout.NORTH);
        PMainEastNorth.setLayout(new GridLayout(4, 1));
        PMainEast.add(PMainEastSouth = new JPanel(), BorderLayout.SOUTH);
        PMainEastSouth.add(sheepDropDown = new JComboBox());

        PLabelAction.add(LAction1 = new JLabel("Non è il tuo turno"));
        PLabelAction.add(LAction2 = new JLabel("Attendi..."));
        PLabelAction.add(LAction3 = new JLabel(" "));

        PMainEastNorth.add(LCoins = new JLabel("Monete :" + coins));
        PMainEastNorth.add(LShepard1 = new JLabel("Posizione 1° pastore: non posizionato"));
        PMainEastNorth.add(LShepard2 = new JLabel("Posizione 2° pastore: non posizionato"));
        sheepDropDown.setEnabled(false);

        for (int i = 0; i < 42; i++) {
            roadsWithFence[i] = false;
        }

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
        BMoveShepard.addActionListener(new StaticActionListener(this));
        BMoveShepard.setActionCommand("MoveShepard");
        PActions.add(BMoveShepard);
        BMoveShepard.setEnabled(false);

        BMoveSheep = new JButton("Muovi Pecora");
        BMoveSheep.addActionListener(new StaticActionListener(this));
        BMoveSheep.setActionCommand("MoveSheep");
        PActions.add(BMoveSheep);
        BMoveSheep.setEnabled(false);

        BBuyCard = new JButton("Compra Carta");
        BBuyCard.addActionListener(new StaticActionListener(this));
        BBuyCard.setActionCommand("BuyCard");
        PActions.add(BBuyCard);
        BBuyCard.setEnabled(false);

        BJoinSheeps = new JButton("Accoppia Ovini");
        BBuyCard.addActionListener(new StaticActionListener(this));
        BJoinSheeps.setActionCommand("JoinSheeps");
        PActions.add(BJoinSheeps);
        BJoinSheeps.setEnabled(false);

        BKillSheep = new JButton("Abbatti Ovino");
        BKillSheep.addActionListener(new StaticActionListener(this));
        BKillSheep.setActionCommand("KillSheep");
        PActions.add(BKillSheep);
        BKillSheep.setEnabled(false);

        for (int i = 0; i <= 18; i++) {
            JButton B = new JButton("Terreno" + i);
            B.addActionListener(new StaticTerrainListener(this, i));
            B.setActionCommand("" + i);
            BTerrain.add(B);
            PTerrain.add(B);
            B.setEnabled(false);
        }

        for (int i = 0; i <= 41; i++) {
            JButton B = new JButton("Strada" + i);
            B.addActionListener(new StaticRoadListener(this,i));
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
        /*BPlain.addActionListener(new GUIlistener());
         BForest.addActionListener(new GUIlistener());
         BRiver.addActionListener(new GUIlistener());
         BDesert.addActionListener(new GUIlistener());
         BMountain.addActionListener(new GUIlistener());
         BField.addActionListener(new GUIlistener());*/
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

    }

    

    public void clickAction() {
        LAction2.setText("E' il tuo Turno!");
        activateActions(true);
    }

    public void activateActions(boolean val) {
        BMoveShepard.setEnabled(val);
        BMoveSheep.setEnabled(val);
        BBuyCard.setEnabled(val);
        BJoinSheeps.setEnabled(val);
        BKillSheep.setEnabled(val);
    }

    public void activateRoads(boolean val) {
        for (int i = 0; i <= 41; i++) {
            if (roadsWithFence[i] == false) {
                BRoad.get(i).setEnabled(val);
            }
        }
    }

    public void activateTerrains(boolean val) {
        for (int i = 0; i <= 18; i++) {
            BRoad.get(i).setEnabled(val);
        }
    }

    public void activateTerrainType(boolean val) {

        BPlain.setEnabled(val);
        BForest.setEnabled(val);
        BRiver.setEnabled(val);
        BDesert.setEnabled(val);
        BMountain.setEnabled(val);
        BField.setEnabled(val);

    }

    public void activateSheepSelection(boolean val, int terrain) {
        if (val == true) {
            animalsInDropDown.clear();
            ArrayList<String> dropDownLabels = new ArrayList<String>();

            for (ViewAnimal ele : animals) {
                if (ele.getPosition() == terrain) {
                    animalsInDropDown.add(ele);
                }
            }

            for (ViewAnimal ele2 : animalsInDropDown) {
                String string = "Pecora con id " + ele2.getId();
                JButton prova = new JButton("ciao");
                JButton prova2 = new JButton("ciao2");
                sheepDropDown.addItem(prova);
                sheepDropDown.addItem(prova2);
                dropDownLabels.add(string);
                //sheepDropDown.addItem(string);
            }
            //caso in cui non ci sono pecore da selezionare
            if (dropDownLabels.size() == 0) {
                LAction3.setText("In quel territorio non ci sono Ovini! Ritenta la mossa");
                activateTerrains(true);
            }//TODO tutto sto metodo da capire se a sheepDropDown posso aggiungere bottoni
        }
        sheepDropDown.setEnabled(val);

    }

    public void refreshCoin(int coins, boolean addCoins) {
        if (addCoins) {
            this.coins = this.coins + coins;
        } else {
            this.coins = this.coins - coins;
        }

        LCoins.setText("Monete : " + this.coins);
    }

    public void refreshCard(String typeOfTerrain, boolean isSold) {

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
    
    public void refreshMoveAnimal(int id, int terrain) {
        if (id >= 0) {
            LAction2.setText("Animale " + id + "è stato mosso in terreno " + terrain);
            ViewAnimal sheep = idToViewSheep(id);
            sheep.setPosition(terrain);
        } else if (id == -1) {//caso BlackSheep
            LAction3.setText("La pecora nera si è mossa in terreno " + terrain);
            ViewAnimal blackSheep = idToViewSheep(id);
            blackSheep.setPosition(terrain);
        } else if (id == -2) {//caso BlackSheep
            LAction2.setText("Il Lupo si è mosso in terreno " + terrain);
            ViewAnimal blackSheep = idToViewSheep(id);
            blackSheep.setPosition(terrain);
        }

    }

    public void refreshAddAnimal(int terrain, String animalType) {
        int i = animals.size();
        if ("wolf".equals(animalType)) {
            animals.add(new ViewAnimal(-2, 18));
        } else if ("blackSheep".equals(animalType)) {
            animals.add(new ViewAnimal(-1, 18));
        } else if ("whiteSheep".equals(animalType) || "ram".equals(animalType) || "lamb".equals(animalType)) {
            animals.add(new ViewAnimal(i, terrain, animalType));
            LAction2.setText("E' nata/o un nuovo " + animalType + " sul terreno numero " + terrain);
        }
    }

    public void refreshKillAnimal(int id) {
        ViewAnimal sheepToKill = idToViewSheep(id);
        animals.remove(sheepToKill);
        LAction2.setText("E' stato ucciso l'Animale numero " + id);
    }

    public void refreshTransformAnimal(int id, String kind) {
        ViewAnimal sheepGrowing = idToViewSheep(id);
        sheepGrowing.setType(kind);
        LAction3.setText("Sono cresciuti degli animali! ");
    }

    public void refreshAddShepard(int id, int road) {
        shepards.add(new ViewShepard(id, road));
        LAction2.setText("E' stato aggiunto un Pastore sulla strada " + road);
    }

    public void refreshMoveShepard(int id, int road) {

        ViewShepard shepard = idToViewShepard(id);
        int pos = shepard.getPostition();
        roadsWithFence[pos] = true;
        BRoad.get(pos).setText("Coperta da Cancello");
        shepard.setPostition(road);
        LAction2.setText("E' stato mosso un Pastore sulla strada " + road);

    }

    public void declareWinner(boolean isWinner) {
        if (isWinner) {
            LAction3.setText("HAI VINTO!!!!");
        } else {
            LAction3.setText("Un altro giocatore ha vinto :(");
        }
    }

    public void errorMessage(String message) {
        LAction3.setText(message);
    }

    private ViewAnimal idToViewSheep(int id) {
        ViewAnimal sheep = null;
        for (ViewAnimal ele : animals) {
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

    public GUIState getGUIState() {
        return state;
    }

    public void setGUIState(GUIState state) {
        this.state = state;
    }

    public void setNickname() {
        //TODO setNickname
    }

    public void placeShepard(int id) {
        LAction2.setText("Selezionare strada dove piazzare Pastore");
        state = GUIState.PLACESHEPARD;
        activateRoads(true);
        tempIdShepard = id;
    }

    public void sendPlaceShepard(int road) {
        shepards.add(new ViewShepard(tempIdShepard, road));
        connectionClient.placeShepard(road);
        //TODO occhio che qui creo il pastore anche se è in una posizione scorretta!
    }

    public void sendMoveSheep(int terrain) {
        connectionClient.moveSheep(tempIdSheep, terrain);
    }
    
    public void sendMoveShepard(int terrainTo){
        connectionClient.moveShepard(tempIdShepard, terrainTo);
    }

    public JLabel getLAction2() {
        return LAction2;
    }

    public void setLAction2(JLabel LAction2) {
        this.LAction2 = LAction2;
    }

    public void sendJoinSheeps(int terrain) {
        connectionClient.joinSheep(terrain);
    }

    public void sendKillSheep(int terrain) {

    }

    public int getTempTerrain() {
        return tempTerrain;
    }

    public void setTempTerrain(int tempTerrain) {
        this.tempTerrain = tempTerrain;
    }

    public int getTempIdShepard() {
        return tempIdShepard;
    }

    public void setTempIdShepard(int tempIdShepard) {
        this.tempIdShepard = tempIdShepard;
    }

    public int getTempRoad() {
        return tempRoad;
    }

    public void setTempRoad(int tempRoad) {
        this.tempRoad = tempRoad;
    }
    
    

    public ConnectionClient getConnectionClient() {
        return connectionClient;
    }

    public void setConnectionClient(ConnectionClient connectionClient) {
        this.connectionClient = connectionClient;
    }
    
    

}
