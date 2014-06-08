/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lelino
 */
public class StatusPlayerTest {

    StatusPlayer sp;

    public StatusPlayerTest() {
        sp = new StatusPlayer(StatusMessage.TYPE_RMI.toString(), 0, 1, true);
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
     * Test of getTypeConnection method, of class StatusPlayer.
     */
    @Test
    public void testGetTypeConnection() {
        assertEquals(StatusMessage.TYPE_RMI.toString(), sp.getTypeConnection());
    }

    /**
     * Test of getIdGame method, of class StatusPlayer.
     */
    @Test
    public void testGetIdGame() {
        assertEquals(0, sp.getIdGame());
    }

    /**
     * Test of getIdPlayer method, of class StatusPlayer.
     */
    @Test
    public void testGetIdPlayer() {
        assertEquals(1, sp.getIdPlayer());
    }

    /**
     * Test of isOnLine method, of class StatusPlayer.
     */
    @Test
    public void testIsOnLine() {
        assertEquals(true, sp.isOnLine());
    }

    /**
     * Test of setOnLineStatus method, of class StatusPlayer.
     */
    @Test
    public void testSetOnLineStatus() {
        sp.setOnLineStatus(false);
        assertEquals(false, sp.isOnLine());
    }

}
