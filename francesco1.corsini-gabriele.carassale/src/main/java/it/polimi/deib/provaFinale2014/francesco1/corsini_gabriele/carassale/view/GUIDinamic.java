package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
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
    private JButton[] jbuttonSheeps = new JButton[19];
    private JButton[] jbuttonKillSheep = new JButton[19];
    private JButton[] jbuttonJoinSheeps = new JButton[19];
    private JButton[] jbuttonMoveSheep = new JButton[19];
    private JLayeredPane layeredPane;
    private ImageIcon imageIcon = new ImageIcon("src\\main\\resources\\joinSheeps.png");
    private ImageIcon plainCards[] = new ImageIcon[6];
    private ImageIcon forestCards[] = new ImageIcon[6];
    private ImageIcon riverCards[] = new ImageIcon[6];
    private ImageIcon desertCards[] = new ImageIcon[6];
    private ImageIcon mountainCards[] = new ImageIcon[6];
    private ImageIcon fieldCards[] = new ImageIcon[6];

    //se -1 vuol dire che nussun submenu è aperto
    private int subMenuOpen = -1;

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
        ImageIcon icon = new ImageIcon("src\\main\\resources\\sheepSmall.png");
        for (int i = 0; i <= 18; i++) {
            jbuttonSheeps[i] = new JButton(icon);
            setBackGroundInvisible(jbuttonSheeps[i]);
            jbuttonSheeps[i].addActionListener(new GUIDinamicSheepListener(this, i));
            layeredPane.add(jbuttonSheeps[i], new Integer(2));
            jbuttonSheeps[i].setSize(100, 100);
            if (i == 0) {// la prima è la x, la seconda è la y(parte dall'alto)
                jbuttonSheeps[i].setLocation(165, 110);
            } else if (i == 1) {
                jbuttonSheeps[i].setLocation(220, 280);
            } else if (i == 2) {
                jbuttonSheeps[i].setLocation(340, 350);
            } else if (i == 3) {
                jbuttonSheeps[i].setLocation(220, 410);
            } else if (i == 4) {
                jbuttonSheeps[i].setLocation(320, 460);
            } else if (i == 5) {
                jbuttonSheeps[i].setLocation(450, 380);
            } else if (i == 6) {
                jbuttonSheeps[i].setLocation(440, 500);
            } else if (i == 7) {
                jbuttonSheeps[i].setLocation(570, 460);
            } else if (i == 8) {
                jbuttonSheeps[i].setLocation(570, 330);
            } else if (i == 9) {
                jbuttonSheeps[i].setLocation(660, 380);
            } else if (i == 10) {
                jbuttonSheeps[i].setLocation(690, 270);
            } else if (i == 11) {
                jbuttonSheeps[i].setLocation(585, 220);
            } else if (i == 12) {
                jbuttonSheeps[i].setLocation(690, 165);
            } else if (i == 13) {
                jbuttonSheeps[i].setLocation(600, 120);
            } else if (i == 14) {
                jbuttonSheeps[i].setLocation(465, 165);
            } else if (i == 15) {
                jbuttonSheeps[i].setLocation(500, 60);
            } else if (i == 16) {
                jbuttonSheeps[i].setLocation(330, 100);
            } else if (i == 17) {
                jbuttonSheeps[i].setLocation(340, 200);
            } else if (i == 18) {
                jbuttonSheeps[i].setLocation(450, 270);
            }

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
            jbuttonJoinSheeps[i].addActionListener(new GUIDinamicSheepSubMenuListener(this, i, "JoinSheeps"));
            jbuttonKillSheep[i].addActionListener(new GUIDinamicSheepSubMenuListener(this, i, "KillSheep"));
            jbuttonMoveSheep[i].addActionListener(new GUIDinamicSheepSubMenuListener(this, i, "MoveSheep"));
            layeredPane.add(jbuttonKillSheep[i], new Integer(3));
            layeredPane.add(jbuttonJoinSheeps[i], new Integer(3));
            layeredPane.add(jbuttonMoveSheep[i], new Integer(3));
            jbuttonKillSheep[i].setSize(70, 70);
            jbuttonJoinSheeps[i].setSize(70, 70);
            jbuttonMoveSheep[i].setSize(70, 70);

            Point p = jbuttonSheeps[i].getLocation();
            jbuttonMoveSheep[i].setLocation(p.x - 70, p.y - 50);
            jbuttonJoinSheeps[i].setLocation(p.x, p.y - 70);
            jbuttonKillSheep[i].setLocation(p.x + 70, p.y - 50);
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
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, "Plain"));
            } else if (i == 1) {
                cards[i] = new JButton(forestCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, "Forest"));
            } else if (i == 2) {
                cards[i] = new JButton(riverCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, "River"));
            } else if (i == 3) {
                cards[i] = new JButton(desertCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, "Desert"));
            } else if (i == 4) {
                cards[i] = new JButton(mountainCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, "Mountain"));
            } else if (i == 5) {
                cards[i] = new JButton(fieldCards[0]);
                cards[i].addActionListener(new GUIDinamicCardListener(this, i, "Field"));
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
            layeredPane.add(label, new Integer(1));
            if (i == 0) {
                label.setLocation(185, 130);
            }
            else if (i == 1) {
                label.setLocation(240, 260);
            }
            label.setSize(50,50);
            label.setVisible(true);
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

    public void refreshAddAnimal(int idTerrain, String kind) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
