/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Corsini
 */
public class TerrainCardTest {

    private TerrainCard terrainCard;

    @Before
    public void setUp() {
        terrainCard = new TerrainCard();

    }

    /**
     * Test of TerrainCard.
     */
    @Test
    public void testGetTerrainTypeAndSetTerrainType() {
        terrainCard.setTerrainType(TypeCard.PLAIN.toString());
        assertEquals(TypeCard.PLAIN.toString(), terrainCard.getTerrainType());
    }

}
