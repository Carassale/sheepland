/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepard;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Fefa
 */
public class PlayerTest {

    private GameTable game;
    private Player player;
    private Shepard shepard;
    private Sheep sheep;

    @Before
    public void setUp() {
        game = new GameTable();
        player = new Player(false);
        shepard = new Shepard(game.getMap().getRoads().get(0), player, 0);
        player.getShepards().add(shepard);
        sheep = new Sheep(game.getMap().getTerrain().get(0), true, 0);

    }

    /**
     * Compro una carta Pianura e poi testo di avere una carta Pianura. Poi ne
     * compro un altra
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     */
    @Test
    public void testBuyTerrainCard() throws CoinException {
        assertEquals(0, player.getTerrainCardsOwned(TypeCard.PLAIN.toString()).size());
        assertEquals(5, game.getTerrainCardPool(TypeCard.PLAIN.toString()).size());

        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);

        assertEquals(1, player.getTerrainCardsOwned(TypeCard.PLAIN.toString()).size());
        assertEquals(4, game.getTerrainCardPool(TypeCard.PLAIN.toString()).size());
    }

    /**
     * test per vedere se vengono scalati i soldi in modo giusto quando compro
     * Carta
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     */
    @Test
    public void testBuyTerrainCardCoins() throws CoinException {

        assertEquals(20, player.getCoins());
        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);
        assertEquals(20, player.getCoins());
        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);
        assertEquals(19, player.getCoins());
        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);
        assertEquals(17, player.getCoins());
    }

    /**
     * test per vedere se gestisce bene il non avere soldi sufficenti
     *
     * @throws CoinException
     */
    @Test(expected = CoinException.class)
    public void testBuyTerrainCardExeption() throws CoinException {
        player.setCoins(0);
        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);
        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);
    }

    /**
     * Test che muove shepard in strada adiacente senza pagare
     *
     * @throws Exception
     */
    @Test
    public void testMoveShepard() throws Exception {
        assertSame(shepard.getPosition(), game.getMap().getRoads().get(0));
        player.moveShepard(game.getMap().getRoads().get(1), shepard, game);
        assertSame(shepard.getPosition(), game.getMap().getRoads().get(1));
        assertEquals(20, player.getCoins());
    }

    /**
     * Test che muove shepard in strada adiacente pagando
     *
     * @throws Exception
     */
    @Test
    public void testMoveShepard2() throws Exception {
        assertSame(shepard.getPosition(), game.getMap().getRoads().get(0));
        player.moveShepard(game.getMap().getRoads().get(13), shepard, game);
        assertSame(shepard.getPosition(), game.getMap().getRoads().get(13));
        assertEquals(19, player.getCoins());
    }

    /**
     * Test che muove shepard non avendo soldi sufficenti
     *
     * @throws Exception
     */
    @Test(expected = CoinException.class)
    public void testMoveShepard3() throws Exception {
        player.setCoins(0);
        player.moveShepard(game.getMap().getRoads().get(13), shepard, game);
    }

    /**
     * Test che muove shepard in strada con una Fence sopra
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     */
    @Test(expected = MoveException.class)
    public void testMoveShepard4() throws MoveException, CoinException {
        game.getMap().getRoads().get(13).setFence(true);
        player.moveShepard(game.getMap().getRoads().get(13), shepard, game);
    }

    /**
     * Test che muove shepard in strada con uno Shepard sopra
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     */
    @Test(expected = MoveException.class)
    public void testMoveShepard5() throws MoveException, CoinException {
        Shepard shepard2 = new Shepard(game.getMap().getRoads().get(13));
        player.moveShepard(game.getMap().getRoads().get(13), shepard, game);
    }

    /**
     * Test che muove sheep
     *
     * @throws MoveException
     */
    @Test
    public void testMoveSheep() throws MoveException {
        Terrain destination = game.getMap().getTerrain().get(16);

        assertSame(sheep.getPosition(), game.getMap().getTerrain().get(0));
        player.moveSheep(sheep, destination, game);
        assertSame(sheep.getPosition(), game.getMap().getTerrain().get(16));
    }

    /**
     * Test che muove sheep e fallisce poichè non c'è shepard sulla strada
     *
     * @throws MoveException
     * @throws CoinException
     */
    @Test(expected = MoveException.class)
    public void testMoveSheep2() throws MoveException, CoinException {
        Terrain destination = game.getMap().getTerrain().get(16);
        shepard.setPosition(game.getMap().getRoads().get(10));

        player.moveSheep(sheep, destination, game);

    }

    /**
     * Test che muove sheep e fallisce poichè non i due territori non sono
     * confinanti
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     */
    @Test(expected = MoveException.class)
    public void testMoveSheep3() throws MoveException, CoinException {
        Terrain destination = game.getMap().getTerrain().get(12);

        player.moveSheep(sheep, destination, game);

    }

    /**
     * Test di accoppiamento di pecore(prima sono 2 poi diventano 3) svuoto il
     * territorio prima poichè la plancia di default ha 1 sheep inizializzata
     *
     * @throws MoveException
     */
    @Test
    public void testJoinSheeps() throws MoveException {
        Terrain terrain = game.getMap().getTerrain().get(0);
        terrain.getAnimals().clear();
        Sheep sheep2 = new Sheep(terrain, true, 1);
        Sheep sheep1 = new Sheep(terrain, true, 2);
        sheep2.setSex(TypeAnimal.MALE.toString());
        sheep1.setSex(TypeAnimal.FEMALE.toString());
        sheep2.setOld(true);
        sheep1.setOld(true);

        assertEquals(2, terrain.getAnimals().size());
        player.joinSheeps(terrain, game);
        assertEquals(3, terrain.getAnimals().size());

    }

    /**
     * Test di accoppiamento che lancia accezione perchè due montoni
     *
     * @throws MoveException
     */
    @Test(expected = MoveException.class)
    public void testJoinSheeps2() throws MoveException {
        Terrain terrain = game.getMap().getTerrain().get(16);
        terrain.getAnimals().remove(0);

        Sheep sheep2 = new Sheep(terrain, true, 1);
        Sheep sheep1 = new Sheep(terrain, true, 2);
        sheep2.setSex(TypeAnimal.MALE.toString());
        sheep1.setSex(TypeAnimal.MALE.toString());
        sheep2.setOld(true);
        sheep1.setOld(true);

        player.joinSheeps(terrain, game);
    }

    /**
     * Test di accoppiamento che lancia accezione perchè non c'è pastore vicino
     *
     * @throws MoveException
     */
    @Test(expected = MoveException.class)
    public void testJoinSheeps3() throws MoveException {
        Terrain terrain = game.getMap().getTerrain().get(10);
        Sheep sheep2 = new Sheep(terrain, true, 1);
        Sheep sheep1 = new Sheep(terrain, true, 2);
        sheep2.setSex(TypeAnimal.MALE.toString());
        sheep1.setSex(TypeAnimal.FEMALE.toString());
        sheep2.setOld(true);
        sheep1.setOld(true);

        player.joinSheeps(terrain, game);
    }

    /**
     * Test di abbattimento che viene eseguito correttamente
     *
     * @throws CoinException
     * @throws MoveException
     * @throws WrongDiceNumberException
     */
    @Test
    public void testKillAnimal() throws CoinException, MoveException, WrongDiceNumberException {
        Terrain terrain = game.getMap().getTerrain().get(0);
        terrain.getAnimals().clear();
        Sheep sheepToKill = new Sheep(terrain, true, 5);

        Player player2 = new Player(false);
        player.setCoins(20);
        player2.setCoins(20);
        player.getShepards().clear();
        Shepard shepard1 = new Shepard(game.getMap().getRoads().get(1), player, 1);
        Shepard shepard2 = new Shepard(game.getMap().getRoads().get(2), player2, 2);
        player.getShepards().add(shepard1);

        try {
            player.killAnimal(sheepToKill, game);
            assertEquals(0, terrain.getAnimals().size());
        } catch (WrongDiceNumberException e) {
            assertTrue(true);
        }

    }

    /**
     * Test di abbattimento che lancia eccezione perchè non abbastanza soldi
     *
     * @throws CoinException
     * @throws MoveException
     * @throws WrongDiceNumberException
     */
    @Test(expected = CoinException.class)
    public void testKillAnimal1() throws CoinException, MoveException, WrongDiceNumberException {

        Player player2 = new Player(false);
        player.setCoins(3);
        Shepard shepard1 = new Shepard(game.getMap().getRoads().get(1), player2, 1);
        Shepard shepard2 = new Shepard(game.getMap().getRoads().get(2), player2, 2);

        Sheep sheepToKill = (Sheep) game.getMap().getTerrain().get(0).getAnimals().get(0);

        player.killAnimal(sheep, game);
    }

    /**
     * Test di abbattimento che lancia eccezione perchè non c'è pastore
     * posseduto vicino (elimino i pastori del giocatore prima)
     *
     * @throws CoinException
     * @throws MoveException
     * @throws WrongDiceNumberException
     */
    @Test(expected = MoveException.class)
    public void testKillAnimal2() throws CoinException, MoveException, WrongDiceNumberException {
        player.getShepards().clear();

        Sheep sheepToKill = (Sheep) game.getMap().getTerrain().get(0).getAnimals().get(0);

        player.killAnimal(sheep, game);
    }

    @Test
    public void testKillAnimal3() throws CoinException, MoveException, WrongDiceNumberException {
        Terrain terrain = game.getMap().getTerrain().get(0);
        terrain.getAnimals().clear();
        Sheep sheepToKill = new Sheep(terrain, true, 5);

        player.getShepards().clear();
        player.setCoins(20);
        Shepard shepard1 = new Shepard(game.getMap().getRoads().get(1), player, 1);
        player.getShepards().add(shepard1);

        Player player2 = new Player(false);
        player2.setCoins(20);
        Shepard shepard2 = new Shepard(game.getMap().getRoads().get(2), player2, 2);
        player2.getShepards().add(shepard2);

        player.killAnimal(sheepToKill, game, 3);

        assertEquals(0, terrain.getAnimals().size());

    }

}
