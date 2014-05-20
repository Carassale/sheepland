/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fefa
 */
public class SheepTest {
    
    private static Sheep sheep;
    private static Terrain terrain0;
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @Before
    public void setUp() {
        terrain0 = new Terrain();
        sheep = new Sheep(terrain0, false);
        for(int i=0;i<4;i++)
            sheep.growUpOneTurn();
        
    }
    
    /**
     * Test of getAge method, of class Sheep.
     */
    @Test
    public void testGetAge() {
        assertEquals(4,sheep.getAge());
    }

    /**
     * Test of setAge method, of class Sheep.
     */
    @Test
    public void testSetAge() {
        sheep.setAge(0);
        assertEquals(0,sheep.getAge());
    }

    /**
     * Test of getSex and setSex methods, of class Sheep.
     */
    @Test
    public void testGetSexAndSetSex() {
        sheep.setSex("Female");
        assertEquals("Female",sheep.getSex());
    }


    /**
     * Test of growUpOneTurn method, of class Sheep.
     */
    @Test
    public void testGrowUpOneTurn() {
        sheep.setAge(0);
        sheep.growUpOneTurn();
        assertEquals(1,sheep.getAge());
    }

    /**
     * Test of isWhiteSheep method, of class Sheep.
     */
    @Test
    public void testIsWhiteSheep() {
        sheep.setSex("Female");
        assertTrue(sheep.isWhiteSheep());
    }

    /**
     * Test of isLamb method, of class Sheep.
     */
    @Test
    public void testIsLamb() {
        sheep.setAge(0);
        sheep.setOld(false);
        assertTrue(sheep.isLamb());
    }

    /**
     * Test of isRam method, of class Sheep.
     */
    @Test
    public void testIsRam() {
        sheep.setSex("Male");
        assertTrue(sheep.isRam());
    }

    /**
     * Test of isOld method, of class Sheep.
     */
    @Test
    public void testIsOld() {
        assertTrue(sheep.isOld());
    }

    
}