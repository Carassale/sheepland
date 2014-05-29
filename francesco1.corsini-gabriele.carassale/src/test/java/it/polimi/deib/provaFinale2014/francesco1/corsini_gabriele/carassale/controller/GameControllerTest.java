package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Wolf;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Corsini
 */
public class GameControllerTest {

    private GameController gameController;
    private GameTable gameTable;

    @Before
    public void setUp() {
        gameController = new GameController(4);
        gameTable = gameController.getGameTable();
    }

    /**
     * Test che chiama prova a muovere la Wolf: si muove per forza perchè
     * all'inizio è a SheepSbourg è ci sono 6 strade collegate. Poi deve
     * mangiare per forza poichè c'è per forza una pecora ovunque si muova
     */
    @Test
    public void testWolfEatsSheep() {
        Wolf wolf = gameTable.getWolf();
        Terrain terrainWherePlaced = wolf.getPosition();

        boolean wolfHasMoved = gameController.moveWolf();
        if (wolfHasMoved) {
            assertNotSame(terrainWherePlaced, wolf.getPosition());
        } else {
            assertSame(terrainWherePlaced, wolf.getPosition());
        }

        boolean wolfHasEat = gameController.tryEatSheep();
        if (wolfHasEat) {
            assertEquals(0, wolf.getPosition().getAnimals().size());
        } else {
            assertEquals(1, wolf.getPosition().getAnimals().size());
        }
    }

    /**
     * Test che chiama prova a muovere il Wolf: se si muove torna true e mangia
     * pecora. Altrimenti false
     *
     */
    @Test
    public void testWolfEatsSheep2() {
        //la metto in un angolo della mappa così ha più possibilità di lanciare eccezione
        Terrain terrainToPlace = gameTable.getMap().getTerrain().get(0);

        Wolf wolf = gameTable.getWolf();
        wolf.setPosition(terrainToPlace);

        boolean wolfHasMoved = gameController.moveWolf();
        if (wolfHasMoved) {
            assertNotSame(terrainToPlace, wolf.getPosition());
        } else {
            assertSame(terrainToPlace, wolf.getPosition());
        }

        boolean wolfHasEat = gameController.tryEatSheep();
        if (wolfHasEat) {
            assertEquals(0, wolf.getPosition().getAnimals().size());
        } else {
            assertEquals(1, wolf.getPosition().getAnimals().size());
        }
    }

    /**
     * Test per distribuzione delle carte
     */
    @Test
    public void testDistributeCards() {
        //6 giocatori implica che tutte le tipologie di Card sono state distribuite quindi ce ne saranno solo 4 in ogni pool
        gameController = new GameController(6);
        assertEquals(4, gameController.getGameTable().getTerrainCardPool(TypeCard.plain.toString()).size());
        assertEquals(4, gameController.getGameTable().getTerrainCardPool(TypeCard.forest.toString()).size());
        assertEquals(4, gameController.getGameTable().getTerrainCardPool(TypeCard.river.toString()).size());
        assertEquals(4, gameController.getGameTable().getTerrainCardPool(TypeCard.desert.toString()).size());
        assertEquals(4, gameController.getGameTable().getTerrainCardPool(TypeCard.mountain.toString()).size());
        assertEquals(4, gameController.getGameTable().getTerrainCardPool(TypeCard.field.toString()).size());
    }

    /**
     * Test per il piazzamento degli Shepard
     */
    @Test
    public void testPlaceShepards() {
        assertTrue(gameController.getGameTable().getMap().getRoads().get(0).hasShepard());
        assertTrue(gameController.getGameTable().getMap().getRoads().get(1).hasShepard());
        assertTrue(gameController.getGameTable().getMap().getRoads().get(2).hasShepard());
        assertTrue(gameController.getGameTable().getMap().getRoads().get(3).hasShepard());
        assertFalse(gameController.getGameTable().getMap().getRoads().get(4).hasShepard());
    }
}
