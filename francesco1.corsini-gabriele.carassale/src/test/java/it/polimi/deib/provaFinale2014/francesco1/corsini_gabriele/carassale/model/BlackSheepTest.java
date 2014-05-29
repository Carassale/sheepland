/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.WrongDiceNumberException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Fefa
 */
public class BlackSheepTest {

    private static Terrain terrain0, terrain1, terrain2, terrain3, terrain4, terrain5, terrain6;
    private static Road road1, road2, road3, road4, road5, road6;
    private static BlackSheep bsheep;

    @Before
    public void setUpClass() {
        terrain0 = new Terrain();
        terrain1 = new Terrain();
        terrain2 = new Terrain();
        terrain3 = new Terrain();
        terrain4 = new Terrain();
        terrain5 = new Terrain();
        terrain6 = new Terrain();
        terrain0.setID(0);
        terrain1.setID(1);
        terrain2.setID(2);
        terrain3.setID(3);
        terrain4.setID(4);
        terrain5.setID(5);
        terrain6.setID(6);

        road1 = new Road(1);
        road2 = new Road(2);
        road3 = new Road(3);
        road4 = new Road(4);
        road5 = new Road(5);
        road6 = new Road(6);

        try {
            road1.connectTerrainRoad(terrain0);
            road1.connectTerrainRoad(terrain1);
            road2.connectTerrainRoad(terrain0);
            road2.connectTerrainRoad(terrain2);
            road3.connectTerrainRoad(terrain0);
            road3.connectTerrainRoad(terrain3);
            road4.connectTerrainRoad(terrain0);
            road4.connectTerrainRoad(terrain4);
            road5.connectTerrainRoad(terrain0);
            road5.connectTerrainRoad(terrain5);
            road6.connectTerrainRoad(terrain0);
            road6.connectTerrainRoad(terrain6);
        } catch (TerrainBoundariesExeption ex) {

        }

        terrain0.getAdjacentRoads().add(road1);
        terrain0.getAdjacentRoads().add(road2);
        terrain0.getAdjacentRoads().add(road3);
        terrain0.getAdjacentRoads().add(road4);
        terrain0.getAdjacentRoads().add(road5);
        terrain0.getAdjacentRoads().add(road6);
        terrain1.getAdjacentRoads().add(road1);
        terrain2.getAdjacentRoads().add(road2);
        terrain3.getAdjacentRoads().add(road3);
        terrain4.getAdjacentRoads().add(road4);
        terrain5.getAdjacentRoads().add(road5);
        terrain6.getAdjacentRoads().add(road6);

        bsheep = new BlackSheep(terrain0);

    }

    /**
     * Test per vedere se è dove inizializzata
     */
    /**
     * Test per vedere se si muove quando si chiama funzione hasToMove
     */
    @Test
    public void testHasToMove() {
        road1.setRoadNumber(2);
        Road road;
        try {
            road = bsheep.hasToMove(2);
            assertSame(road1, road);
        } catch (WrongDiceNumberException ex) {
            Logger.getLogger(BlackSheepTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    public void stayWhereInizialized() {
        assertSame(terrain0, bsheep.getPosition());
    }

}
