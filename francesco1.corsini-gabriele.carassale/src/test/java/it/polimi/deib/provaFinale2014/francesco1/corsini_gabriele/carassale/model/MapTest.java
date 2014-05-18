/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Francesco Corsini
 */
public class MapTest {

    private static Map map;
    
    @Before
    public void setUp() {
        map = new Map();
    }

    /**
     * Test generale sulla giusta compisizione della Mappa, presa una strada specifica vedo se la creazione della mappa è andata a buon fine
     * controllando se c'è attaccato il giusto terreno
     */
  
    @Test
    public void testRoads() {
       assertSame(map.getRoads().get(1),map.getTerrain().get(0).getAdjacentRoads().get(1));
    }
    
    
    
}
