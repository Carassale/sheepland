/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
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
        //la nascondo
        gui.setVisible(false);
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

    /**
     * Test of activateActions method, of class GUISwingStatic.
     */
    @Test
    public void testActivateActions() {
        System.out.println("activateActions");
        boolean val = false;
        GUISwingStatic instance = null;
        instance.activateActions(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activateRoads method, of class GUISwingStatic.
     */
    @Test
    public void testActivateRoads() {
        System.out.println("activateRoads");
        boolean val = false;
        GUISwingStatic instance = null;
        instance.activateRoads(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activateTerrains method, of class GUISwingStatic.
     */
    @Test
    public void testActivateTerrains() {
        System.out.println("activateTerrains");
        boolean val = false;
        GUISwingStatic instance = null;
        instance.activateTerrains(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activateTerrainType method, of class GUISwingStatic.
     */
    @Test
    public void testActivateTerrainType() {
        System.out.println("activateTerrainType");
        boolean val = false;
        GUISwingStatic instance = null;
        instance.activateTerrainType(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activateSheepSelection method, of class GUISwingStatic.
     */
    @Test
    public void testActivateSheepSelection() {
        System.out.println("activateSheepSelection");
        boolean val = false;
        int terrain = 0;
        GUISwingStatic instance = null;
        instance.activateSheepSelection(val, terrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activateDropDown method, of class GUISwingStatic.
     */
    @Test
    public void testActivateDropDown() {
        System.out.println("activateDropDown");
        boolean val = false;
        GUISwingStatic instance = null;
        instance.activateDropDown(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of activateShepardSelection method, of class GUISwingStatic.
     */
    @Test
    public void testActivateShepardSelection() {
        System.out.println("activateShepardSelection");
        boolean val = false;
        GUISwingStatic instance = null;
        instance.activateShepardSelection(val);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshCoin method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshCoin() {
        System.out.println("refreshCoin");
        int coins = 0;
        boolean addCoins = false;
        GUISwingStatic instance = null;
        instance.refreshCoin(coins, addCoins);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshCard method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshCard() {
        System.out.println("refreshCard");
        String typeOfTerrain = "";
        boolean isSold = false;
        GUISwingStatic instance = null;
        instance.refreshCard(typeOfTerrain, isSold);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshMoveAnimal method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshMoveAnimal() {
        System.out.println("refreshMoveAnimal");
        int id = 0;
        int terrain = 0;
        GUISwingStatic instance = null;
        instance.refreshMoveAnimal(id, terrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshAddAnimal method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshAddAnimal() {
        System.out.println("refreshAddAnimal");
        int idAnimal = 0;
        int terrain = 0;
        String animalType = "";
        GUISwingStatic instance = null;
        instance.refreshAddAnimal(idAnimal, terrain, animalType);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshKillAnimal method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshKillAnimal() {
        System.out.println("refreshKillAnimal");
        int id = 0;
        GUISwingStatic instance = null;
        instance.refreshKillAnimal(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshTransformAnimal method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshTransformAnimal() {
        System.out.println("refreshTransformAnimal");
        int id = 0;
        String kind = "";
        GUISwingStatic instance = null;
        instance.refreshTransformAnimal(id, kind);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshAddShepard method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshAddShepard_int_int() {
        System.out.println("refreshAddShepard");
        int id = 0;
        int road = 0;
        GUISwingStatic instance = null;
        instance.refreshAddShepard(id, road);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshMoveShepard method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshMoveShepard() {
        System.out.println("refreshMoveShepard");
        int id = 0;
        int road = 0;
        GUISwingStatic instance = null;
        instance.refreshMoveShepard(id, road);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of declareWinner method, of class GUISwingStatic.
     */
    @Test
    public void testDeclareWinner() {
        System.out.println("declareWinner");
        boolean isWinner = false;
        GUISwingStatic instance = null;
        instance.declareWinner(isWinner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of errorMessage method, of class GUISwingStatic.
     */
    @Test
    public void testErrorMessage() {
        System.out.println("errorMessage");
        String message = "";
        GUISwingStatic instance = null;
        instance.errorMessage(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getGUIState method, of class GUISwingStatic.
     */
    @Test
    public void testGetGUIState() {
        System.out.println("getGUIState");
        GUISwingStatic instance = null;
        GUIState expResult = null;
        GUIState result = instance.getGUIState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGUIState method, of class GUISwingStatic.
     */
    @Test
    public void testSetGUIState() {
        System.out.println("setGUIState");
        GUIState state = null;
        GUISwingStatic instance = null;
        instance.setGUIState(state);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeShepard method, of class GUISwingStatic.
     */
    @Test
    public void testPlaceShepard() {
        System.out.println("placeShepard");
        int id = 0;
        GUISwingStatic instance = null;
        instance.placeShepard(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendBuyCard method, of class GUISwingStatic.
     */
    @Test
    public void testSendBuyCard() {
        System.out.println("sendBuyCard");
        String terrainkind = "";
        GUISwingStatic instance = null;
        instance.sendBuyCard(terrainkind);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendPlaceShepard method, of class GUISwingStatic.
     */
    @Test
    public void testSendPlaceShepard() {
        System.out.println("sendPlaceShepard");
        int road = 0;
        GUISwingStatic instance = null;
        instance.sendPlaceShepard(road);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMoveSheep method, of class GUISwingStatic.
     */
    @Test
    public void testSendMoveSheep() {
        System.out.println("sendMoveSheep");
        int terrain = 0;
        GUISwingStatic instance = null;
        instance.sendMoveSheep(terrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendMoveShepard method, of class GUISwingStatic.
     */
    @Test
    public void testSendMoveShepard() {
        System.out.println("sendMoveShepard");
        int roadTo = 0;
        GUISwingStatic instance = null;
        instance.sendMoveShepard(roadTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLAction2 method, of class GUISwingStatic.
     */
    @Test
    public void testSetLAction2() {
        System.out.println("setLAction2");
        JLabel lAction2 = null;
        GUISwingStatic instance = null;
        instance.setLAction2(lAction2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLAction2 method, of class GUISwingStatic.
     */
    @Test
    public void testGetLAction2() {
        System.out.println("getLAction2");
        GUISwingStatic instance = null;
        JLabel expResult = null;
        JLabel result = instance.getLAction2();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendJoinSheeps method, of class GUISwingStatic.
     */
    @Test
    public void testSendJoinSheeps() {
        System.out.println("sendJoinSheeps");
        int terrain = 0;
        GUISwingStatic instance = null;
        instance.sendJoinSheeps(terrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendKillSheep method, of class GUISwingStatic.
     */
    @Test
    public void testSendKillSheep() {
        System.out.println("sendKillSheep");
        int terrain = 0;
        GUISwingStatic instance = null;
        instance.sendKillSheep(terrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTempTerrain method, of class GUISwingStatic.
     */
    @Test
    public void testGetTempTerrain() {
        System.out.println("getTempTerrain");
        GUISwingStatic instance = null;
        int expResult = 0;
        int result = instance.getTempTerrain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTempTerrain method, of class GUISwingStatic.
     */
    @Test
    public void testSetTempTerrain() {
        System.out.println("setTempTerrain");
        int tempTerrain = 0;
        GUISwingStatic instance = null;
        instance.setTempTerrain(tempTerrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTempIdShepard method, of class GUISwingStatic.
     */
    @Test
    public void testGetTempIdShepard() {
        System.out.println("getTempIdShepard");
        GUISwingStatic instance = null;
        int expResult = 0;
        int result = instance.getTempIdShepard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTempIdShepard method, of class GUISwingStatic.
     */
    @Test
    public void testSetTempIdShepard() {
        System.out.println("setTempIdShepard");
        int tempIdShepard = 0;
        GUISwingStatic instance = null;
        instance.setTempIdShepard(tempIdShepard);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTempIdSheep method, of class GUISwingStatic.
     */
    @Test
    public void testGetTempIdSheep() {
        System.out.println("getTempIdSheep");
        GUISwingStatic instance = null;
        int expResult = 0;
        int result = instance.getTempIdSheep();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTempIdSheep method, of class GUISwingStatic.
     */
    @Test
    public void testSetTempIdSheep() {
        System.out.println("setTempIdSheep");
        int tempIdSheep = 0;
        GUISwingStatic instance = null;
        instance.setTempIdSheep(tempIdSheep);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTempRoad method, of class GUISwingStatic.
     */
    @Test
    public void testGetTempRoad() {
        System.out.println("getTempRoad");
        GUISwingStatic instance = null;
        int expResult = 0;
        int result = instance.getTempRoad();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTempRoad method, of class GUISwingStatic.
     */
    @Test
    public void testSetTempRoad() {
        System.out.println("setTempRoad");
        int tempRoad = 0;
        GUISwingStatic instance = null;
        instance.setTempRoad(tempRoad);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConnectionClient method, of class GUISwingStatic.
     */
    @Test
    public void testGetConnectionClient() {
        System.out.println("getConnectionClient");
        GUISwingStatic instance = null;
        ConnectionClient expResult = null;
        ConnectionClient result = instance.getConnectionClient();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSheepDropDown method, of class GUISwingStatic.
     */
    @Test
    public void testGetSheepDropDown() {
        System.out.println("getSheepDropDown");
        GUISwingStatic instance = null;
        JComboBox expResult = null;
        JComboBox result = instance.getSheepDropDown();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of messageText method, of class GUISwingStatic.
     */
    @Test
    public void testMessageText() {
        System.out.println("messageText");
        String message = "";
        GUISwingStatic instance = null;
        instance.messageText(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshAddShepard method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshAddShepard_3args() {
        System.out.println("refreshAddShepard");
        int idShepard = 0;
        int road = 0;
        boolean isMine = false;
        GUISwingStatic instance = null;
        instance.refreshAddShepard(idShepard, road, isMine);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshWinner method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshWinner() {
        System.out.println("refreshWinner");
        int position = 0;
        int score = 0;
        GUISwingStatic instance = null;
        instance.refreshWinner(position, score);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshAddFence method, of class GUISwingStatic.
     */
    @Test
    public void testRefreshAddFence() {
        System.out.println("refreshAddFence");
        int idRoad = 0;
        GUISwingStatic instance = null;
        instance.refreshAddFence(idRoad);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSheepSelected method, of class GUISwingStatic.
     */
    @Test
    public void testSetSheepSelected() {
        System.out.println("setSheepSelected");
        int sheepSelected = 0;
        GUISwingStatic instance = null;
        instance.setSheepSelected(sheepSelected);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setShepardSelected method, of class GUISwingStatic.
     */
    @Test
    public void testSetShepardSelected() {
        System.out.println("setShepardSelected");
        int shepardSelected = 0;
        GUISwingStatic instance = null;
        instance.setShepardSelected(shepardSelected);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
