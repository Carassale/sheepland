/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Francesco Corsini
 */
public class GameControllerTest {
    
    
    private GameController gameController;
    
    @Before
    public void setUp() {
        gameController = new GameController(6);
        
    }

    @Test
    public void testCreateGameController() {
        assertEquals(4,gameController.getPlayerPool().getPlayers().size());
    }
    
    @Test
    public void testDistributeCard() {
       assertEquals(4,gameController.getGameTable().getTerrainCardPool("Plain").size());
    }
    /*
    @Test
    public void testPlayRounds() {
        
    }


    @Test
    public void testGetPlayerPool() {
        
    }


    @Test
    public void testPlayGame() {
        
    }


    @Test
    public void testGetGameTable() {
        
    }*/
    
}
