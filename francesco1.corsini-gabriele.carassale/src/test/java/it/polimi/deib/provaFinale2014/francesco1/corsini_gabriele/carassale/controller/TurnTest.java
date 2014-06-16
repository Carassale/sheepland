/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.BlackSheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Sheep;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Terrain;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Francesco Corsini
 */
public class TurnTest {

    private Turn turn;
    private GameTable game;

    @Before
    public void setUp() {
        game = new GameTable();
        turn = new Turn(false, game);
    }

    /**
     * Test che chiama prova a muovere la BlackSheep: si muove per forza perchè
     * all'inizio è a SheepSbourg è ci sono 6 strade collegate
     */
    @Test
    public void testControlBlackSheep() {

        Terrain terrain = game.getBlacksheep().getPosition();

        turn.moveBlackSheep();
        assertNotSame(terrain, game.getBlacksheep().getPosition());
    }

    /**
     * Test che prova a muovere la BlackSheep e valuta se la pecora si è mossa
     * veramente(se moveBlackSheep() ritorna true vuol dire che il method la
     * dovrebbe aver mossa, metre false il dado a fallito e la BlackSheep non si
     * muove). Questo è obbligato poichè il risultato della mossa di BlackSheep
     * è randomico a causa del lancio del dado
     */
    @Test
    public void testControlBlackSheep2() {

        boolean sheepHasMoved;
        BlackSheep blackSheep = game.getBlacksheep();

        //la metto in un angolo della mappa così ha più possibilità di lanciare eccezione
        Terrain terrainToPlace = game.getMap().getTerrain().get(0);
        blackSheep.setPosition(terrainToPlace);
        terrainToPlace.addAnimal(blackSheep);

        sheepHasMoved = turn.moveBlackSheep();
        if (sheepHasMoved) {
            assertNotSame(terrainToPlace, blackSheep.getPosition());
        } else {
            assertSame(terrainToPlace, blackSheep.getPosition());
        }
    }
    
    @Test
    public void testGrowUpLamb(){
        boolean isThereLamb = false;
        int age = 0;
        Sheep lamb = null;
        //cerco nella mappa un lamb(prenderò ultimo nella lista)
        for(Sheep ele : game.getSheeps()){
            if(ele.isLamb()){
                lamb = ele;
                isThereLamb = true;

            }
        }
        //se non c'erano agnelli termino
        if(!isThereLamb){
            assertTrue(true);
        }
        age = lamb.getAge();
        turn.growUpLambs();
        assertEquals(age+1,lamb.getAge());
        
    }

}
