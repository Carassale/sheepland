package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Fefa
 */
public class GUISwingStaticTest {
    
    private static GUISwingStatic gui;
    private ViewAnimal wolf,blacksheep,sheep;
    private GUIState guiState;
    
    @BeforeClass
    public void setUp() {
        gui = new GUISwingStatic();
    }
    
    @After
    public void tearDown() {
        gui.setGUIState(GUIState.WAITINGFOROTHERPLAYER);
    }

    /**
     * Test of clickAction method, of class GUISwingStatic.
     */
    @Test
    public void testClickAction() {
        assertSame(gui.getGUIState(),GUIState.WAITINGFOROTHERPLAYER);
        
        //gui.clickAction();
        
    }

    /*
    
    */
}
