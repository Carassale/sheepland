package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Classe principale per la GUI Dinamica. Questa classe fa da raccoglitore di
 * tutti gli oggetti.
 *
 * @author Francesco Corsini
 */
public class GUIDinamic extends JFrame implements TypeOfInteraction {

    private ConnectionClient connectionClient;

    private GUIDinamicState state;

    private GUIDinamicPanel panel;
    private final JButton[] cards = new JButton[6];
    private final DinamicSheepButton[] jbuttonSheeps = new DinamicSheepButton[19];
    private final DinamicKillSheepButton[] jbuttonKillSheep = new DinamicKillSheepButton[19];
    private final DinamicJoinSheepsButton[] jbuttonJoinSheeps = new DinamicJoinSheepsButton[19];
    private final DinamicMoveSheepButton[] jbuttonMoveSheep = new DinamicMoveSheepButton[19];
    private final JLabel[] jlabelBlackSheep = new JLabel[19];
    private final JLabel[] jlabelWolf = new JLabel[19];
    private final DimanicSheepTypeButton[] jlabelLamb = new DimanicSheepTypeButton[19];
    private final DimanicSheepTypeButton[] jlabelWhiteSheep = new DimanicSheepTypeButton[19];
    private final DimanicSheepTypeButton[] jlabelRam = new DimanicSheepTypeButton[19];
    private final DinamicRoadButton[] roads = new DinamicRoadButton[42];
    private JLayeredPane layeredPane;
    private JLabel textLabel, coinNumber, coinPicture, fenceCounter, borderPicture, winner, sadFace;
    private JTextArea istructionLabel;
    private JLabel[] backPlayer = new JLabel[4];
    private JLabel[] iconPlayer = new JLabel[4];
    private JLabel[] nicknamePlayer = new JLabel[4];
    private final List<JLabel> sideLabels = new ArrayList<JLabel>();

    private final List<String> messages = new ArrayList<String>();

    private final ImageIcon[] plainCards = new ImageIcon[6];
    private final ImageIcon[] forestCards = new ImageIcon[6];
    private final ImageIcon[] riverCards = new ImageIcon[6];
    private final ImageIcon[] desertCards = new ImageIcon[6];
    private final ImageIcon[] mountainCards = new ImageIcon[6];
    private final ImageIcon[] fieldCards = new ImageIcon[6];
    private int plain = 0, forest = 0, river = 0, desert = 0, mountain = 0, field = 0;

    private final List<ViewShepherd> shepherds = new ArrayList<ViewShepherd>();

    //contenitore di tutte le immagini
    private final BufferedImageContainer imagePool;

    //uso per attesa animazioni
    private boolean waitingForAddAnimal = false;
    //variabile temporanea usata per posizionare Pastori 
    private int tempShepherd = -1;

    private List<ViewAnimal> animals = new ArrayList<ViewAnimal>();

    //per eseguire il refresh giusto nel muovipastore e nel muovipecora
    private int tempRoad;

    //se -1 vuol dire che nussun submenu è aperto
    private int subMenuOpen = -1;
    private int wolfActive = 18, blackSheepActive = 18;

    //per eseguire il refresh giusto nel muovipastore
    private int tempIdShepherd;
    private int coins;
    private int fenceNumber;

    private ViewAnimal sheepSelected;
    private boolean twoPlayer = true;

    private static final String FONT_FANTASY = "fantasy";

    /**
     * Constructor that initializes the GUI. It creates the
     * BufferedImageContainer that is the container of all the game pictures
     *
     * @param connectionClient the one that the GUI uses to send back the
     * commands
     */
    public GUIDinamic(ConnectionClient connectionClient) {
        this.connectionClient = connectionClient;
        imagePool = new BufferedImageContainer();
        fenceNumber = 20;

        state = GUIDinamicState.INITIALIZATION;
        createAndShowGUI(true);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Costruttore usato solo nei test(non c'è connessione e non si vede gui)
     */
    public GUIDinamic() {
        imagePool = new BufferedImageContainer();
        fenceNumber = 20;

        state = GUIDinamicState.INITIALIZATION;
        createAndShowGUI(false);

        //se test allora faccio la disclose per uscire
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * service method that supports the constructor in the creation of the GUI
     * components
     */
    private void createAndShowGUI(boolean showGUI) {
        Dimension dim = new Dimension(1200, 650);
        setSize(dim);

        layeredPane = new JLayeredPane();
        layeredPane.setBackground(new Color(36, 159, 245));
        layeredPane.setPreferredSize(new Dimension(1200, 650));

        createTable();
        createSheepButtons();
        createSheepSubMenu();
        createBlackSheepLabels();
        createWolfLabels();
        createCards();
        createRoadLabels();
        createTextLabel();
        createFenceCounter();
        createMoney();
        createBorder();
        createStatusPlayer();
        createWinnerScene();
        createMouse();

        setLocationRelativeTo(null);
        //Display the window.
        pack();
        setVisible(showGUI);
    }

    /**
     * Service method that creates the main picture of the island
     */
    private void createTable() {
        JComponent newContentPane = layeredPane;
        newContentPane.setOpaque(true);
        setContentPane(newContentPane);
        try {
            panel = new GUIDinamicPanel("src\\main\\resources\\Table.png");
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        panel.setLocation(100, 0);
        Dimension dim2 = new Dimension(750, 700);
        panel.setSize(dim2);
        layeredPane.add(panel, Integer.valueOf(0));
        newContentPane.addMouseListener(null);
    }

    /**
     * Service method to create the sheep buttons on the territories
     */
    private void createSheepButtons() {
        for (int i = 0; i <= 18; i++) {
            jbuttonSheeps[i] = new DinamicSheepButton(this, i);
            setBackGroundInvisible(jbuttonSheeps[i]);
            layeredPane.add(jbuttonSheeps[i], Integer.valueOf(3));
            jbuttonSheeps[i].setSize(100, 100);
        }
        //serve ad inizializzare le pecore su sheapsbourg a 0
        jbuttonSheeps[18].setnumber(0);
    }

    /**
     * Service method to create the action submenu on the sheep button
     */
    private void createSheepSubMenu() {
        for (int i = 0; i <= 18; i++) {
            jlabelLamb[i] = new DimanicSheepTypeButton(this, i, TypeAnimal.LAMB.toString(), imagePool);
            jlabelWhiteSheep[i] = new DimanicSheepTypeButton(this, i, TypeAnimal.WHITE_SHEEP.toString(), imagePool);
            jlabelRam[i] = new DimanicSheepTypeButton(this, i, TypeAnimal.RAM.toString(), imagePool);
            jbuttonKillSheep[i] = new DinamicKillSheepButton(this, i, imagePool);
            jbuttonJoinSheeps[i] = new DinamicJoinSheepsButton(this, i, imagePool);
            jbuttonMoveSheep[i] = new DinamicMoveSheepButton(this, i, imagePool);
            layeredPane.add(jbuttonKillSheep[i], Integer.valueOf(5));
            layeredPane.add(jbuttonJoinSheeps[i], Integer.valueOf(5));
            layeredPane.add(jbuttonMoveSheep[i], Integer.valueOf(5));
            layeredPane.add(jlabelLamb[i], Integer.valueOf(5));
            layeredPane.add(jlabelWhiteSheep[i], Integer.valueOf(5));
            layeredPane.add(jlabelRam[i], Integer.valueOf(5));
            jbuttonKillSheep[i].setSize(70, 70);
            jbuttonJoinSheeps[i].setSize(100, 100);
            jbuttonMoveSheep[i].setSize(70, 70);
            jlabelLamb[i].setSize(70, 70);
            jlabelWhiteSheep[i].setSize(70, 70);
            jlabelRam[i].setSize(70, 70);

            Point p = jbuttonSheeps[i].getLocation();
            jbuttonMoveSheep[i].setLocation(p.x - 70, p.y - 35);
            jbuttonJoinSheeps[i].setLocation(p.x - 20, p.y - 100);
            jbuttonKillSheep[i].setLocation(p.x + 70, p.y - 35);
            jlabelLamb[i].setLocation(p.x + 70, p.y + 35);
            jlabelWhiteSheep[i].setLocation(p.x + 5, p.y + 60);
            jlabelRam[i].setLocation(p.x - 70, p.y + 35);
            jbuttonKillSheep[i].setVisible(false);
        }

    }

    /**
     * Service method to create and show the cards on the right side
     */
    private void createCards() {
        for (int j = 0; j <= 5; j++) {
            plainCards[j] = new ImageIcon("src\\main\\resources\\Carte\\Carta6_" + j + ".png");
            forestCards[j] = new ImageIcon("src\\main\\resources\\Carte\\Carta5_" + j + ".png");
            riverCards[j] = new ImageIcon("src\\main\\resources\\Carte\\Carta4_" + j + ".png");
            desertCards[j] = new ImageIcon("src\\main\\resources\\Carte\\Carta3_" + j + ".png");
            mountainCards[j] = new ImageIcon("src\\main\\resources\\Carte\\Carta2_" + j + ".png");
            fieldCards[j] = new ImageIcon("src\\main\\resources\\Carte\\Carta1_" + j + ".png");
        }
        for (int i = 0; i <= 5; i++) {
            if (i == 0) {
                cards[i] = new JButton(plainCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, TypeCard.PLAIN.toString()));
            } else if (i == 1) {
                cards[i] = new JButton(forestCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, TypeCard.FOREST.toString()));
            } else if (i == 2) {
                cards[i] = new JButton(riverCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, TypeCard.RIVER.toString()));
            } else if (i == 3) {
                cards[i] = new JButton(desertCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, TypeCard.DESERT.toString()));
            } else if (i == 4) {
                cards[i] = new JButton(mountainCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, TypeCard.MOUNTAIN.toString()));
            } else if (i == 5) {
                cards[i] = new JButton(fieldCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, TypeCard.FIELD.toString()));
            }
            setBackGroundInvisible(cards[i]);
            layeredPane.add(cards[i], Integer.valueOf(2));
            cards[i].setSize(136, 134);
            cards[i].setLocation(0, 100 * i);

        }
    }

    /**
     * Service method to create the BlackSHeep labels on the map
     */
    private void createBlackSheepLabels() {
        ImageIcon icon = new ImageIcon("src\\main\\resources\\blackSheep.png");
        for (int i = 0; i <= 18; i++) {
            JLabel label = new JLabel(icon);
            jlabelBlackSheep[i] = label;
            layeredPane.add(label, Integer.valueOf(1));
            if (i == 0) {
                jlabelBlackSheep[i].setLocation(225, 180);
            } else if (i == 1) {
                jlabelBlackSheep[i].setLocation(250, 250);
            } else if (i == 2) {
                jlabelBlackSheep[i].setLocation(370, 330);
            } else if (i == 3) {
                jlabelBlackSheep[i].setLocation(220, 390);
            } else if (i == 4) {
                jlabelBlackSheep[i].setLocation(300, 530);
            } else if (i == 5) {
                jlabelBlackSheep[i].setLocation(470, 440);
            } else if (i == 6) {
                jlabelBlackSheep[i].setLocation(400, 590);
            } else if (i == 7) {
                jlabelBlackSheep[i].setLocation(580, 550);
            } else if (i == 8) {
                jlabelBlackSheep[i].setLocation(625, 325);
            } else if (i == 9) {
                jlabelBlackSheep[i].setLocation(740, 420);
            } else if (i == 10) {
                jlabelBlackSheep[i].setLocation(705, 255);
            } else if (i == 11) {
                jlabelBlackSheep[i].setLocation(570, 250);
            } else if (i == 12) {
                jlabelBlackSheep[i].setLocation(760, 160);
            } else if (i == 13) {
                jlabelBlackSheep[i].setLocation(670, 100);
            } else if (i == 14) {
                jlabelBlackSheep[i].setLocation(530, 180);
            } else if (i == 15) {
                jlabelBlackSheep[i].setLocation(580, 50);
            } else if (i == 16) {
                jlabelBlackSheep[i].setLocation(410, 120);
            } else if (i == 17) {
                jlabelBlackSheep[i].setLocation(360, 265);
            } else if (i == 18) {
                jlabelBlackSheep[i].setLocation(450, 270);
            }
            jlabelBlackSheep[i].setSize(50, 50);
            jlabelBlackSheep[i].setVisible(false);

        }
    }

    /**
     * Service method to create the Wolf labels around the map
     */
    private void createWolfLabels() {
        ImageIcon icon = new ImageIcon("src\\main\\resources\\wolf.png");
        for (int i = 0; i <= 18; i++) {
            jlabelWolf[i] = new JLabel(icon);
            layeredPane.add(jlabelWolf[i], Integer.valueOf(2));
            if (i == 0) {
                jlabelWolf[i].setLocation(215, 90);
            } else if (i == 1) {
                jlabelWolf[i].setLocation(295, 290);
            } else if (i == 2) {
                jlabelWolf[i].setLocation(380, 410);
            } else if (i == 3) {
                jlabelWolf[i].setLocation(190, 475);
            } else if (i == 4) {
                jlabelWolf[i].setLocation(390, 500);
            } else if (i == 5) {
                jlabelWolf[i].setLocation(470, 370);
            } else if (i == 6) {
                jlabelWolf[i].setLocation(350, 610);
            } else if (i == 7) {
                jlabelWolf[i].setLocation(640, 510);
            } else if (i == 8) {
                jlabelWolf[i].setLocation(570, 375);
            } else if (i == 9) {
                jlabelWolf[i].setLocation(660, 440);
            } else if (i == 10) {
                jlabelWolf[i].setLocation(715, 330);
            } else if (i == 11) {
                jlabelWolf[i].setLocation(640, 220);
            } else if (i == 12) {
                jlabelWolf[i].setLocation(760, 205);
            } else if (i == 13) {
                jlabelWolf[i].setLocation(715, 110);
            } else if (i == 14) {
                jlabelWolf[i].setLocation(500, 215);
            } else if (i == 15) {
                jlabelWolf[i].setLocation(550, 120);
            } else if (i == 16) {
                jlabelWolf[i].setLocation(315, 120);
            } else if (i == 17) {
                jlabelWolf[i].setLocation(400, 210);
            } else if (i == 18) {
                jlabelWolf[i].setLocation(500, 310);
            }
            jlabelWolf[i].setSize(50, 50);
            jlabelWolf[i].setVisible(false);

        }

    }

    /**
     * Service method to create the Roads buttons around the map
     */
    private void createRoadLabels() {

        for (int i = 0; i <= 41; i++) {
            roads[i] = new DinamicRoadButton(this, i, imagePool);
            layeredPane.add(roads[i], Integer.valueOf(4));
            roads[i].setSize(50, 50);
            roads[i].setVisible(true);
        }
    }

    /**
     * Service method to create the text labels
     */
    private void createTextLabel() {
        textLabel = new JLabel("");
        textLabel.setFont(new Font(FONT_FANTASY, Font.BOLD, 18));
        textLabel.setForeground(Color.WHITE);
        layeredPane.add(textLabel, Integer.valueOf(10));
        textLabel.setLocation(995, 30);
        textLabel.setSize(textLabel.getPreferredSize());
        textLabel.setVisible(true);

        istructionLabel = new JTextArea(Message.ISTRUCTION.toString());
        istructionLabel.setFont(new Font(FONT_FANTASY, Font.PLAIN, 12));
        istructionLabel.setOpaque(true);
        istructionLabel.setForeground(Color.WHITE);
        istructionLabel.setBackground(new Color(36, 159, 245));
        istructionLabel.setEditable(false);
        layeredPane.add(istructionLabel, 1);
        istructionLabel.setLocation(995, 420);
        istructionLabel.setSize(istructionLabel.getPreferredSize());
        istructionLabel.setVisible(true);

        for (int i = 0; i <= 6; i++) {
            JLabel ele = new JLabel("");
            if (i == 0) {
                ele.setFont(new Font(FONT_FANTASY, Font.BOLD, 12));
                ele.setForeground(Color.WHITE);
            } else {
                ele.setFont(new Font(FONT_FANTASY, Font.PLAIN, 12));
                ele.setForeground(Color.WHITE);
            }
            layeredPane.add(ele, Integer.valueOf(10));
            ele.setLocation(995, 150 + (i * 35));
            ele.setSize(ele.getPreferredSize());
            ele.setVisible(true);
            sideLabels.add(ele);
        }

    }

    /**
     * Service method to create the image with the fence remaining
     */
    private void createFenceCounter() {
        ImageIcon fen = new ImageIcon(imagePool.getFenceNumber(fenceNumber));
        fenceCounter = new JLabel(fen);
        layeredPane.add(fenceCounter, Integer.valueOf(2));
        fenceCounter.setSize(150, 150);
        fenceCounter.setLocation(825, -15);
        fenceCounter.setVisible(true);
    }

    /**
     * Service method to update the image with the fence remaining
     */
    private void updateFenceCounter() {
        ImageIcon fen = new ImageIcon(imagePool.getFenceNumber(fenceNumber));
        fenceCounter.setIcon(fen);
    }

    /**
     * Service method to create the money icon
     */
    private void createMoney() {
        ImageIcon fen = new ImageIcon(imagePool.getCoins());
        coinPicture = new JLabel(fen);
        layeredPane.add(coinPicture, Integer.valueOf(2));
        coinPicture.setSize(80, 80);
        coinPicture.setLocation(825, 550);
        coinPicture.setVisible(true);

        coinNumber = new JLabel("");
        coinNumber.setFont(new Font(FONT_FANTASY, Font.BOLD, 35));
        coinNumber.setForeground(Color.black);
        coinNumber.setSize(coinNumber.getPreferredSize());
        layeredPane.add(coinNumber, Integer.valueOf(2));
        coinNumber.setLocation(790, 570);
        coinNumber.setVisible(true);

    }

    /**
     * Service method to create the border right
     */
    private void createBorder() {
        ImageIcon fen = new ImageIcon(imagePool.getBorderRight());
        borderPicture = new JLabel(fen);
        layeredPane.add(borderPicture, 1);
        borderPicture.setSize(13, 705);
        borderPicture.setLocation(975, 0);
        borderPicture.setVisible(true);
    }

    /**
     * Service method to create the status icon of players
     */
    private void createStatusPlayer() {
        ImageIcon fen = new ImageIcon(imagePool.getBackPlayer());
        ImageIcon ico = null;

        for (int i = 0; i < 4; i++) {
            int y = 130 + (105 * i);

            if (i == 0) {
                ico = new ImageIcon(imagePool.getRedPlayer());
            } else if (i == 1) {
                ico = new ImageIcon(imagePool.getBluPlayer());
            } else if (i == 2) {
                ico = new ImageIcon(imagePool.getYellowPlayer());
            } else if (i == 3) {
                ico = new ImageIcon(imagePool.getGreenPlayer());
            }

            iconPlayer[i] = new JLabel(ico);
            layeredPane.add(iconPlayer[i], 108 + i);
            iconPlayer[i].setSize(80, 50);
            iconPlayer[i].setLocation(905, y + 20);
            iconPlayer[i].setVisible(false);

            nicknamePlayer[i] = new JLabel();
            layeredPane.add(nicknamePlayer[i], 104 + i);
            nicknamePlayer[i].setSize(80, 20);
            nicknamePlayer[i].setLocation(905, y + 70);
            nicknamePlayer[i].setFont(new Font(FONT_FANTASY, Font.BOLD, 15));
            nicknamePlayer[i].setForeground(Color.WHITE);

            nicknamePlayer[i].setOpaque(true);
            if (i == 0) {
                nicknamePlayer[i].setBackground(new Color(255, 0, 0, 180));
            } else if (i == 1) {
                nicknamePlayer[i].setBackground(new Color(0, 0, 255, 180));
            } else if (i == 2) {
                nicknamePlayer[i].setBackground(new Color(255, 255, 0, 180));
            } else if (i == 3) {
                nicknamePlayer[i].setBackground(new Color(0, 255, 0, 180));
            }

            nicknamePlayer[i].setHorizontalAlignment(SwingConstants.CENTER);
            nicknamePlayer[i].setVisible(false);

            backPlayer[i] = new JLabel(fen);
            layeredPane.add(backPlayer[i], 100 + i);
            backPlayer[i].setSize(150, 100);
            backPlayer[i].setLocation(825, y);
            backPlayer[i].setHorizontalAlignment(SwingConstants.RIGHT);
            backPlayer[i].setVisible(false);
        }
    }

    /**
     * Service method to create the winner or looser panels
     */
    private void createWinnerScene() {
        ImageIcon sadf = new ImageIcon(imagePool.getSadFace());
        ImageIcon win = new ImageIcon(imagePool.getWinner());
        winner = new JLabel(win);
        sadFace = new JLabel(sadf);
        layeredPane.add(winner, Integer.valueOf(11));
        layeredPane.add(sadFace, Integer.valueOf(11));
        winner.setSize(250, 250);
        sadFace.setSize(250, 250);
        winner.setLocation(getWidth() *2 / 3, getHeight() *2 / 3);
        sadFace.setLocation(getWidth() *2 / 3, getHeight() *2 / 3);
        winner.setVisible(false);
        sadFace.setVisible(false);
    }

    private void createMouse() {

        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(imagePool.getCursor(), new Point(0, 0), "Custom cursor");
        setCursor(cursor);
    }

    /**
     * Method that updates the text written on the message Label
     *
     * @param text what needs to be written
     */
    public void updateText(String text) {
        textLabel.setText(text);
        textLabel.setSize(textLabel.getPreferredSize());
        textLabel.repaint();
    }

    /**
     * Method that starts the animation when two sheeps are joining
     *
     * @param x x position of the button pressed
     * @param y y position of the button pressed
     * @param terrain where to close the submenu
     */
    public void animationJoinSheeps(int x, int y, int terrain) {
        AnimationJoinSheeps anim = new AnimationJoinSheeps();

        layeredPane.add(anim, Integer.valueOf(5));
        anim.setVisible(true);
        //c'è un offset rispetto al bottone che lo invoca
        anim.setLocation(x + 60, y + 130);
        anim.getRunner().start();
        activateSubMenuSheep(terrain, false);
        activateSheepType(terrain, false, null);
    }

    /**
     * Method called for animation in the case of a succesfull joining (two
     * different animations)
     *
     * @param end true if it's succesfull, false if not
     */
    private void animationJoinSheepSuccesfull(boolean end) {
        AnimationJoinSheepsAfter anim = new AnimationJoinSheepsAfter(end, imagePool);

        layeredPane.add(anim, Integer.valueOf(5));
        anim.setVisible(true);
        anim.setLocation(this.getWidth() / 2, this.getHeight() / 2);
        anim.getRunner().start();
    }

    /**
     * Method that starts the animation when a sheep is killed
     *
     * @param x x position of the button pressed
     * @param y y position of the button pressed
     * @param terrain where to close the submenu
     */
    public void animationKillSheep(int x, int y, int terrain) {
        AnimationKillSheep anim = new AnimationKillSheep(imagePool, this.getWidth(), this.getHeight());

        layeredPane.add(anim, Integer.valueOf(5));
        anim.setVisible(true);
        anim.setLocation(0, 0);
        anim.getRunner().start();
    }

    /**
     * Method called when a sheepTypeBUtton is called: it opens the action
     * submenu
     *
     * @param i which sheepButton is clicked
     * @param val true to open, false to close
     */
    public void activateSubMenuSheep(int i, boolean val) {
        jbuttonMoveSheep[i].setVisible(val);
        jbuttonJoinSheeps[i].setVisible(val);
        jbuttonKillSheep[i].setVisible(val);
    }

    /**
     * Method called to calculate and activate the SheepType buttons
     *
     * @param i the territory to calc
     */
    public void activateSheepTypeButton(int i) {
        //prima li disattivo(la stringa non importa nel caso false)
        jlabelWhiteSheep[i].activateTypeButton();
        jlabelRam[i].activateTypeButton();
        jlabelLamb[i].activateTypeButton();
    }

    /**
     * Method to show the SheepType buttons
     *
     * @param i the terrain where the button is
     * @param val true to show
     * @param kind the type of sheep to show
     */
    public void activateSheepType(int i, boolean val, String kind) {
        if (!val) {
            jlabelWhiteSheep[i].setVisible(false);
            jlabelRam[i].setVisible(false);
            jlabelLamb[i].setVisible(false);
        } else {
            if (TypeAnimal.WHITE_SHEEP.toString().equals(kind)) {
                jlabelWhiteSheep[i].setVisible(true);
            } else if (TypeAnimal.RAM.toString().equals(kind)) {
                jlabelRam[i].setVisible(true);
            } else if (TypeAnimal.LAMB.toString().equals(kind)) {
                jlabelLamb[i].setVisible(true);
            }
        }
    }

    /**
     * Service method called for the Jbutton to hide the background
     *
     * @param comp the jbutton to process
     */
    private void setBackGroundInvisible(JButton comp) {
        comp.setBorderPainted(false);
        comp.setFocusPainted(false);
        comp.setContentAreaFilled(false);
    }

    /**
     * Method to hide the wolf in the current terrain and to show it in the next
     *
     * @param terrain where the wolf is moving to
     */
    public void activateWolf(int terrain) {
        jlabelWolf[wolfActive].setVisible(false);
        for (ViewAnimal ele : animals) {
            //id del wolf
            if (ele.getId() == -2) {
                ele.setPosition(terrain);
            }
        }
        wolfActive = terrain;
        jlabelWolf[terrain].setVisible(true);
    }

    /**
     * Method to hide the blacksheep in the current terrain and to show it in
     * the next
     *
     * @param terrain where the blacksheep is moving to
     */
    public void activateBlackSheep(int terrain) {
        jlabelBlackSheep[blackSheepActive].setVisible(false);
        for (ViewAnimal ele : animals) {
            //id della blacksheep
            if (ele.getId() == -1) {
                ele.setPosition(terrain);
            }
        }
        blackSheepActive = terrain;
        jlabelBlackSheep[terrain].setVisible(true);
    }

    /**
     * Method to update the SheepButton with the right amount of sheep displayed
     *
     * @param terrain the terrain of the button
     */
    private void activateSheep(int terrain) {
        int i = 0;
        for (ViewAnimal ele : animals) {
            //conta gli animali che sono nel suo terreno e che non siano lupi o blacksheep
            if (ele.getPosition() == terrain && ele.getId() != -2 && ele.getId() != -1) {
                i++;
            }
        }
        jbuttonSheeps[terrain].setnumber(i);
    }

    /**
     * Method called from the connection to enable the player to make a move
     * ("wakeup")
     */
    public void clickAction() {
        state = GUIDinamicState.WAITINGFORPLAYER;
        updateText("E' il tuo turno");
    }

    /**
     * Method called from the connection in case of errors
     *
     * @param message to show
     */
    public void errorMessage(String message) {
        //caso in cui l'accoppiamento non è andato a buon fine
        if (waitingForAddAnimal) {
            waitingForAddAnimal = false;
            animationJoinSheepSuccesfull(false);
        }
        if (messages.size() == 10) {
            messages.remove(0);
            messages.add(message);
        } else {
            //altrimenti
            messages.add(message);
        }
        updateSideLabels();
    }

    /**
     * Method called from the connection to enable the player in placing the
     * shepherd
     *
     * @param idShepherd
     */
    public void placeShepherd(int idShepherd) {
        tempShepherd = idShepherd;
        state = GUIDinamicState.PLACESHEPARD;
        updateText("Posizionare Pastore su Strada");
    }

    /**
     * Method called from the connection when a Animal is moved
     *
     * @param idAnimal id of the moved animal
     * @param idTerrain id of the destination
     */
    public void refreshMoveAnimal(int idAnimal, int idTerrain) {
        //caso lupo
        if (idAnimal == -2) {
            for (ViewAnimal ele : animals) {
                if (ele.getId() == idAnimal) {
                    ele.setPosition(idTerrain);
                    activateWolf(idTerrain);
                }
            }
        } else if (idAnimal == -1) {
            //caso blacksheep
            for (ViewAnimal ele : animals) {
                if (ele.getId() == idAnimal) {
                    ele.setPosition(idTerrain);
                    activateBlackSheep(idTerrain);
                }
            }
        } else {
            //caso pecore
            for (ViewAnimal ele : animals) {
                if (ele.getId() == idAnimal) {
                    int oldTerr = ele.getPosition();
                    ele.setPosition(idTerrain);
                    activateSheep(oldTerr);
                    activateSheep(idTerrain);
                }
            }
        }
    }

    /**
     * Method called from the connection when a new animal needs to be added to
     * the game
     *
     * @param idAnimal id of the new animal
     * @param idTerrain id of the starting point
     * @param animalType type of the animal
     */
    public void refreshAddAnimal(int idAnimal, int idTerrain, String animalType) {
        if (waitingForAddAnimal) {
            waitingForAddAnimal = false;
            animationJoinSheepSuccesfull(true);
        }
        if (TypeAnimal.WOLF.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-2, idTerrain, TypeAnimal.WOLF.toString()));
            activateWolf(idTerrain);
        } else if (TypeAnimal.BLACK_SHEEP.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-1, idTerrain, TypeAnimal.BLACK_SHEEP.toString()));
            activateBlackSheep(idTerrain);
        } else if (TypeAnimal.WHITE_SHEEP.toString().equals(animalType)
                || TypeAnimal.RAM.toString().equals(animalType)
                || TypeAnimal.LAMB.toString().equals(animalType)) {
            animals.add(new ViewAnimal(idAnimal, idTerrain, animalType));
            activateSheep(idTerrain);
        }
    }

    /**
     * Method called from the connection when there is need to recover the
     * position of the fences (used in case of reconnection after a downtime)
     *
     * @param idRoad road where to place the fence
     */
    public void refreshAddFence(int idRoad) {
        roads[idRoad].setFence();
        fenceNumber--;
        updateFenceCounter();
    }

    /**
     * Method called from the connection to impose the death of an animal
     *
     * @param idAnimal id of the animal to kill
     */
    public void refreshKillAnimal(int idAnimal) {
        ViewAnimal sheepToKill = null;
        int pos = -1;
        for (ViewAnimal ele : animals) {
            if (ele.getId() == idAnimal) {
                pos = ele.getPosition();
                sheepToKill = ele;
                updateText("Un Ovino è stato ucciso!");
            }
        }
        if (pos != -1) {
            animals.remove(sheepToKill);
            activateSheep(pos);
        } else {
            errorMessage("ERROR KILL SHEEP");
        }
    }

    /**
     * Method called from the connection to proclaim a winner and the losers
     *
     * @param position the position of this GUI(player)
     * @param score score of this GUI(player)
     */
    public void refreshWinner(int position, int score) {
        if (position == 1) {
            winner.setVisible(true);
            updateText("HAI VINTO CON " + score + " PUNTI!!!");
        } else {
            sadFace.setVisible(true);
            updateText("Hai totalizzato " + score + " punti");
        }
    }

    /**
     * Method called from the connection when a lamb grows up
     *
     * @param idAnimal id of the lamb
     * @param kind new animal kind
     */
    public void refreshTransformAnimal(int idAnimal, String kind) {
        for (ViewAnimal ele : animals) {
            if (ele.getId() == idAnimal) {
                ele.setType(kind);
                updateText("Un Ovino è cresciuto!");
            }
        }
    }

    /**
     * Method called from the connection when the player has bought a card
     *
     * @param typeOfTerrain the type of the card
     * @param isSold false if is bought, true if is sold(market)
     */
    public void refreshCard(String typeOfTerrain, boolean isSold) {

        if (TypeCard.PLAIN.toString().equals(typeOfTerrain)) {
            plain++;
            cards[0].setIcon(plainCards[plain]);
        } else if (TypeCard.FOREST.toString().equals(typeOfTerrain)) {
            forest++;
            cards[1].setIcon(forestCards[forest]);
        } else if (TypeCard.RIVER.toString().equals(typeOfTerrain)) {
            river++;
            cards[2].setIcon(riverCards[river]);
        } else if (TypeCard.DESERT.toString().equals(typeOfTerrain)) {
            desert++;
            cards[3].setIcon(desertCards[desert]);
        } else if (TypeCard.MOUNTAIN.toString().equals(typeOfTerrain)) {
            mountain++;
            cards[4].setIcon(mountainCards[mountain]);
        } else {
            field++;
            cards[5].setIcon(fieldCards[field]);
        }

    }

    /**
     * Method called from the connection when there is a change in the money
     *
     * @param coinsChange delta of the coins
     * @param addCoin true if are added, false if subtracted
     */
    public void refreshCoin(int coinsChange, boolean addCoin) {
        if (addCoin) {
            coins = coins + coinsChange;
        } else {
            coins = coins - coinsChange;
        }

        String num = String.valueOf(coins);
        coinNumber.setText(num);
        coinNumber.setSize(coinNumber.getPreferredSize());
        coinNumber.setVisible(true);
    }

    /**
     * Method called from the connection when a new shepherd is added to the
     * game
     *
     * @param idShepherd id of the shepherd
     * @param road where to place
     * @param isMine if this player owns the shepherd
     */
    public void refreshAddShepherd(int idShepherd, int road, boolean isMine) {
        ViewShepherd shep = new ViewShepherd(idShepherd, road);
        roads[road].setShepherd(idShepherd, twoPlayer);
        if (isMine) {
            shep.setIsOwned(true);
            updateText("Posizionato");
        } else {
            shep.setIsOwned(false);
            updateText("Un altro pastore è stato posizionato");
        }
        tempShepherd = -1;
        shepherds.add(shep);
        state = GUIDinamicState.WAITINGFORSERVER;
    }

    /**
     * Method called from the connection to move a shepherd
     *
     * @param idShepherd id of the shepherd
     * @param roadTo id where to move
     */
    public void refreshMoveShepherd(int idShepherd, int roadTo) {

        for (ViewShepherd ele : shepherds) {
            if (ele.getId() == idShepherd) {
                roads[ele.getPostition()].setFence();
                roads[roadTo].setShepherd(idShepherd, twoPlayer);
                ele.setPostition(roadTo);
            }
        }
        state = GUIDinamicState.WAITINGFORSERVER;
        fenceNumber--;
        updateFenceCounter();
    }

    /**
     * Getter method of the State
     *
     * @return the state of the GUI
     */
    public GUIDinamicState getGUIDinamicState() {
        return state;
    }

    /**
     * Setter method of the State
     *
     * @param state the state of the GUI
     */
    public void setGUIDinamicState(GUIDinamicState state) {
        this.state = state;
    }

    /**
     * Getter method of the opened sub menu
     *
     * @return the menu opened
     */
    public int getSubMenuOpen() {
        return subMenuOpen;
    }

    /**
     * Setter method of the opened sub menu
     *
     * @param subMenuOpen the menu opened
     */
    public void setSubMenuOpen(int subMenuOpen) {
        this.subMenuOpen = subMenuOpen;
    }

    /**
     * Method called from the connection to update a message
     *
     * @param message to be written
     */
    public void messageText(String message) {
        //se la lista messaggi è piena
        if (messages.size() == 7) {
            messages.remove(0);
            messages.add(message);
        } else {
            //altrimenti
            messages.add(message);
        }
        updateSideLabels();
    }

    /**
     * Method used to send to the connection the buyCard action
     *
     * @param terrainType the type of card to buy
     */
    public void sendBuyCard(String terrainType) {
        connectionClient.buyCard(terrainType);
        state = GUIDinamicState.WAITINGFORSERVER;
        updateText("");
    }

    /**
     * Method used to send to the connection the MoveSheep action
     *
     * @param terrain where to move
     */
    public void sendMoveSheep(int terrain) {
        if (sheepSelected != null) {
            int id = sheepSelected.getId();
            connectionClient.moveSheep(id, terrain);
            sheepSelected = null;
            state = GUIDinamicState.WAITINGFORSERVER;
        } else {
            errorMessage("ERROR");
        }
        updateText("");
    }

    /**
     * Method used to send to the connection the MoveShepherd action
     *
     * @param roadTo destination road
     */
    public void sendMoveShepherd(int roadTo) {
        state = GUIDinamicState.WAITINGFORSERVER;
        connectionClient.moveShepherd(tempIdShepherd, roadTo);
        updateText("");
    }

    /**
     * Method used to send to the connection the JoinSheeps action
     *
     * @param terrain where to join the sheeps
     */
    public void sendJoinSheeps(int terrain) {
        waitingForAddAnimal = true;
        connectionClient.joinSheep(terrain);
        updateText("");
        state = GUIDinamicState.WAITINGFORSERVER;
    }

    /**
     * Method used to send to the connection the KillSheep action of the
     * selected sheep
     */
    public void sendKillSheep() {
        if (sheepSelected != null) {
            int id = sheepSelected.getId();
            connectionClient.killSheep(id);
            state = GUIDinamicState.WAITINGFORSERVER;
            sheepSelected = null;
        } else {
            errorMessage("ERROR ");
        }
        updateText("");
    }

    /**
     * Method used to send to the connection the placement of a shepherd
     *
     * @param road where to place
     */
    public void sendPlaceShepherd(int road) {
        connectionClient.placeShepherd(road);
        state = GUIDinamicState.WAITINGFORSERVER;
        updateText("");
    }

    /**
     * Getter of the shepherd selected
     *
     * @return the id of the shepherd
     */
    public int getTempShepherd() {
        return tempShepherd;
    }

    /**
     * Getter of the list of all the shepherds
     *
     * @return list of shepherds
     */
    public List<ViewShepherd> getShepherds() {
        return shepherds;
    }

    /**
     * Getter of the selected road
     *
     * @return id of the road
     */
    public int getTempRoad() {
        return tempRoad;
    }

    /**
     * Setter of the selected road
     *
     * @param tempRoad id of the road
     */
    public void setTempRoad(int tempRoad) {
        this.tempRoad = tempRoad;
    }

    /**
     * Getter of the selected shepherd
     *
     * @return id of the shepherd
     */
    public int getTempIdShepherd() {
        return tempIdShepherd;
    }

    /**
     * Setter of the selected shepherd
     *
     * @param tempIdShepherd id of the shepherd
     */
    public void setTempIdShepherd(int tempIdShepherd) {
        this.tempIdShepherd = tempIdShepherd;
    }

    /**
     * Getter of the selected Sheep
     *
     * @return the ViewSheep selected
     */
    public ViewAnimal getSheepSelected() {
        return sheepSelected;
    }

    /**
     * Setter of the selected Sheep
     *
     * @param sheepSelected the ViewSheep selected
     */
    public void setSheepSelected(ViewAnimal sheepSelected) {
        this.sheepSelected = sheepSelected;
    }

    /**
     * Getter of the animals
     *
     * @return list of the animals
     */
    public List<ViewAnimal> getAnimals() {
        return animals;
    }

    /**
     * Setter of the animals
     *
     * @param animals arraylist of the animals
     */
    public void setAnimals(List<ViewAnimal> animals) {
        this.animals = animals;
    }

    /**
     * Getter used only for testing purpose
     *
     * @return the arraylist of the buttons
     */
    public DinamicRoadButton[] getRoads() {
        return roads;
    }

    /**
     * Getter used only for testing purpose
     *
     * @return int number of coins of the player
     */
    public int getCoins() {
        return coins;
    }

    private void updateSideLabels() {
        for (int i = 0; i < messages.size(); i++) {
            //per stampare da alto verso basso
            int j = messages.size() - 1 - i;
            sideLabels.get(i).setText(messages.get(j));
            sideLabels.get(i).setSize(sideLabels.get(i).getPreferredSize());
        }

    }

    /**
     * Riceve il player aggiunto
     *
     * @param nickname Nickname del player
     * @param idPlayer Id del player
     */
    public void refreshAddPlayer(String nickname, int idPlayer) {
        nicknamePlayer[idPlayer].setText(nickname);
        nicknamePlayer[idPlayer].setVisible(true);

        backPlayer[idPlayer].setVisible(true);
        iconPlayer[idPlayer].setVisible(true);

        if (idPlayer > 2) {
            twoPlayer = false;
        }
    }

    /**
     * Riceve il player in attesa di riconnessione
     *
     * @param idPlayer Id del player
     */
    public void refreshWaitPlayer(int idPlayer) {
        //TODO
    }

    /**
     * Riceve il player disconnesso/riconnesso
     *
     * @param idPlayer Id del player
     * @param turnOff True se disconnesso, false se riconnesso
     */
    public void refreshTurnOffPlayer(int idPlayer, boolean turnOff) {
        //TODO
    }

    /**
     * Riceve il player a cui tocca effettuare le azioni
     *
     * @param idPlayer Id del player
     */
    public void refreshTurnPlayer(int idPlayer) {
        ImageIcon fen = new ImageIcon(imagePool.getBackPlayer());
        for (int i = 0; i < 4; i++) {
            backPlayer[i].setIcon(fen);
            backPlayer[i].repaint();
        }

        fen = new ImageIcon(imagePool.getBackCurrentPlayer());
        backPlayer[idPlayer].setIcon(fen);
        backPlayer[idPlayer].repaint();
    }




}
