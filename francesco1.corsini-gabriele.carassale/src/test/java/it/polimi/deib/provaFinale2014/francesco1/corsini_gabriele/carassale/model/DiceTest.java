/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fefa
 */
public class DiceTest {
    

    private static int num;
    
    @BeforeClass
    public static void setUpClass() {
        Dice dice = new Dice();
        num = dice.getRandom();
        
    }
    

    /**
     * Test of getRandom method, of class Dice.
     */
    @Test
    public void testGetRandom1() {
        
        assertEquals(1,num);
    }
    @Test
    public void testGetRandom2() {
        assertEquals(2,num);
    }
    @Test
    public void testGetRandom3() {
        assertEquals(3,num);
    }
    @Test
    public void testGetRandom4() {
        assertEquals(4,num);
    }
    @Test
    public void testGetRandom5() {
        assertEquals(5,num);
    }
    @Test
    public void testGetRandom6() {
        assertEquals(6,num);
    }
}
