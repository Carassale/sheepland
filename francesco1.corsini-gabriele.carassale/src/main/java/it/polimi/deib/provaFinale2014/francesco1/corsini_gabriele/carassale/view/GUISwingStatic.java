package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Main class for the Static GUI
 *
 * @author Francesco Corsini
 */
public class GUISwingStatic extends JFrame implements TypeOfInteraction {

    private ConnectionClient connectionClient;
    private final GUISwingStatic gui;

    private GUIState state;

    private JPanel pMainEast, pMainEastNorth, pMainWest, pMain, pNorth, pSounth,
            pTerrain, pRoads, pActions, pTerrainType, pLabelAction, pLabelStatus;
    private JLabel lAction1, lAction2, lAction3, lCoins, lShepard1, lShepard2;
    private final JLabel[] lTerrainCards = new JLabel[6];
    private JMenu file;
    private JButton bMoveShepard, bMoveSheep, bBuyCard, bJoinSheeps, bKillSheep;
    private JButton bPlain, bForest, bRiver, bDesert, bMountain, bField;
    private final List<JButton> bTerrain = new ArrayList<JButton>();
    private final List<JButton> bRoad = new ArrayList<JButton>();
    private final List<ViewAnimal> animalsInDropDown = new ArrayList<ViewAnimal>();
    private final List<ViewShepard> shepardsInDropDown = new ArrayList<ViewShepard>();
    private JComboBox sheepDropDown;

    private final List<ViewAnimal> animals = new ArrayList<ViewAnimal>();
    private final List<ViewShepard> shepards = new ArrayList<ViewShepard>();
    private final int[] cards = new int[6];
    private final boolean[] roadsWithFence = new boolean[42];
    private int coins = 0;
    private int tempTerrain, tempRoad, tempIdShepard = -1, tempIdSheep;
    private boolean firstShepard;
    private int sheepSelected = -1, shepardSelected;

    /**
     * Constructor that get the connection
     *
     * @param connectionClient the connection to talk to
     */
    public GUISwingStatic(ConnectionClient connectionClient) {
        gui = this;
        this.connectionClient = connectionClient;
        initUI(true);

    }

    /**
     * Costruttore usato solo per i test(fa a meno della connessione)
     */
    public GUISwingStatic() {
        gui = this;
        initUI(false);

    }

    /**
     * Metodo creatore principale della GUI che inizializza tutti i pannelli e
     * divide la frame
     */
    private void initUI(boolean showGUI) {

        JFrame.setDefaultLookAndFeelDecorated(true);

        setLayout(new BorderLayout());

        JMenuBar menubar = new JMenuBar();
        ImageIcon icon = new ImageIcon("exit.png");

        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem eMenuFileItem = new JMenuItem("Exit", icon);
        eMenuFileItem.setMnemonic(KeyEvent.VK_E);
        eMenuFileItem.setToolTipText("Exit application");

        pMainEast = new JPanel();
        add(pMainEast, BorderLayout.EAST);
        pMainWest = new JPanel();
        add(pMainWest, BorderLayout.WEST);
        pMain = new JPanel();
        pMainWest.add(pMain, BorderLayout.NORTH);

        pMain.setLayout(new BorderLayout());
        pNorth = new JPanel();
        pMain.add(pNorth, BorderLayout.NORTH);
        pNorth.setLayout(new BorderLayout());
        pTerrain = new JPanel();
        pNorth.add(pTerrain, BorderLayout.NORTH);
        pTerrain.setLayout(new GridLayout(3, 7));
        pTerrainType = new JPanel();
        pNorth.add(pTerrainType, BorderLayout.SOUTH);
        pTerrainType.setLayout(new FlowLayout());
        pLabelAction = new JPanel();
        pMain.add(pLabelAction, BorderLayout.WEST);
        pLabelAction.setLayout(new GridLayout(3, 1));
        pActions = new JPanel();
        pMain.add(pActions, BorderLayout.CENTER);
        pActions.setLayout(new GridLayout(2, 3));
        pLabelStatus = new JPanel();
        pMain.add(pLabelStatus, BorderLayout.EAST);
        pLabelStatus.setLayout(new GridLayout(6, 1));
        pSounth = new JPanel();
        pMain.add(pSounth, BorderLayout.SOUTH);
        pSounth.setLayout(new BorderLayout());
        pRoads = new JPanel();
        pSounth.add(pRoads, BorderLayout.NORTH);
        pRoads.setLayout(new GridLayout(6, 7));
        sheepDropDown = new JComboBox();
        pSounth.add(sheepDropDown, BorderLayout.SOUTH);
        pMainEast.setLayout(new BorderLayout());
        pMainEastNorth = new JPanel();
        pMainEast.add(pMainEastNorth, BorderLayout.NORTH);
        pMainEastNorth.setLayout(new GridLayout(4, 1));

        try {
            pSounth = new GUIDinamicPanel("src\\main\\resources\\DefaultBoardRegionMapping.png");
            pMainEast.add(pSounth, BorderLayout.CENTER);
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }

        lAction1 = new JLabel("Non è il tuo turno");
        pLabelAction.add(lAction1);
        lAction2 = new JLabel("Attendi...");
        pLabelAction.add(lAction2);
        lAction3 = new JLabel(" ");
        pLabelAction.add(lAction3);

        lCoins = new JLabel("Monete :" + coins);
        pMainEastNorth.add(lCoins);
        lShepard1 = new JLabel("Primo pastore in posizione nel terreno numero: non posizionato");
        pMainEastNorth.add(lShepard1);
        lShepard2 = new JLabel("Secondo pastore in posizione nel terreno numero: non posizionato");
        pMainEastNorth.add(lShepard2);

        sheepDropDown.addActionListener(new StaticDropDownSelectionListener(this));
        sheepDropDown.setEnabled(false);

        for (int i = 0; i < 42; i++) {
            roadsWithFence[i] = false;
        }

        for (int i = 0; i < 6; i++) {
            cards[i] = 0;

            if (i == 0) {
                lTerrainCards[i] = new JLabel("Carte Pianura: " + cards[i]);
            } else if (i == 1) {
                lTerrainCards[i] = new JLabel("Carte Foresta: " + cards[i]);
            } else if (i == 2) {
                lTerrainCards[i] = new JLabel("Carte Fiume: " + cards[i]);
            } else if (i == 3) {
                lTerrainCards[i] = new JLabel("Carte Deserto: " + cards[i]);
            } else if (i == 4) {
                lTerrainCards[i] = new JLabel("Carte Montagna: " + cards[i]);
            } else if (i == 5) {
                lTerrainCards[i] = new JLabel("Carte Campi: " + cards[i]);
            }
            pLabelStatus.add(lTerrainCards[i]);
        }

        bMoveShepard = new JButton("Muovi Pastore");
        bMoveShepard.addActionListener(new StaticActionListener(this));
        bMoveShepard.setActionCommand(TypeAction.MOVE_SHEPARD.toString());
        pActions.add(bMoveShepard);
        bMoveShepard.setEnabled(false);

        bMoveSheep = new JButton("Muovi Pecora");
        bMoveSheep.addActionListener(new StaticActionListener(this));
        bMoveSheep.setActionCommand(TypeAction.MOVE_SHEEP.toString());
        pActions.add(bMoveSheep);
        bMoveSheep.setEnabled(false);

        bBuyCard = new JButton("Compra Carta");
        bBuyCard.addActionListener(new StaticActionListener(this));
        bBuyCard.setActionCommand(TypeAction.BUY_CARD.toString());
        pActions.add(bBuyCard);
        bBuyCard.setEnabled(false);

        bJoinSheeps = new JButton("Accoppia Ovini");
        bJoinSheeps.addActionListener(new StaticActionListener(this));
        bJoinSheeps.setActionCommand(TypeAction.JOIN_SHEEP.toString());
        pActions.add(bJoinSheeps);
        bJoinSheeps.setEnabled(false);

        bKillSheep = new JButton("Abbatti Ovino");
        bKillSheep.addActionListener(new StaticActionListener(this));
        bKillSheep.setActionCommand(TypeAction.KILL_SHEEP.toString());
        pActions.add(bKillSheep);
        bKillSheep.setEnabled(false);

        for (int i = 0; i <= 18; i++) {
            JButton button = new JButton("Terreno" + i);
            button.addActionListener(new StaticTerrainListener(this, i));
            button.setActionCommand("" + i);
            bTerrain.add(button);
            pTerrain.add(button);
            button.setEnabled(false);
        }

        for (int i = 0; i <= 41; i++) {
            JButton button = new JButton("Strada" + i);
            button.addActionListener(new StaticRoadListener(this, i));
            button.setActionCommand("" + i);
            bRoad.add(button);
            pRoads.add(button);
            button.setEnabled(false);
        }

        bPlain = new JButton("Pianura");
        bForest = new JButton("Foresta");
        bRiver = new JButton("Fiume");
        bDesert = new JButton("Deserto");
        bMountain = new JButton("Montagna");
        bField = new JButton("Campi");
        bPlain.addActionListener(new StaticTerrainTypeListener(gui));
        bForest.addActionListener(new StaticTerrainTypeListener(gui));
        bRiver.addActionListener(new StaticTerrainTypeListener(gui));
        bDesert.addActionListener(new StaticTerrainTypeListener(gui));
        bMountain.addActionListener(new StaticTerrainTypeListener(gui));
        bField.addActionListener(new StaticTerrainTypeListener(gui));
        bPlain.setActionCommand(TypeCard.PLAIN.toString());
        bForest.setActionCommand(TypeCard.FOREST.toString());
        bRiver.setActionCommand(TypeCard.RIVER.toString());
        bDesert.setActionCommand(TypeCard.DESERT.toString());
        bMountain.setActionCommand(TypeCard.MOUNTAIN.toString());
        bField.setActionCommand(TypeCard.FIELD.toString());
        pTerrainType.add(bPlain);
        pTerrainType.add(bForest);
        pTerrainType.add(bRiver);
        pTerrainType.add(bDesert);
        pTerrainType.add(bMountain);
        pTerrainType.add(bField);
        bPlain.setEnabled(false);
        bForest.setEnabled(false);
        bRiver.setEnabled(false);
        bDesert.setEnabled(false);
        bMountain.setEnabled(false);
        bField.setEnabled(false);

        file.add(eMenuFileItem);
        menubar.add(file);
        setJMenuBar(menubar);

        state = GUIState.WAITINGFOROTHERPLAYER;

        setTitle("SheepLand");

        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = (int) tk.getScreenSize().getWidth();
        int ySize = (int) tk.getScreenSize().getHeight();
        Dimension dim = new Dimension(xSize, ySize);

        setMaximumSize(dim);
        setMinimumSize(dim);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(showGUI);
        pack();

        Dimension size = bMoveShepard.getSize();
        bMoveShepard.setMaximumSize(size);
        bMoveShepard.setMinimumSize(size);

    }

    /**
     * Metodo chiamato quando si vuole far iniziare il turno al giocatore
     */
    public void clickAction() {
        lAction1.setText("E' il tuo Turno!");
        activateActions(true);
    }

    /**
     * Metodo che attiva/disattiva i bottoni azione del giocatore
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateActions(boolean val) {
        bMoveShepard.setEnabled(val);
        bMoveSheep.setEnabled(val);
        bBuyCard.setEnabled(val);
        bJoinSheeps.setEnabled(val);
        bKillSheep.setEnabled(val);
    }

    /**
     * Metodo che attiva/disattiva i bottoni delle strade
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateRoads(boolean val) {
        for (int i = 0; i <= 41; i++) {
            if (!roadsWithFence[i]) {
                bRoad.get(i).setEnabled(val);
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
            bTerrain.get(i).setEnabled(val);
        }
    }

    /**
     * Metodo che attiva/disattiva i bottoni della tipologia di terreno
     *
     * @param val true si attivano, false si disattivano
     */
    public void activateTerrainType(boolean val) {

        bPlain.setEnabled(val);
        bForest.setEnabled(val);
        bRiver.setEnabled(val);
        bDesert.setEnabled(val);
        bMountain.setEnabled(val);
        bField.setEnabled(val);

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
        itemCount = itemCount - 1;
        for (int i = 0; i < itemCount; i++) {
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
            lAction3.setText("In quel territorio non ci sono Ovini! Ritenta la mossa");
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
        itemCount = itemCount - 1;
        for (int i = 0; i < itemCount; i++) {
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

        lCoins.setText("Monete : " + this.coins);
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
            lTerrainCards[0].setText("Carte Pianura: " + cards[0]);
        } else if (TypeCard.FOREST.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(1, isSold);
            lTerrainCards[1].setText("Carte Foresta: " + cards[1]);
        } else if (TypeCard.RIVER.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(2, isSold);
            lTerrainCards[2].setText("Carte Fiume: " + cards[2]);
        } else if (TypeCard.DESERT.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(3, isSold);
            lTerrainCards[3].setText("Carte Deserto: " + cards[3]);
        } else if (TypeCard.MOUNTAIN.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(4, isSold);
            lTerrainCards[4].setText("Carte Montagna: " + cards[4]);
        } else if (TypeCard.FIELD.toString().equals(typeOfTerrain)) {
            serviceRefreshCards(5, isSold);
            lTerrainCards[5].setText("Carte Campi: " + cards[5]);
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
            lAction2.setText("Animale " + id + "è stato mosso in terreno " + terrain);
            ViewAnimal sheep = idToViewSheep(id);
            sheep.setPosition(terrain);
        } else if (id == -1) {
            //caso BlackSheep
            lAction3.setText("La pecora nera si è mossa in terreno " + terrain);
            ViewAnimal blackSheep = idToViewSheep(id);
            blackSheep.setPosition(terrain);
        } else if (id == -2) {
            //caso BlackSheep
            lAction2.setText("Il Lupo si è mosso in terreno " + terrain);
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
    public void refreshAddAnimal(int idAnimal, int terrain, String animalType) {
        int i = animals.size();
        if (TypeAnimal.WOLF.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-2, 18));
        } else if (TypeAnimal.BLACK_SHEEP.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-1, 18));
        } else if (TypeAnimal.WHITE_SHEEP.toString().equals(animalType)
                || TypeAnimal.RAM.toString().equals(animalType)
                || TypeAnimal.LAMB.toString().equals(animalType)) {
            animals.add(new ViewAnimal(i, terrain, animalType));
            lAction2.setText("E' nata/o un nuovo " + animalType + " sul terreno numero " + terrain);
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
        lAction2.setText("E' stato ucciso l'Animale numero " + id);
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
        lAction3.setText("Sono cresciuti degli animali! ");
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
            if (firstShepard) {
                newShep.setIsFirst(true);
                lShepard1.setText("Primo pastore in posizione nel terreno numero: " + newShep.getPostition());
                firstShepard = false;
            } else {
                newShep.setIsFirst(false);
                lShepard2.setText("Secondo pastore in posizione nel terreno numero: " + newShep.getPostition());
            }
        }
        lAction2.setText("E' stato aggiunto un Pastore sulla strada " + road);

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
        bRoad.get(pos).setText("Coperta da Cancello");
        shepard.setPostition(road);
        lAction2.setText("E' stato mosso un Pastore sulla strada " + road);

        if (shepard.isIsFirst()) {
            lShepard1.setText("Primo pastore in posizione nel terreno numero: " + shepard.getPostition());
        } else {
            lShepard2.setText("Secondo pastore in posizione nel terreno numero: " + shepard.getPostition());
        }

    }

    /**
     * Metodo chiamato dalla connection per proclamare vincitore
     *
     * @param isWinner true se è il vincitore
     */
    public void declareWinner(boolean isWinner) {
        if (isWinner) {
            lAction3.setText("HAI VINTO!!!!");
        } else {
            lAction3.setText("Un altro giocatore ha vinto :(");
        }
    }

    /**
     * Metodo chiamato dalla connection per sollevare eccezione a schermo
     *
     * @param message stringa da stampare
     */
    public void errorMessage(String message) {
        lAction3.setText(message);
        gui.activateDropDown(false);
        gui.activateActions(true);
    }

    /**
     * Metodo che converte un id in un animale
     *
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
     *
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
     *
     * @return gui state
     */
    public GUIState getGUIState() {
        return state;
    }

    /**
     * Setter
     *
     * @param state of the GUI
     */
    public void setGUIState(GUIState state) {
        this.state = state;
    }

    /**
     * Method per disporre lo shepherd
     *
     * @param id id of the shepherd
     */
    public void placeShepard(int id) {
        lAction2.setText("Selezionare strada dove piazzare Pastore");
        state = GUIState.PLACESHEPARD;
        if (tempIdShepard == -1) {
            firstShepard = true;
        }
        tempIdShepard = id;
        activateRoads(true);
    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione buyCard
     *
     * @param terrainkind tipologia carta comprata
     */
    public void sendBuyCard(String terrainkind) {
        connectionClient.buyCard(terrainkind);
    }

    /**
     * Metodo chiamato per mandare alla connessione il posizionamento di un
     * pastore
     *
     * @param road id strada
     */
    public void sendPlaceShepard(int road) {
        connectionClient.placeShepard(road);

    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione MoveSheep
     *
     * @param terrain dove muovere
     */
    public void sendMoveSheep(int terrain) {
        connectionClient.moveSheep(sheepSelected, terrain);
    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione MoveShepherd
     *
     * @param roadTo dove muoversi
     */
    public void sendMoveShepard(int roadTo) {
        connectionClient.moveShepard(shepardSelected, roadTo);
    }

    /**
     * Setter per scrivere sulla label
     *
     * @param LAction2
     */
    public void setLAction2(JLabel lAction2) {
        this.lAction2 = lAction2;
    }

    /**
     * Getter per scrivere
     *
     * @return Label
     */
    public JLabel getLAction2() {
        return lAction2;
    }

    /**
     * Metodo chiamato per mandare alla connessione l'azione JoinSheeps
     *
     * @param terrain dove si accoppiano
     */
    public void sendJoinSheeps(int terrain) {
        connectionClient.joinSheep(terrain);
    }

    public void sendKillSheep(int terrain) {
        if (sheepSelected != -1) {
            connectionClient.killSheep(sheepSelected);
        } else {
            lAction1.setText("Non hai selezionato la pecora da abbattere!");
        }
    }

    /**
     * Getter per il terreno selezionato
     *
     * @return terreno selezionato
     */
    public int getTempTerrain() {
        return tempTerrain;
    }

    /**
     * Setter per il terreno selezionato
     *
     * @param tempTerrain terr selezionato
     */
    public void setTempTerrain(int tempTerrain) {
        this.tempTerrain = tempTerrain;
    }

    /**
     * Getter id shepherd selezionato
     *
     * @return id shep
     */
    public int getTempIdShepard() {
        return tempIdShepard;
    }

    /**
     * setter id shepherd selezionato
     *
     * @param tempIdShepard
     */
    public void setTempIdShepard(int tempIdShepard) {
        this.tempIdShepard = tempIdShepard;
    }

    /**
     * getter pecora selezionata
     *
     * @return id pecora
     */
    public int getTempIdSheep() {
        return tempIdSheep;
    }

    /**
     * setter pecora selezionata
     *
     * @param tempIdSheep id pecora
     */
    public void setTempIdSheep(int tempIdSheep) {
        this.tempIdSheep = tempIdSheep;
    }

    /**
     * Getter strada selezionata
     *
     * @return id road
     */
    public int getTempRoad() {
        return tempRoad;
    }

    /**
     * Setter strada selezionata
     *
     * @param tempRoad id road
     */
    public void setTempRoad(int tempRoad) {
        this.tempRoad = tempRoad;
    }

    /**
     * Getter connection
     *
     * @return connection
     */
    public ConnectionClient getConnectionClient() {
        return connectionClient;
    }

    /**
     * Getter dropDown menù
     *
     * @return JComboBox
     */
    public JComboBox getSheepDropDown() {
        return sheepDropDown;
    }

    /**
     * Method to set a string message on the gui
     *
     * @param message text to show
     */
    public void messageText(String message) {
        lAction3.setText(message);
    }

    /**
     * Method colled by the connection to place a new shepard
     *
     * @param idShepard id of shepard
     * @param road id of road
     * @param isMine true if the owner is this gui
     */
    public void refreshAddShepard(int idShepard, int road, boolean isMine) {
        refreshAddShepard(idShepard, road);
    }

    /**
     * Method called to finish the game
     *
     * @param position in the ranking
     * @param score final score
     */
    public void refreshWinner(int position, int score) {
        if (position == 1) {
            lAction3.setText("SEI IL VINCITORE CON " + score + " PUNTI");
        } else {
            lAction3.setText("NON HAI VINTO CON " + score + " PUNTI");
        }

    }

    /**
     * Method called to add all the fences
     *
     * @param idRoad id of the road where to place the fence
     */
    public void refreshAddFence(int idRoad) {
        bRoad.get(idRoad).setEnabled(false);
        bRoad.get(idRoad).setText("Fence");
    }

    /**
     * Setter for the selected sheep
     *
     * @param sheepSelected id of sheep
     */
    public void setSheepSelected(int sheepSelected) {
        this.sheepSelected = sheepSelected;
    }

    /**
     * Setter for the selected shepherd
     *
     * @param shepardSelected id of sheph
     */
    public void setShepardSelected(int shepardSelected) {
        this.shepardSelected = shepardSelected;
    }

    public void refreshAddPlayer(String nickname, int idPlayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshWaitPlayer(int idPlayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshTurnOffPlayer(int idPlayer, boolean turnOff) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshTurnPlayer(int idPlayer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
