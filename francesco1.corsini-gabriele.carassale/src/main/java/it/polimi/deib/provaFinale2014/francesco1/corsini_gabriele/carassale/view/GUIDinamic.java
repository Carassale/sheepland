package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
    private JButton[] jbuttonJoinSheeps = new JButton[19];
    private JButton[] jbuttonMoveSheep = new JButton[19];
    private JLabel[] jlabelBlackSheep = new JLabel[19];
    private JLabel[] jlabelWolf = new JLabel[19];
    private JLayeredPane layeredPane;
    private ImageIcon imageIcon = new ImageIcon("src\\main\\resources\\joinSheeps.png");
    private ImageIcon plainCards[] = new ImageIcon[6];
    private ImageIcon forestCards[] = new ImageIcon[6];
    private ImageIcon riverCards[] = new ImageIcon[6];
    private ImageIcon desertCards[] = new ImageIcon[6];
    private ImageIcon mountainCards[] = new ImageIcon[6];
    private ImageIcon fieldCards[] = new ImageIcon[6];

    private ArrayList<ViewAnimal> animals = new ArrayList<ViewAnimal>();

    //se -1 vuol dire che nussun submenu è aperto
    private int subMenuOpen = -1;
    private int wolfActive = 18, blackSheepActive = 18;

    public GUIDinamic(ConnectionClient connectionClient) {
        this.connectionClient = connectionClient;
        createAndShowGUI();

    }

    private void createAndShowGUI() {
        Dimension dim = new Dimension(950, 650);
        setSize(dim);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(950, 650));

        createTable();
        createSheepButtons();
        createSheepSubMenu();
        createBlackSheepLabels();
        createWolfLabels();
        createCards();

        state = state.WAITINGFORPLAYER;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Display the window.
        pack();
        setVisible(true);
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
            jbuttonSheeps[i] = new DinamicSheepButton();
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
            jbuttonSheeps[i].setnumber(2);
        }
    }

    private void createSheepSubMenu() {
        ImageIcon iconKillsheep = new ImageIcon("src\\main\\resources\\killSheep.png");
        ImageIcon iconJoinSheeps = imageIcon;
        ImageIcon iconMoveSheep = new ImageIcon("src\\main\\resources\\runningSheep2.png");
        for (int i = 0; i <= 18; i++) {
            jbuttonKillSheep[i] = new JButton(iconKillsheep);
            jbuttonJoinSheeps[i] = new JButton(iconJoinSheeps);
            jbuttonMoveSheep[i] = new JButton(iconMoveSheep);
            setBackGroundInvisible(jbuttonKillSheep[i]);
            setBackGroundInvisible(jbuttonJoinSheeps[i]);
            setBackGroundInvisible(jbuttonMoveSheep[i]);
            jbuttonJoinSheeps[i].addActionListener(new GUIDinamicSheepSubMenuListener(this, i, TypeAction.JOIN_SHEEP.toString()));
            jbuttonKillSheep[i].addActionListener(new GUIDinamicSheepSubMenuListener(this, i, TypeAction.KILL_SHEEP.toString()));
            jbuttonMoveSheep[i].addActionListener(new GUIDinamicSheepSubMenuListener(this, i, TypeAction.MOVE_SHEEP.toString()));
            layeredPane.add(jbuttonKillSheep[i], new Integer(4));
            layeredPane.add(jbuttonJoinSheeps[i], new Integer(4));
            layeredPane.add(jbuttonMoveSheep[i], new Integer(4));
            jbuttonKillSheep[i].setSize(70, 70);
            jbuttonJoinSheeps[i].setSize(70, 70);
            jbuttonMoveSheep[i].setSize(70, 70);

            Point p = jbuttonSheeps[i].getLocation();
            jbuttonMoveSheep[i].setLocation(p.x - 70, p.y - 35);
            jbuttonJoinSheeps[i].setLocation(p.x, p.y - 55);
            jbuttonKillSheep[i].setLocation(p.x + 70, p.y - 35);
            jbuttonMoveSheep[i].setVisible(false);
            jbuttonJoinSheeps[i].setVisible(false);
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

    public void joinSheeps(int terrain) {
        try {
            JPanel panelJoinSheeps = new GUIDinamicPanel("src\\main\\resources\\joinSheeps2.png");
            panelJoinSheeps.setLocation(jbuttonJoinSheeps[terrain].getLocation());
            Dimension dim2 = new Dimension(70, 70);
            panelJoinSheeps.setSize(dim2);
            panelJoinSheeps.add(panel, new Integer(3));

            new AnimationJoinSheeps(this, panelJoinSheeps);
        } catch (IOException ex) {
            Logger.getLogger(GUIDinamic.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        //CONT DA QUI
        int i = 0;
        for (int j = 0; j <= 5; j++) {

        }

        for (ViewAnimal ele : animals) {
            if (ele.getPosition() == terrain) {
                i++;
            }
        }
        if (i <= 4) {

        }

    }

    private void joinSheepAnimation() {

    }

    public void clickAction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setNickname() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void errorMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void placeShepard(int idShepard) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshMoveAnimal(int idAnimal, int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshAddAnimal(int idTerrain, String animalType) {
        int i = animals.size();
        if (TypeAnimal.WOLF.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-2, 18));
            activateWolf(18);
        } else if (TypeAnimal.BLACK_SHEEP.toString().equals(animalType)) {
            animals.add(new ViewAnimal(-1, 18));
            activateBlackSheep(18);
        } else if (TypeAnimal.WHITE_SHEEP.toString().equals(animalType)
                || TypeAnimal.RAM.toString().equals(animalType)
                || TypeAnimal.LAMB.toString().equals(animalType)) {
            //CONT DA QUI
            animals.add(new ViewAnimal(i, idTerrain, animalType));
            activateSheep(idTerrain);

        }
    }

    public void refreshKillAnimal(int idAnimal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshTransformAnimal(int idAnimal, String kind) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshCard(String typeOfTerrain, boolean isSold) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshCoin(int coins, boolean addCoin) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshAddShepard(int idShepard, int road) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshMoveShepard(int idShepard, int road) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
