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
public class ShepardTest {

    private Map map;
    private Shepard shepard;

    @Before
    public void setUp() {
        map = new Map();
        shepard = new Shepard(map.getRoads().get(1));

    }

    /**
     * Test of getPosition method, of class Shepard.
     */
    @Test
    public void testGetPosition() {
        assertSame(map.getRoads().get(1), shepard.getPosition());
    }

    /**
     * Test of setPosition method, of class Shepard.
     */
    @Test
    public void testSetPosition() {
        shepard.setPosition(map.getRoads().get(13));
        assertSame(map.getRoads().get(13), shepard.getPosition());
    }

    /**
     * Test of isExpensiveMove method, of class Shepard.
     */
    @Test
    public void testIsExpensiveMove() {
        assertTrue(shepard.isExpensiveMove(map.getRoads().get(13)));
    }

}
