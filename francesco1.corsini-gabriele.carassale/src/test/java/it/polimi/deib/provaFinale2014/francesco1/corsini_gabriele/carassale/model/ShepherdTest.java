/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Corsini
 */
public class ShepherdTest {

    private Map map;
    private Shepherd shepherd;

    @Before
    public void setUp() {
        map = new Map();
        shepherd = new Shepherd(map.getRoads().get(1));

    }

    /**
     * Test of getPosition method, of class Shepherd.
     */
    @Test
    public void testGetPosition() {
        assertSame(map.getRoads().get(1), shepherd.getPosition());
    }

    /**
     * Test of setPosition method, of class Shepherd.
     */
    @Test
    public void testSetPosition() {
        shepherd.setPosition(map.getRoads().get(13));
        assertSame(map.getRoads().get(13), shepherd.getPosition());
    }

    /**
     * Test of isExpensiveMove method, of class Shepherd.
     */
    @Test
    public void testIsExpensiveMove() {
        assertTrue(shepherd.isExpensiveMove(map.getRoads().get(13)));
    }

}
