package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GUIDinamic extends JFrame implements TypeOfInteraction {

    private ConnectionClient connectionClient;

    private GUIDinamicState state;

    private GUIDinamicPanel panel;
    private JButton[] cards = new JButton[6];
    private DinamicSheepButton[] jbuttonSheeps = new DinamicSheepButton[19];
    private JButton[] jbuttonKillSheep = new JButton[19];
    private DinamicJoinSheepsButton[] jbuttonJoinSheeps = new DinamicJoinSheepsButton[19];
    private DinamicMoveSheepButton[] jbuttonMoveSheep = new DinamicMoveSheepButton[19];
    private JLabel[] jlabelBlackSheep = new JLabel[19];
    private JLabel[] jlabelWolf = new JLabel[19];
    private DinamicRoadButton[] roads = new DinamicRoadButton[42];
    private JLayeredPane layeredPane;
    private JLabel textLabel;

    private ImageIcon plainCards[] = new ImageIcon[6];
    private ImageIcon forestCards[] = new ImageIcon[6];
    private ImageIcon riverCards[] = new ImageIcon[6];
    private ImageIcon desertCards[] = new ImageIcon[6];
    private ImageIcon mountainCards[] = new ImageIcon[6];
    private ImageIcon fieldCards[] = new ImageIcon[6];
    
    private ArrayList<ViewShepard> shepards = new ArrayList<ViewShepard>();
    
    //contenitore di tutte le immagini
    private BufferedImageContainer imagePool;

    //uso per attesa animazioni
    private boolean waitingForAddAnimal = false;
    //variabile temporanea usata per posizionare Pastori 
    private int tempShepard;

    private ArrayList<ViewAnimal> animals = new ArrayList<ViewAnimal>();

    //per prova eliminare
    private int num = 0;

    //se -1 vuol dire che nussun submenu è aperto
    private int subMenuOpen = -1;
    private int wolfActive = 18, blackSheepActive = 18;

    public GUIDinamic(ConnectionClient connectionClient) {
        this.connectionClient = connectionClient;
        imagePool = new BufferedImageContainer();
        createAndShowGUI();
        state = GUIDinamicState.WAITINGFORSERVER;
        
    }

    private void createAndShowGUI() {
        Dimension dim = new Dimension(950, 650);
        setSize(dim);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(950, 650));

        /*per prova
         DinamicMoveSheepButton prova = new DinamicMoveSheepButton(this, 1);
         layeredPane.add(prova, new Integer(5));
         prova.setSize(70, 70);
         prova.setLocation(200, 200);
         */
        createTable();
        createSheepButtons();
        createSheepSubMenu();
        createBlackSheepLabels();
        createWolfLabels();
        createCards();
        createRoadLabels();
        createTextLabel();
        setupAnimations();

        state = state.WAITINGFORPLAYER;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Display the window.
        pack();
        setVisible(true);
    }

    private void setupAnimations() {

    }

    private void createTable() {
        JComponent newContentPane = layeredPane;
        newContentPane.setOpaque(true);
        setContentPane(newContentPane);
        try {
            panel = new GUIDinamicPanel("src\\main\\resources\\Table.png");
        } catch (IOException ex) {
            Logger.getLogger(GUIDinamic.class.getName()).log(Level.SEVERE, null, ex);
        }
        panel.setLocation(100, 0);
        Dimension dim2 = new Dimension(750, 700);
        panel.setSize(dim2);
        layeredPane.add(panel, new Integer(0));
    }

    private void createSheepButtons() {

        int offset = 20;
        for (int i = 0; i <= 18; i++) {
            jbuttonSheeps[i] = new DinamicSheepButton(this);
            setBackGroundInvisible(jbuttonSheeps[i]);
            jbuttonSheeps[i].addActionListener(new GUIDinamicSheepListener(this, i));
            layeredPane.add(jbuttonSheeps[i], new Integer(3));
            jbuttonSheeps[i].setSize(100, 100);
            if (i == 0) {// la prima è la x, la seconda è la y(parte dall'alto)
                jbuttonSheeps[i].setLocation(165 + offset, 110 + offset);
            } else if (i == 1) {
                jbuttonSheeps[i].setLocation(220 + offset, 280 + offset);
            } else if (i == 2) {
                jbuttonSheeps[i].setLocation(340 + offset, 350 + offset);
            } else if (i == 3) {
                jbuttonSheeps[i].setLocation(220 + offset, 410 + offset);
            } else if (i == 4) {
                jbuttonSheeps[i].setLocation(320 + offset, 460 + offset);
            } else if (i == 5) {
                jbuttonSheeps[i].setLocation(450 + offset, 380 + offset);
            } else if (i == 6) {
                jbuttonSheeps[i].setLocation(440 + offset, 500 + offset);
            } else if (i == 7) {
                jbuttonSheeps[i].setLocation(570 + offset, 460 + offset);
            } else if (i == 8) {
                jbuttonSheeps[i].setLocation(570 + offset, 330 + offset);
            } else if (i == 9) {
                jbuttonSheeps[i].setLocation(660 + offset, 380 + offset);
            } else if (i == 10) {
                jbuttonSheeps[i].setLocation(690 + offset, 270 + offset);
            } else if (i == 11) {
                jbuttonSheeps[i].setLocation(585 + offset, 220 + offset);
            } else if (i == 12) {
                jbuttonSheeps[i].setLocation(690 + offset, 165 + offset);
            } else if (i == 13) {
                jbuttonSheeps[i].setLocation(600 + offset, 120 + offset);
            } else if (i == 14) {
                jbuttonSheeps[i].setLocation(465 + offset, 165 + offset);
            } else if (i == 15) {
                jbuttonSheeps[i].setLocation(500 + offset, 60 + offset);
            } else if (i == 16) {
                jbuttonSheeps[i].setLocation(330 + offset, 100 + offset);
            } else if (i == 17) {
                jbuttonSheeps[i].setLocation(340 + offset, 200 + offset);
            } else if (i == 18) {
                jbuttonSheeps[i].setLocation(450 + offset, 270 + offset);
            }
            
        }

        //serve ad inizializzare le pecore su sheapsbourg a 0
        jbuttonSheeps[18].setnumber(0);
    }

    private void createSheepSubMenu() {
        ImageIcon iconKillsheep = new ImageIcon("src\\main\\resources\\killSheep.png");

        for (int i = 0; i <= 18; i++) {
            jbuttonKillSheep[i] = new JButton(iconKillsheep);
            jbuttonJoinSheeps[i] = new DinamicJoinSheepsButton(this, i);
            jbuttonMoveSheep[i] = new DinamicMoveSheepButton(this, i);
            setBackGroundInvisible(jbuttonKillSheep[i]);
            jbuttonKillSheep[i].addActionListener(new GUIDinamicSheepSubMenuListener(this, i, TypeAction.KILL_SHEEP.toString()));
            layeredPane.add(jbuttonKillSheep[i], new Integer(4));
            layeredPane.add(jbuttonJoinSheeps[i], new Integer(4));
            layeredPane.add(jbuttonMoveSheep[i], new Integer(4));
            jbuttonKillSheep[i].setSize(70, 70);
            jbuttonJoinSheeps[i].setSize(100, 100);
            jbuttonMoveSheep[i].setSize(70, 70);

            Point p = jbuttonSheeps[i].getLocation();
            jbuttonMoveSheep[i].setLocation(p.x - 70, p.y - 35);
            jbuttonJoinSheeps[i].setLocation(p.x - 20, p.y - 100);
            jbuttonKillSheep[i].setLocation(p.x + 70, p.y - 35);
            jbuttonKillSheep[i].setVisible(false);

        }

    }

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
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, TypeCard.PLAIN.toString()));
            } else if (i == 1) {
                cards[i] = new JButton(forestCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, TypeCard.FOREST.toString()));
            } else if (i == 2) {
                cards[i] = new JButton(riverCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, TypeCard.RIVER.toString()));
            } else if (i == 3) {
                cards[i] = new JButton(desertCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, TypeCard.DESERT.toString()));
            } else if (i == 4) {
                cards[i] = new JButton(mountainCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, TypeCard.MOUNTAIN.toString()));
            } else if (i == 5) {
                cards[i] = new JButton(fieldCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, TypeCard.FIELD.toString()));
            }
            setBackGroundInvisible(cards[i]);
            layeredPane.add(cards[i], new Integer(2));
            cards[i].setSize(136, 134);
            cards[i].setLocation(0, 100 * i);
        }
    }

    private void createBlackSheepLabels() {
        ImageIcon icon = new ImageIcon("src\\main\\resources\\blackSheep.png");
        for (int i = 0; i <= 18; i++) {
            JLabel label = new JLabel(icon);
            jlabelBlackSheep[i] = label;
            layeredPane.add(label, new Integer(1));
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

    private void createWolfLabels() {
        ImageIcon icon = new ImageIcon("src\\main\\resources\\wolf.png");
        for (int i = 0; i <= 18; i++) {
            jlabelWolf[i] = new JLabel(icon);
            layeredPane.add(jlabelWolf[i], new Integer(2));
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
    
   /* public void resizeRoad(int road, boolean isFence){
        if(isFence){
            roads[road].setSize(50, 50);
            roads[road].setLocation(roads[road].getLocation().x+10, getLocation().y+10);
        }
        else{
            roads[road].setSize(70, 70);
            
        }
    }*/

    private void createRoadLabels() {
        int offsetx = -35;
        int offsety = -55;
        for (int i = 0; i <= 41; i++) {
            roads[i] = new DinamicRoadButton(this, i, imagePool);
            layeredPane.add(roads[i], new Integer(2));
            roads[i].setSize(50, 50);//TODO cambiare size dipendentemente se pastore o fence 50/50 caso fence
            roads[i].setVisible(true);
            
            if (i == 0) {
                roads[i].setLocation(298+offsetx, 208+offsety);
            } else if (i == 1) {
                roads[i].setLocation(344+offsetx, 257+offsety);
            } else if (i == 2) {
                roads[i].setLocation(233+offsetx, 285+offsety);
            } else if (i == 3) {
                roads[i].setLocation(344+offsetx, 314+offsety);
            } else if (i == 4) {
                roads[i].setLocation(344+offsetx, 379+offsety);
            } else if (i == 5) {
                roads[i].setLocation(289+offsetx, 412+offsety);
            } else if (i == 6) {
                roads[i].setLocation(352+offsetx, 449+offsety);
            } else if (i == 7) {
                roads[i].setLocation(299+offsetx, 556+offsety);
            } else if (i == 8) {
                roads[i].setLocation(398+offsetx, 495+offsety);
            } else if (i == 9) {
                roads[i].setLocation(456+offsetx, 513+offsety);
            } else if (i == 10) {
                roads[i].setLocation(398+offsetx, 610+offsety);
            } else if (i == 11) {
                roads[i].setLocation(509+offsetx, 535+offsety);
            } else if (i == 12) {
                roads[i].setLocation(568+offsetx, 585+offsety);
            } else if (i == 13) {
                roads[i].setLocation(566+offsetx, 509+offsety);
            } else if (i == 14) {
                roads[i].setLocation(613+offsetx, 480+offsety);
            } else if (i == 15) {
                roads[i].setLocation(679+offsetx, 526+offsety);
            } else if (i == 16) {
                roads[i].setLocation(669+offsetx, 439+offsety);
            } else if (i == 17) {
                roads[i].setLocation(761+offsetx, 413+offsety);
            } else if (i == 18) {
                roads[i].setLocation(696+offsetx, 374+offsety);
            } else if (i == 19) {
                roads[i].setLocation(698+offsetx, 320+offsety);
            } else if (i == 20) {
                roads[i].setLocation(782+offsetx, 284+offsety);
            } else if (i == 21) {
                roads[i].setLocation(710+offsetx, 269+offsety);
            } else if (i == 22) {
                roads[i].setLocation(734+offsetx, 203+offsety);
            } else if (i == 23) {
                roads[i].setLocation(651+offsetx, 251+offsety);
            } else if (i == 24) {
                roads[i].setLocation(604+offsetx, 227+offsety);
            } else if (i == 25) {
                roads[i].setLocation(634+offsetx, 151+offsety);
            } else if (i == 26) {
                roads[i].setLocation(552+offsetx, 200+offsety);
            } else if (i == 27) {
                roads[i].setLocation(498+offsetx, 145+offsety);
            } else if (i == 28) {
                roads[i].setLocation(491+offsetx, 211+offsety);
            } else if (i == 29) {
                roads[i].setLocation(418+offsetx, 234+offsety);
            } else if (i == 30) {
                roads[i].setLocation(470+offsetx, 264+offsety);
            } else if (i == 31) {
                roads[i].setLocation(445+offsetx, 324+offsety);
            } else if (i == 32) {
                roads[i].setLocation(393+offsetx, 352+offsety);
            } else if (i == 33) {
                roads[i].setLocation(452+offsetx, 377+offsety);
            } else if (i == 34) {
                roads[i].setLocation(461+offsetx, 445+offsety);
            } else if (i == 35) {
                roads[i].setLocation(515+offsetx, 399+offsety);
            } else if (i == 36) {
                roads[i].setLocation(566+offsetx, 433+offsety);
            } else if (i == 37) {
                roads[i].setLocation(568+offsetx, 374+offsety);
            } else if (i == 38) {
                roads[i].setLocation(618+offsetx, 355+offsety);
            } else if (i == 39) {
                roads[i].setLocation(563+offsetx, 323+offsety);
            } else if (i == 40) {
                roads[i].setLocation(584+offsetx, 274+offsety);
            } else if (i == 41) {
                roads[i].setLocation(504+offsetx, 298+offsety);
            }
            
        }
    }
    
    private void createTextLabel(){
        textLabel = new JLabel("");
        textLabel.setFont(new Font("fantasy", Font.BOLD, 25));
        textLabel.setForeground(Color.red);
        layeredPane.add(textLabel, new Integer(10));
        textLabel.setLocation(120, 1);
        textLabel.setSize(textLabel.getPreferredSize());
        //textLabel.setSize(WIDTH, WIDTH);
        textLabel.setVisible(true);
        
        
    }
    
    public void updateText(String text){
        textLabel.setText(text);
        textLabel.setSize(textLabel.getPreferredSize());
    }

    public void animationJoinSheeps(int x, int y, int terrain) {
        AnimationJoinSheeps anim = new AnimationJoinSheeps();

        layeredPane.add(anim, new Integer(5));
        anim.setVisible(true);
        anim.setLocation(x + 50, y + 50);
        anim.getRunner().start();
        activateSubMenuSheep(terrain, false);
    }

    private void animationJoinSheepSuccesfull(boolean end) {
        AnimationJoinSheepsAfter anim = new AnimationJoinSheepsAfter(end);

        layeredPane.add(anim, new Integer(5));
        anim.setVisible(true);
        anim.setLocation(this.getWidth() / 2, this.getHeight() / 2);
        anim.getRunner().start();
    }

    void animationMoveSheep(int x, int y, int terrain, int cont) {

    }

    public void activateSubMenuSheep(int i, boolean val) {
        jbuttonMoveSheep[i].setVisible(val);
        jbuttonJoinSheeps[i].setVisible(val);
        jbuttonKillSheep[i].setVisible(val);
    }

    private void setBackGroundInvisible(JButton comp) {
        comp.setBorderPainted(false);
        comp.setFocusPainted(false);
        comp.setContentAreaFilled(false);
    }

    public void activateWolf(int terrain) {
        jlabelWolf[wolfActive].setVisible(false);
        wolfActive = terrain;
        jlabelWolf[terrain].setVisible(true);
    }

    public void activateBlackSheep(int terrain) {
        jlabelBlackSheep[blackSheepActive].setVisible(false);
        blackSheepActive = terrain;
        jlabelBlackSheep[terrain].setVisible(true);
    }

    private void activateSheep(int terrain) {

        int i = 0;

        for (ViewAnimal ele : animals) {
            if (ele.getPosition() == terrain) {
                i++;
            }
        }

        jbuttonSheeps[terrain].setnumber(i);
    }

    public void clickAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setNickname() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void errorMessage(String message) {
        //caso in cui l'accoppiamento non è andato a buon fine
        if (waitingForAddAnimal == true) {
            animationJoinSheepSuccesfull(false);
        }
    }

    public void placeShepard(int idShepard) {
        tempShepard = idShepard;
        state = GUIDinamicState.PLACESHEPARD;
        updateText("Posizionare Pastore su Strada");
        
    }

    public void refreshMoveAnimal(int idAnimal, int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshAddAnimal(int idAnimal, int idTerrain, String animalType) {
        if (waitingForAddAnimal) {
            waitingForAddAnimal = false;
            animationJoinSheepSuccesfull(true);
        }
        
        if (TypeAnimal.WOLF.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-2, 18));
            activateWolf(18);
        } else if (TypeAnimal.BLACK_SHEEP.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-1, 18));
            activateBlackSheep(18);
        } else if (TypeAnimal.WHITE_SHEEP.toString().equals(animalType)
                || TypeAnimal.RAM.toString().equals(animalType)
                || TypeAnimal.LAMB.toString().equals(animalType)) {
            animals.add(new ViewAnimal(idAnimal, idTerrain, animalType));
            activateSheep(idTerrain);
        }
    }

    public void refreshKillAnimal(int idAnimal) {

    }

    public void refreshTransformAnimal(int idAnimal, String kind) {

    }

    public void refreshCard(String typeOfTerrain, boolean isSold) {

    }

    public void refreshCoin(int coins, boolean addCoin) {

    }

    public void refreshAddShepard(int idShepard, int road) {
        if(idShepard == tempShepard){            
            ViewShepard shep = new ViewShepard(idShepard, road);
            roads[road].setShepard(idShepard);
            shepards.add(shep);
            //per evitare accavallamenti
            tempShepard = -1;
        }
        else{
            roads[road].setShepard(idShepard);
        }
    }

    public void refreshMoveShepard(int idShepard, int road) {

    }

    public GUIDinamicState getGUIDinamicState() {
        return state;
    }

    public void setGUIDinamicState(GUIDinamicState state) {
        this.state = state;
    }

    public int getSubMenuOpen() {
        return subMenuOpen;
    }

    public void setSubMenuOpen(int subMenuOpen) {
        this.subMenuOpen = subMenuOpen;
    }

    public void messageText(String message) {

    }

    void joinSheeps() {

    }

    public void selectedMoveSheep() {

    }

    public void sendMoveSheep(int terrain) {
        //    connectionClient.moveSheep(sheepSelected, terrain);
    }

    public void sendJoinSheeps(int terrain) {
        waitingForAddAnimal = true;
        connectionClient.joinSheep(terrain);
    }

    public void sendKillSheep() {

    }

  

    public int getTempShepard() {
        return tempShepard;
    }

    public void sendPlaceShepard(int road){
        connectionClient.placeShepard(road);
    }

    
}
