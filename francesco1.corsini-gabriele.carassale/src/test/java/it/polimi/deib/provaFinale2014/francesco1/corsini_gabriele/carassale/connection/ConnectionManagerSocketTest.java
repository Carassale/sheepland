/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerSocketTest {

    public ConnectionManagerSocketTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of doAction method, of class ConnectionManagerSocket.
     */
    @Test
    public void testDoAction() {
        System.out.println("doAction");
        ConnectionManagerSocket instance = null;
        boolean expResult = false;
        boolean result = instance.doAction();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayerConnections method, of class ConnectionManagerSocket.
     */
    @Test
    public void testGetPlayerConnections() {
        System.out.println("getPlayerConnections");
        ConnectionManagerSocket instance = null;
        ArrayList<PlayerConnectionSocket> expResult = null;
        ArrayList<PlayerConnectionSocket> result = instance.getPlayerConnections();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of wakeUpPlayer method, of class ConnectionManagerSocket.
     */
    @Test
    public void testWakeUpPlayer() {
        System.out.println("wakeUpPlayer");
        PlayerConnectionSocket pcs = null;
        ConnectionManagerSocket instance = null;
        instance.wakeUpPlayer(pcs);
        fail("The test case is a prototype.");
    }

    /**
     * Test of refreshGame4AllPlayer method, of class ConnectionManagerSocket.
     */
    @Test
    public void testRefreshGame4AllPlayer() {
        System.out.println("refreshGame4AllPlayer");
        ConnectionManagerSocket instance = null;
        instance.refreshGame4AllPlayer();
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNickName method, of class ConnectionManagerSocket.
     */
    @Test
    public void testSetNickName() {
        System.out.println("setNickName");
        ConnectionManagerSocket instance = null;
        instance.setNickName();
        fail("The test case is a prototype.");
    }

    /**
     * Test of nextPlayerConnections method, of class ConnectionManagerSocket.
     */
    @Test
    public void testNextPlayerConnections() {
        System.out.println("nextPlayerConnections");
        ConnectionManagerSocket instance = null;
        instance.nextPlayerConnections();
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveShepard method, of class ConnectionManagerSocket.
     */
    @Test
    public void testMoveShepard() throws Exception {
        System.out.println("moveShepard");
        ConnectionManagerSocket instance = null;
        boolean expResult = false;
        boolean result = instance.moveShepard();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveSheep method, of class ConnectionManagerSocket.
     */
    @Test
    public void testMoveSheep() throws Exception {
        System.out.println("moveSheep");
        ConnectionManagerSocket instance = null;
        boolean expResult = false;
        boolean result = instance.moveSheep();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of buyCard method, of class ConnectionManagerSocket.
     */
    @Test
    public void testBuyCard() throws Exception {
        System.out.println("buyCard");
        ConnectionManagerSocket instance = null;
        boolean expResult = false;
        boolean result = instance.buyCard();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of killSheep method, of class ConnectionManagerSocket.
     */
    @Test
    public void testKillSheep() throws Exception {
        System.out.println("killSheep");
        ConnectionManagerSocket instance = null;
        boolean expResult = false;
        boolean result = instance.killSheep();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of joinSheep method, of class ConnectionManagerSocket.
     */
    @Test
    public void testJoinSheep() throws Exception {
        System.out.println("joinSheep");
        ConnectionManagerSocket instance = null;
        boolean expResult = false;
        boolean result = instance.joinSheep();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

}
