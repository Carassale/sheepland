/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Corsini
 */
public class RoadTest {

    private static Road road;
    private static Terrain terrain0, terrain1, terrain2;

    @Before
    public void setUp() {
        road = new Road(1);
        terrain0 = new Terrain();
        terrain1 = new Terrain();
        terrain2 = new Terrain();

        try {
            road.connectTerrainRoad(terrain0);
            road.connectTerrainRoad(terrain1);
        } catch (TerrainBoundariesExeption ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
        }

    }

    /**
     * Test of getAdjacentTerrain1 method, of class Road.
     */
    @Test
    public void testGetAdjacentTerrain1() {
        assertEquals(terrain0, road.getAdjacentTerrain1());
    }

    /**
     * Test of getAdjacentTerrain2 method, of class Road.
     */
    @Test
    public void testGetAdjacentTerrain2() {
        assertEquals(terrain1, road.getAdjacentTerrain2());
    }

    /**
     * Test of hasFence method, of class Road.
     */
    @Test
    public void testHasFence() {
        assertFalse(road.hasFence());
    }

    /**
     * Test of setFence method, of class Road.
     */
    @Test
    public void testSetFence() {
        road.setFence(true);
        assertTrue(road.hasFence());
    }

    /**
     * Test of hasShepherd method, of class Road.
     */
    @Test
    public void testHasShepherd() {
        assertFalse(road.hasShepherd());
    }

    /**
     * Test of setHasShepherd method, of class Road.
     */
    @Test
    public void testSetShepherd() {
        road.setHasShepherd(true);
        assertTrue(road.hasShepherd());
    }

    /**
     * Test of getRoadNumber method, of class Road.
     */
    @Test
    public void testGetRoadNumber() {
        road.setRoadNumber(1);
        assertEquals(1, road.getRoadNumber());
    }

    /**
     * Test of connectTerrainRoad method, of class Road.
     *
     * @throws TerrainBoundariesExeption
     */
    @Test(expected = TerrainBoundariesExeption.class)
    public void testConnectTerrainRoad() throws TerrainBoundariesExeption {
        road.connectTerrainRoad(terrain2);
    }

}
