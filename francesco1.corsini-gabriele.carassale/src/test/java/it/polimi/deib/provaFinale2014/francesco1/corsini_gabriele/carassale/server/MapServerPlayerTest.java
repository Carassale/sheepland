/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lelino
 */
public class MapServerPlayerTest {

    MapServerPlayer map;

    public MapServerPlayerTest() {
        map = new MapServerPlayer();
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
     * Test of addPlayer method, of class MapServerPlayer.
     */
    @Test
    public void testAddPlayer() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);

        assertEquals(true, map.existPlayer(nickname));
    }

    /**
     * Test of removePlayer method, of class MapServerPlayer.
     */
    @Test
    public void testRemovePlayer() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);
        map.removePlayer(nickname);

        assertEquals(false, map.existPlayer(nickname));
    }

    /**
     * Test of getTypeConnection method, of class MapServerPlayer.
     */
    @Test
    public void testGetTypeConnection() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);

        assertEquals(StatusMessage.TYPE_RMI.toString(), map.getTypeConnection(nickname));
    }

    /**
     * Test of getIdGame method, of class MapServerPlayer.
     */
    @Test
    public void testGetIdGame() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);

        assertEquals(0, map.getIdGame(nickname));
    }

    /**
     * Test of getIdPlayer method, of class MapServerPlayer.
     */
    @Test
    public void testGetIdPlayer() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);

        assertEquals(1, map.getIdPlayer(nickname));
    }

    /**
     * Test of isOnLine method, of class MapServerPlayer.
     */
    @Test
    public void testIsOnLine() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);

        assertEquals(true, map.isOnLine(nickname));
    }

    /**
     * Test of setOnLine method, of class MapServerPlayer.
     */
    @Test
    public void testSetOnLine() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);
        map.setOnLine(nickname, false);

        assertEquals(false, map.isOnLine(nickname));
    }

    /**
     * Test of existPlayer method, of class MapServerPlayer.
     */
    @Test
    public void testExistPlayer() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);

        assertEquals(true, map.existPlayer(nickname));
    }

    /**
     * Test of existPlayer method, of class MapServerPlayer.
     */
    @Test
    public void testExistPlayer2() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);
        map.removePlayer(nickname);

        assertEquals(false, map.existPlayer(nickname));
    }
    
    /**
     * Test of existPlayer method, of class MapServerPlayer.
     */
    @Test
    public void testExistPlayer3() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);
        map.removePlayer(nickname);

        assertEquals(false, map.existPlayer(""));
    }

    /**
     * Test of isTypeConnectionSocket method, of class MapServerPlayer.
     */
    @Test
    public void testIsTypeConnectionSocket() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_SOCKET.toString(), 0, 1);

        assertEquals(true, map.isTypeConnectionSocket(nickname));
    }

    /**
     * Test of isTypeConnectionSocket method, of class MapServerPlayer.
     */
    @Test
    public void testIsTypeConnectionSocket2() {
        String nickname = "mario";
        map.addPlayer(nickname, StatusMessage.TYPE_RMI.toString(), 0, 1);

        assertEquals(false, map.isTypeConnectionSocket(nickname));
    }

}
