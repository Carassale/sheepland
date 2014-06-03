package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Main class for the Static GUI
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
    private JButton BMoveShepard, BMoveSheep, BBuyCard, BJoinSheeps, BKillSheep;
    private JButton BPlain, BForest, BRiver, BDesert, BMountain, BField;
    private ArrayList<JButton> BTerrain = new ArrayList<JButton>();
    private ArrayList<JButton> BRoad = new ArrayList<JButton>();
    private ArrayList<ViewAnimal> animalsInDropDown = new ArrayList<ViewAnimal>();
    private ArrayList<ViewShepard> shepardsInDropDown = new ArrayList<ViewShepard>();
    private JComboBox sheepDropDown;

    private ArrayList<ViewAnimal> animals = new ArrayList<ViewAnimal>();
    private ArrayList<ViewShepard> shepards = new ArrayList<ViewShepard>();
    private int[] cards = new int[6];
    private boolean[] roadsWithFence = new boolean[42];
    private int coins = 0;
    private int tempTerrain, tempRoad, tempIdShepard = -1, tempIdSheep;
    private boolean firstShepard;
    private int sheepSelected, shepardSelected;

    /**
     * Constructor that get the connection
     * @param connectionClient the connection to talk to
     */
    public GUISwingStatic(ConnectionClient connectionClient) {
        GUI = this;
        this.connectionClient = connectionClient;
        initUI();

    }

    /**
     * Metodo creatore principale della GUI che inizializza tutti i pannelli e
     * divide la frame
     */
    private void initUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);

        setLayout(new BorderLayout());

        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");

        file = new JMenu("File");
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
        PMain.add(PSounth = new JPanel(), BorderLayout.SOUTH);
        PSounth.setLayout(new BorderLayout());
        PSounth.add(PRoads = new JPanel(), BorderLayout.NORTH);
        PRoads.setLayout(new GridLayout(6, 7));
        PSounth.add(sheepDropDown = new JComboBox(), BorderLayout.SOUTH);
        PMainEast.setLayout(new BorderLayout());
        PMainEast.add(PMainEastNorth = new JPanel(), BorderLayout.NORTH);
        PMainEastNorth.setLayout(new GridLayout(4, 1));

        try {
            PMainEast.add(PSounth = new GUIDinamicPanel("src\\main\\resources\\DefaultBoardRegionMapping.png"), BorderLayout.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(GUISwingStatic.class.getName()).log(Level.SEVERE, null, ex);
        }

        PLabelAction.add(LAction1 = new JLabel("Non è il tuo turno"));
        PLabelAction.add(LAction2 = new JLabel("Attendi..."));
        PLabelAction.add(LAction3 = new JLabel(" "));

        PMainEastNorth.add(LCoins = new JLabel("Monete :" + coins));
        PMainEastNorth.add(LShepard1 = new JLabel("Primo pastore in posizione nel terreno numero: non posizionato"));
        PMainEastNorth.add(LShepard2 = new JLabel("Secondo pastore in posizione nel terreno numero: non posizionato"));

        sheepDropDown.addActionListener(new StaticDropDownSelectionListener(this));
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

        BMoveShepard = new JButton("Muovi Pastore");
        BMoveShepard.addActionListener(new StaticActionListener(this));
        BMoveShepard.setActionCommand(TypeAction.MOVE_SHEPARD.toString());
        PActions.add(BMoveShepard);
        BMoveShepard.setEnabled(false);

        BMoveSheep = new JButton("Muovi Pecora");
        BMoveSheep.addActionListener(new StaticActionListener(this));
        BMoveSheep.setActionCommand(TypeAction.MOVE_SHEEP.toString());
        PActions.add(BMoveSheep);
        BMoveSheep.setEnabled(false);

        BBuyCard = new JButton("Compra Carta");
        BBuyCard.addActionListener(new StaticActionListener(this));
        BBuyCard.setActionCommand(TypeAction.BUY_CARD.toString());
        PActions.add(BBuyCard);
        BBuyCard.setEnabled(false);

        BJoinSheeps = new JButton("Accoppia Ovini");
        BJoinSheeps.addActionListener(new StaticActionListener(this));
        BJoinSheeps.setActionCommand(TypeAction.JOIN_SHEEP.toString());
        PActions.add(BJoinSheeps);
        BJoinSheeps.setEnabled(false);

        BKillSheep = new JButton("Abbatti Ovino");
        BKillSheep.addActionListener(new StaticActionListener(this));
        BKillSheep.setActionCommand(TypeAction.KILL_SHEEP.toString());
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
            B.addActionListener(new StaticRoadListener(this, i));
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
        BPlain.addActionListener(new StaticTerrainTypeListener(GUI));
        BForest.addActionListener(new StaticTerrainTypeListener(GUI));
        BRiver.addActionListener(new StaticTerrainTypeListener(GUI));
        BDesert.addActionListener(new StaticTerrainTypeListener(GUI));
        BMountain.addActionListener(new StaticTerrainTypeListener(GUI));
        BField.addActionListener(new StaticTerrainTypeListener(GUI));
        BPlain.setActionCommand(TypeCard.PLAIN.toString());
        BForest.setActionCommand(TypeCard.FOREST.toString());
        BRiver.setActionCommand(TypeCard.RIVER.toString());
        BDesert.setActionCommand(TypeCard.DESERT.toString());
        BMountain.setActionCommand(TypeCard.MOUNTAIN.toString());
        BField.setActionCommand(TypeCard.FIELD.toString());
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
        menubar.add(file);
        setJMenuBar(menubar);

        state = state.WAITINGFOROTHERPLAYER;

        setTitle("SheepLand");

        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        Dimension dim = new Dimension(xSize, ySize);

        setMaximumSize(dim);
        setMinimumSize(dim);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

        Dimension size = BMoveShepard.getSize();
        BMoveShepard.setMaximumSize(size);
        BMoveShepard.setMinimumSize(size);

    }

    /**
     * Metodo chiamato quando si vuole far iniziare il turno al giocatore
     */
    public void clickAction() {
        LAction1.setText("E' il tuo Turno!");
        activateActions(true);
    }

    /**
     * Metodo che attiva/disattiva i bottoni azione del giocatore
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateActions(boolean val) {
        BMoveShepard.setEnabled(val);
        BMoveSheep.setEnabled(val);
        BBuyCard.setEnabled(val);
        BJoinSheeps.setEnabled(val);
        BKillSheep.setEnabled(val);
    }

    /**
     * Metodo che attiva/disattiva i bottoni delle strade
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateRoads(boolean val) {
        for (int i = 0; i <= 41; i++) {
            if (roadsWithFence[i] == false) {
                BRoad.get(i).setEnabled(val);
            }
        }
    }

    /**
     * Metodo che attiva/disattiva i bottoni dei terreni
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateTerrains(boolean val) {
        for (int i = 0; i <= 18; i++) {
            BTerrain.get(i).setEnabled(val);
        }
    }

    /**
     * Metodo che attiva/disattiva i bottoni della tipologia di terreno
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateTerrainType(boolean val) {

        BPlain.setEnabled(val);
        BForest.setEnabled(val);
        BRiver.setEnabled(val);
        BDesert.setEnabled(val);
        BMountain.setEnabled(val);
        BField.setEnabled(val);

    }

    /**
     * Metodo che attiva/disattiva il menu a tendina con i le possibili Pecore
     * da selezionare
     *
     * @param val true si attivano, false si disattivano
     * @param terrain su quale terreno sono gli ovini
     */
    public void activateSheepSelection(boolean val, int terrain) {
        animalsInDropDown.clear();

        int itemCount = sheepDropDown.getItemCount();
        for (int i = 0; i < itemCount - 1; i++) {
            sheepDropDown.removeItemAt(0);
        }

        int sheepCount = 0;
        boolean isFirstSelection = true;

        for (ViewAnimal ele : animals) {
            if (ele.getPosition() == terrain) {
                animalsInDropDown.add(ele);
                sheepCount++;
            }
        }

        if (sheepCount == 0) {
            LAction3.setText("In quel territorio non ci sono Ovini! Ritenta la mossa");
            activateTerrains(true);
        } else {
            for (ViewAnimal ele2 : animalsInDropDown) {
                String string = "Pecora con id " + ele2.getId();
                sheepDropDown.addItem(string);
                if (isFirstSelection) {
                    sheepSelected = ele2.getId();
                    isFirstSelection = false;
                }
            }

        }
        sheepDropDown.removeItemAt(0);
        activateDropDown(true);

    }

    /**
     * Metodo che attiva/disattiva il menu a tendina
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateDropDown(boolean val) {
        sheepDropDown.setEnabled(val);
    }

    /**
     * Metodo che attiva/disattiva il menu a tendina e calcola i Pastori che
     * devono figurarci
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateShepardSelection(boolean val) {
        shepardsInDropDown.clear();

        int itemCount = sheepDropDown.getItemCount();
        for (int i = 0; i < itemCount - 1; i++) {
            sheepDropDown.removeItemAt(0);
        }

        boolean isFirstSelection = true;

        for (ViewShepard ele : shepards) {
            if (ele.getIsOwned()) {
                shepardsInDropDown.add(ele);
            }
        }

        for (ViewShepard ele2 : shepardsInDropDown) {
            String string = "Pastore con id " + ele2.getId();
            sheepDropDown.addItem(string);

            if (isFirstSelection) {
                shepardSelected = ele2.getId();
                isFirstSelection = false;
            }
        }
        sheepDropDown.removeItemAt(0);
        activateDropDown(true);

    }

    /**
     * Metodo chiamato dalla connection che aggiorna le monete
     *
     * @param coins quale variazione
     * @param addCoins true se aggiunti, false se sottratti
     */
    public void refreshCoin(int coins, boolean addCoins) {
        if (addCoins) {
            this.coins = this.coins + coins;
        } else {
            this.coins = this.coins - coins;
        }

        LCoins.setText("Monete : " + this.coins);
    }

    /**
     * Metodo chiamato dalla connection che cambia una carta
     *
     * @param typeOfTerrain tipologia terreno
     * @param isSold true se è venduto, false se è comprato
     */
    public void refreshCard(String typeOfTerrain, boolean isSold) {

        if (TypeCard.PLAIN.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(0, isSold);
            LTerrainCards[0].setText("Carte Pianura: " + cards[0]);
        } else if (TypeCard.FOREST.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(1, isSold);
            LTerrainCards[1].setText("Carte Foresta: " + cards[1]);
        } else if (TypeCard.RIVER.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(2, isSold);
            LTerrainCards[2].setText("Carte Fiume: " + cards[2]);
        } else if (TypeCard.DESERT.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(3, isSold);
            LTerrainCards[3].setText("Carte Deserto: " + cards[3]);
        } else if (TypeCard.MOUNTAIN.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(4, isSold);
            LTerrainCards[4].setText("Carte Montagna: " + cards[4]);
        } else if (TypeCard.FIELD.toString().equals(typeOfTerrain)) {
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

    /**
     * Metodo chiamato dalla connection per muovere un animale(Ovino,BlackSHeep
     * o Wolf)
     *
     * @param id id univoco animale
     * @param terrain terreno verso dove muovo
     */
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

    /**
     * Metodo chiamato dalla connection che serve ad aggiungere un nuovo animale
     *
     * @param terrain dove viene aggiunto
     * @param animalType tipologia animale
     */
    public void refreshAddAnimal(int idAnimal, int terrain, String animalType) {//TODO è stato cambiato, ora prende la idAnimal, quindi è da adattare
        int i = animals.size();
        if (TypeAnimal.WOLF.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-2, 18));
        } else if (TypeAnimal.BLACK_SHEEP.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-1, 18));
        } else if (TypeAnimal.WHITE_SHEEP.toString().equals(animalType)
                || TypeAnimal.RAM.toString().equals(animalType)
                || TypeAnimal.LAMB.toString().equals(animalType)) {
            animals.add(new ViewAnimal(i, terrain, animalType));
            LAction2.setText("E' nata/o un nuovo " + animalType + " sul terreno numero " + terrain);
        }
    }

    /**
     * Metodo chiamato dalla connection per uccidere un dato animale
     *
     * @param id id univoco dell'animale da uccidere
     */
    public void refreshKillAnimal(int id) {
        ViewAnimal sheepToKill = idToViewSheep(id);
        animals.remove(sheepToKill);
        LAction2.setText("E' stato ucciso l'Animale numero " + id);
    }

    /**
     * Metodo chiamato dalla connection per far crescere un ovino
     *
     * @param id id univoco animale
     * @param kind tipologia in cui è cresciuto
     */
    public void refreshTransformAnimal(int id, String kind) {
        ViewAnimal sheepGrowing = idToViewSheep(id);
        sheepGrowing.setType(kind);
        LAction3.setText("Sono cresciuti degli animali! ");
    }

    /**
     * Metodo chiamato dalla connection per aggiungere un nuovo Pastore
     *
     * @param id id del pastore
     * @param road dove viene piazzato inizialmente
     */
    public void refreshAddShepard(int id, int road) {
        ViewShepard newShep = new ViewShepard(id, road);
        shepards.add(newShep);
        if (id == tempIdShepard) {

            newShep.setIsOwned(true);
            if (firstShepard == true) {
                newShep.setIsFirst(true);
                LShepard1.setText("Primo pastore in posizione nel terreno numero: " + newShep.getPostition());
                firstShepard = false;
            } else {
                newShep.setIsFirst(false);
                LShepard2.setText("Secondo pastore in posizione nel terreno numero: " + newShep.getPostition());
            }
        }
        LAction2.setText("E' stato aggiunto un Pastore sulla strada " + road);
        //TODO anche nel placeshepard mi deve passare l'id così poi qui la riconosco e aggiungo lo shep alla lista di proprietà
    }

    /**
     * Metodo chiamato dalla connection per muovere un pastore
     *
     * @param id id univoco pastore
     * @param road verso dove si muove
     */
    public void refreshMoveShepard(int id, int road) {

        ViewShepard shepard = idToViewShepard(id);
        int pos = shepard.getPostition();
        roadsWithFence[pos] = true;
        BRoad.get(pos).setText("Coperta da Cancello");
        shepard.setPostition(road);
        LAction2.setText("E' stato mosso un Pastore sulla strada " + road);

        if (shepard.isIsFirst()) {
            LShepard1.setText("Primo pastore in posizione nel terreno numero: " + shepard.getPostition());
        } else {
            LShepard2.setText("Secondo pastore in posizione nel terreno numero: " + shepard.getPostition());
        }

    }

    /**
     * Metodo chiamato dalla connection per proclamare vincitore
     *
     * @param isWinner true se è il vincitore
     */
    public void declareWinner(boolean isWinner) {
        if (isWinner) {
            LAction3.setText("HAI VINTO!!!!");
        } else {
            LAction3.setText("Un altro giocatore ha vinto :(");
        }
    }

    /**
     * Metodo chiamato dalla connection per sollevare eccezione a schermo
     *
     * @param message stringa da stampare
     */
    public void errorMessage(String message) {
        LAction3.setText(message);
        GUI.activateDropDown(false);
        GUI.activateActions(true);
    }

    /**
     * Metodo che converte un id in un animale
     * @param id dell'animale
     * @return animale
     */
    private ViewAnimal idToViewSheep(int id) {
        ViewAnimal sheep = null;
        for (ViewAnimal ele : animals) {
            if (ele.getId() == id) {
                sheep = ele;
            }
        }
        return sheep;
    }

    /**
     * Metodo che converte un id in uno shepherd
     * @param id dello shepherd
     * @return ViewShepard
     */
    private ViewShepard idToViewShepard(int id) {
        ViewShepard shepard = null;
        for (ViewShepard ele : shepards) {
            if (ele.getId() == id) {
                shepard = ele;
            }
        }
        return shepard;
    }

    /**
     * Getter 
     * @return gui state 
     */
    public GUIState getGUIState() {
        return state;
    }

    /**
     * Setter
     * @param state of the GUI 
     */
    public void setGUIState(GUIState state) {
        this.state = state;
    }

    /**
     * Method per disporre lo shepherd
     * @param id id of the shepherd
     */
    public void placeShepard(int id) {
        LAction2.setText("Selezionare strada dove piazzare Pastore");
        state = GUIState.PLACESHEPARD;
        if (tempIdShepard == -1) {
            firstShepard = true;
        }
        tempIdShepard = id;
        activateRoads(true);
    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione buyCard
     * @param terrainkind tipologia carta comprata
     */
    public void sendBuyCard(String terrainkind) {
        connectionClient.buyCard(terrainkind);
    }

    /**
     * Metodo chiamato per mandare alla connessione il posizionamento di un pastore
     * @param road id strada
     */
    public void sendPlaceShepard(int road) {      
        connectionClient.placeShepard(road);
        
    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione MoveSheep
     * @param terrain dove muovere
     */
    public void sendMoveSheep(int terrain) {
        connectionClient.moveSheep(sheepSelected, terrain);
    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione MoveShepherd
     * @param roadTo dove muoversi 
     */
    public void sendMoveShepard(int roadTo) {
        connectionClient.moveShepard(shepardSelected, roadTo);
    }

    /**
     * Setter per scrivere sulla label
     * @param LAction2 
     */
    public void setLAction2(JLabel LAction2) {
        this.LAction2 = LAction2;
    }
    
    /**
     * Getter per scrivere
     * @return Label
     */
    public JLabel getLAction2() {
        return LAction2;
    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione JoinSheeps
     * @param terrain dove si accoppiano
     */
    public void sendJoinSheeps(int terrain) {
        connectionClient.joinSheep(terrain);
    }

    public void sendKillSheep(int terrain) {
        //TODO
    }

    /**
     * Getter per il terreno selezionato
     * @return terreno selezionato
     */
    public int getTempTerrain() {
        return tempTerrain;
    }

    /**
     * Setter per il terreno selezionato
     * @param tempTerrain terr selezionato
     */
    public void setTempTerrain(int tempTerrain) {
        this.tempTerrain = tempTerrain;
    }

    /**
     * Getter id shepherd selezionato
     * @return id shep
     */
    public int getTempIdShepard() {
        return tempIdShepard;
    }

    /**
     * setter id shepherd selezionato
     * @param tempIdShepard 
     */
    public void setTempIdShepard(int tempIdShepard) {
        this.tempIdShepard = tempIdShepard;
    }

    /**
     * getter pecora selezionata
     * @return id pecora
     */
    public int getTempIdSheep() {
        return tempIdSheep;
    }

    /**
     * setter pecora selezionata
     * @param tempIdSheep id pecora
     */
    public void setTempIdSheep(int tempIdSheep) {
        this.tempIdSheep = tempIdSheep;
    }

    /**
     * Getter strada selezionata
     * @return id road
     */
    public int getTempRoad() {
        return tempRoad;
    }

    /**
     * Setter strada selezionata
     * @param tempRoad id road
     */
    public void setTempRoad(int tempRoad) {
        this.tempRoad = tempRoad;
    }

    /**
     * Getter connection
     * @return connection
     */
    public ConnectionClient getConnectionClient() {
        return connectionClient;
    }

    /**
     * Getter dropDown menù
     * @return JComboBox
     */
    public JComboBox getSheepDropDown() {
        return sheepDropDown;
    }

    /**
     * Method to update text
     * @param message text to show
     */
    public void messageText(String message) {
       //TODO
    }

    public void refreshAddShepard(int idShepard, int road, boolean isMine) {
       //TODO
    }
    
    public void refreshWinner(int position, int score){
        //TODO
    }

    public void refreshAddFence(int idRoad) {
        //TODO
    }
    
    /**
     * Setter for the selected sheep
     * @param sheepSelected id of sheep
     */
    public void setSheepSelected(int sheepSelected) {
        this.sheepSelected = sheepSelected;
    }

    /**
     * Setter for the selected shepherd
     * @param shepardSelected id of sheph
     */
    public void setShepardSelected(int shepardSelected) {
        this.shepardSelected = shepardSelected;
    }

}
