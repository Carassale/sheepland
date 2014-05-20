/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Francesco Corsini
 */
public class GameControllerTest {
    
    private PlayerPool playerPool;
    private GameController gameController;
    
    @Before
    public void setUp() {
        gameController = new GameController();
        playerPool = new PlayerPool();
    }

    @Test
    public void testDistributeCard() {
        
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
