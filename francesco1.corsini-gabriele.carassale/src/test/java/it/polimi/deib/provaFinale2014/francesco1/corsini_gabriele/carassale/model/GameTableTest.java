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
public class GameTableTest {

    private GameTable game;

    @Before
    public void setUp() {
        game = new GameTable();
    }

    /**
     * Test of decreaseFenceNumber method, of class GameTable.
     */
    @Test
    public void testDecreaseFenceNumber() {
        int fence1 = game.getFenceNumber();
        game.decreaseFenceNumber();
        assertEquals(fence1 - 1, game.getFenceNumber());
    }

    @Test
    public void testBlackSheep() {
        assertSame(game.getBlacksheep().getPosition(), game.getMap().getTerrain().get(18));
    }

    @Test
    public void testWolf() {
        assertSame(game.getWolf().getPosition(), game.getMap().getTerrain().get(18));
    }
    
    /**
     * Test per vedere se la trasformazione da id a oggetto è giusta
     */
    @Test
    public void testGetId(){
        int number = (int) ((Math.random() * 41) + 1);
        assertSame(game.idToRoad(number),game.getMap().getRoads().get(number));
        
        number = (int) ((Math.random() * 17) + 1);
        assertSame(game.idToTerrain(number),game.getMap().getTerrain().get(number));
        
        number = (int) ((Math.random() * 17) + 1);
        assertSame(game.idToSheep(number), game.getSheeps().get(number));
    }

}
