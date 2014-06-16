/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Shepherd;
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
    private Shepherd shepherd;
    private Sheep sheep;

    @Before
    public void setUp() {
        game = new GameTable();
        player = new Player(false, 0);
        shepherd = new Shepherd(game.getMap().getRoads().get(0), player, 0);
        player.getShepherds().add(shepherd);
        sheep = new Sheep(game.getMap().getTerrain().get(0), true, 0);

    }

    /**
     * Compro una carta Pianura e poi testo di avere una carta Pianura. Poi ne
     * compro un altra
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CardException
     */
    @Test
    public void testBuyTerrainCard() throws CoinException, CardException {
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
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CardException
     */
    @Test
    public void testBuyTerrainCardCoins() throws CoinException, CardException {

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
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CardException
     */
    @Test(expected = CoinException.class)
    public void testBuyTerrainCardExeption() throws CoinException, CardException {
        player.setCoins(0);
        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);
        player.buyTerrainCard(TypeCard.PLAIN.toString(), game);
    }

    /**
     * Test che muove shepherd in strada adiacente senza pagare
     *
     * @throws Exception
     */
    @Test
    public void testMoveShepherd() throws Exception {
        assertSame(shepherd.getPosition(), game.getMap().getRoads().get(0));
        player.moveShepherd(game.getMap().getRoads().get(1), shepherd, game);
        assertSame(shepherd.getPosition(), game.getMap().getRoads().get(1));
        assertEquals(20, player.getCoins());
    }

    /**
     * Test che muove shepherd in strada adiacente pagando
     *
     * @throws Exception
     */
    @Test
    public void testMoveShepherd2() throws Exception {
        assertSame(shepherd.getPosition(), game.getMap().getRoads().get(0));
        player.moveShepherd(game.getMap().getRoads().get(13), shepherd, game);
        assertSame(shepherd.getPosition(), game.getMap().getRoads().get(13));
        assertEquals(19, player.getCoins());
    }

    /**
     * Test che muove shepherd non avendo soldi sufficenti
     *
     * @throws Exception
     */
    @Test(expected = CoinException.class)
    public void testMoveShepherd3() throws Exception {
        player.setCoins(0);
        player.moveShepherd(game.getMap().getRoads().get(13), shepherd, game);
    }

    /**
     * Test che muove shepherd in strada con una Fence sopra
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     * @throws it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.ShepherdException
     */
    @Test(expected = MoveException.class)
    public void testMoveShepherd4() throws MoveException, CoinException, ShepherdException {
        game.getMap().getRoads().get(13).setFence(true);
        player.moveShepherd(game.getMap().getRoads().get(13), shepherd, game);
    }

    /**
     * Test che muove shepherd in strada con uno Shepherd sopra
     *
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.MoveException
     * @throws
     * it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.CoinException
     * @throws it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.ShepherdException
     */
    @Test(expected = MoveException.class)
    public void testMoveShepherd5() throws MoveException, CoinException, ShepherdException {
        Shepherd shepherd2 = new Shepherd(game.getMap().getRoads().get(13));
        player.moveShepherd(game.getMap().getRoads().get(13), shepherd, game);
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
     * Test che muove sheep e fallisce poichè non c'è shepherd sulla strada
     *
     * @throws MoveException
     * @throws CoinException
     */
    @Test(expected = MoveException.class)
    public void testMoveSheep2() throws MoveException, CoinException {
        Terrain destination = game.getMap().getTerrain().get(16);
        shepherd.setPosition(game.getMap().getRoads().get(10));

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

        Player player2 = new Player(false, 0);
        player.setCoins(20);
        player2.setCoins(20);
        player.getShepherds().clear();
        Shepherd shepherd1 = new Shepherd(game.getMap().getRoads().get(1), player, 1);
        Shepherd shepherd2 = new Shepherd(game.getMap().getRoads().get(2), player2, 2);
        player.getShepherds().add(shepherd1);

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

        Player player2 = new Player(false, 0);
        player.setCoins(3);
        Shepherd shepherd1 = new Shepherd(game.getMap().getRoads().get(1), player2, 1);
        Shepherd shepherd2 = new Shepherd(game.getMap().getRoads().get(2), player2, 2);

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
        player.getShepherds().clear();

        Sheep sheepToKill = (Sheep) game.getMap().getTerrain().get(0).getAnimals().get(0);

        player.killAnimal(sheep, game);
    }

    @Test
    public void testKillAnimal3() throws CoinException, MoveException, WrongDiceNumberException {
        Terrain terrain = game.getMap().getTerrain().get(0);
        terrain.getAnimals().clear();
        Sheep sheepToKill = new Sheep(terrain, true, 5);

        player.getShepherds().clear();
        player.setCoins(20);
        Shepherd shepherd1 = new Shepherd(game.getMap().getRoads().get(1), player, 1);
        player.getShepherds().add(shepherd1);

        Player player2 = new Player(false, 0);
        player2.setCoins(20);
        Shepherd shepherd2 = new Shepherd(game.getMap().getRoads().get(2), player2, 2);
        player2.getShepherds().add(shepherd2);

        player.killAnimal(sheepToKill, game, 3);

        assertEquals(0, terrain.getAnimals().size());

    }

}
