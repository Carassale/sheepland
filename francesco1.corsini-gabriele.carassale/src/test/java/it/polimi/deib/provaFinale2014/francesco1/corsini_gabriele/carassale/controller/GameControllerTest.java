/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Wolf;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fefa
 */
public class GameControllerTest {
    
    private GameController gameController;
    private GameTable game;
    
    @Before
    public void setUp() {
        gameController = new GameController(4);
        game = gameController.getGameTable();
        
    }

    
    
    /**
     * Test che chiama prova a muovere la Wolf: si muove per forza perchè
     * all'inizio è a SheepSbourg è ci sono 6 strade collegate. Poi deve
     * mangiare per forza poichè c'è per forza una pecora ovunque si muova
     */
    @Test
    public void testWolfEatsSheep() {
        Wolf wolf = game.getWolf();
        Terrain terrainWherePlaced = wolf.getPosition();

        gameController.moveWolf();
        assertNotSame(terrainWherePlaced, wolf.getPosition());
        assertEquals(0, wolf.getPosition().getAnimals().size());//ci sono zero animali(il lupo non conta) quindi è stata mangiata la pecora
    }

    /**
     * Test che chiama prova a muovere il Wolf: se si muove torna true e mangia
     * pecora. Altrimenti false
     *
     */
    @Test
    public void testWolfEatsSheep2() {
        Wolf wolf = game.getWolf();
        Terrain terrainToPlace = game.getMap().getTerrain().get(0); //la metto in un angolo della mappa così ha più possibilità di lanciare eccezione
        wolf.setPosition(terrainToPlace);
        boolean wolfHasMoved;

        wolfHasMoved = gameController.moveWolf();
        if (wolfHasMoved) {
            assertNotSame(terrainToPlace, wolf.getPosition());
            assertEquals(0, wolf.getPosition().getAnimals().size());
        } else {
            assertSame(terrainToPlace, wolf.getPosition());
            assertEquals(1, wolf.getPosition().getAnimals().size());
        }
    }
    
    @Test
    public void testDistributeCards() {
        //6 giocatori implica che tutte le tipologie di Card sono state distribuite quindi ce ne saranno solo 4 in ogni pool
        gameController = new GameController(6);
        assertEquals(4,gameController.getGameTable().getTerrainCardPool("Plain").size());
        assertEquals(4,gameController.getGameTable().getTerrainCardPool("Forest").size());
        assertEquals(4,gameController.getGameTable().getTerrainCardPool("River").size());
        assertEquals(4,gameController.getGameTable().getTerrainCardPool("Desert").size());
        assertEquals(4,gameController.getGameTable().getTerrainCardPool("Mountain").size());
        assertEquals(4,gameController.getGameTable().getTerrainCardPool("Field").size());
    }
    
    @Test
    public void testPlaceShepards(){
        assertTrue(gameController.getGameTable().getMap().getRoads().get(0).hasShepard());
        assertTrue(gameController.getGameTable().getMap().getRoads().get(1).hasShepard());
        assertTrue(gameController.getGameTable().getMap().getRoads().get(2).hasShepard());
        assertTrue(gameController.getGameTable().getMap().getRoads().get(3).hasShepard());
        assertFalse(gameController.getGameTable().getMap().getRoads().get(4).hasShepard());
    }
}
