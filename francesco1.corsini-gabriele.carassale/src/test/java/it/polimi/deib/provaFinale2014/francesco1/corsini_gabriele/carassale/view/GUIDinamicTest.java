/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import java.util.ArrayList;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Classe di test sulla GUI Dinamica. Crea una finestra, la rende invisibile e la testa
 * @author Fefa
 */
public class GUIDinamicTest {
    
    
    private static GUIDinamic gui;
    private ViewAnimal wolf,blacksheep,sheep;
    private GUIDinamicState guiState;
    
    @BeforeClass
    public static void setUp() {
        gui = new GUIDinamic();
        //la nascondo
        gui.setVisible(false);
    }
    
    @After
    public void disassemble(){
        gui.getAnimals().clear();
        gui.setGUIDinamicState(GUIDinamicState.INITIALIZATION);
    }




    /**
     * Test per vedere che il Lupo View si muova correttamente quando comandato dalla connection
     */
    @Test
    public void testActivateWolf() {
        gui.refreshAddAnimal(-2, 18, TypeAnimal.WOLF.toString());
        
        for(ViewAnimal ele : gui.getAnimals()){
            if(TypeAnimal.WOLF.toString().equals(ele.getType())){
                wolf = ele;
            }
        }
        
        assertEquals(18,wolf.getPosition());
        //non avendo logica, posso spostarlo anche in territorio non adiacente
        gui.activateWolf(2);
        assertEquals(2,wolf.getPosition());
       
    }

    /**
     * Test per vedere che la BlackSheep View si muova correttamente quando comandato dalla connection
     */
    @Test
    public void testActivateBlackSheep() {
        
        gui.refreshAddAnimal(-1, 18, TypeAnimal.BLACK_SHEEP.toString());
        
        for(ViewAnimal ele : gui.getAnimals()){
            if(TypeAnimal.BLACK_SHEEP.toString().equals(ele.getType())){
                blacksheep = ele;
            }
        }        
        
        assertEquals(18,blacksheep.getPosition());
        //non avendo logica, posso spostarlo anche in territorio non adiacente
        gui.activateBlackSheep(5);
        assertEquals(5,blacksheep.getPosition());
    }

    /**
     * Test per vedere se la GUI cambia stato in modo opportuno
     */
    @Test
    public void testClickAction() {
        guiState = gui.getGUIDinamicState();
        assertSame(GUIDinamicState.INITIALIZATION,guiState);
        gui.clickAction();
        guiState = gui.getGUIDinamicState();
        assertSame(GUIDinamicState.WAITINGFORPLAYER,guiState);
    }


    /**
     * Test per vedere se la GUI cambia stato in modo opportuno
     */
    @Test
    public void testPlaceShepard() {
        guiState = gui.getGUIDinamicState();
        assertSame(GUIDinamicState.INITIALIZATION,guiState);
        //un id a caso tanto non viene posizionato
        gui.placeShepard(1);
        guiState = gui.getGUIDinamicState();
        assertSame(GUIDinamicState.PLACESHEPARD,guiState);
    }

    /**
     * Test per vedere che venga aggiunto un animale correttamente quando comandato dalla connection
     */
    @Test
    public void testRefreshMoveAnimal() {
        gui.refreshAddAnimal(1, 1, TypeAnimal.WHITE_SHEEP.toString());
        
        for(ViewAnimal ele : gui.getAnimals()){
            if(TypeAnimal.WHITE_SHEEP.toString().equals(ele.getType())){
                sheep = ele;
            }
        }  
        
        assertSame(sheep.getPosition(),1);
        gui.refreshMoveAnimal(sheep.getId(), 10);
        assertSame(sheep.getPosition(),10);
    }


    /**
     * Test per vedere che venga aggiunto una fence correttamente quando comandato dalla connection
     */
    @Test
    public void testRefreshAddFence() {
        DinamicRoadButton road = gui.getRoads()[10];
        assertFalse(road.isHasFence());
        
        gui.refreshAddFence(10);
        assertTrue(road.isHasFence());
        
        //recupero 
        road.setHasFence(false);
    }

    /**
     * Test per vedere che venga compiuta l'azione KillSheep correttamente quando comandato dalla connection
     */
    @Test
    public void testRefreshKillAnimal() {
        gui.refreshAddAnimal(1, 1, TypeAnimal.WHITE_SHEEP.toString());
        
        boolean thereIsSheep = false;
        
        gui.refreshKillAnimal(1);
        
        for(ViewAnimal ele : gui.getAnimals()){
            if(ele.getId() == 1){
                thereIsSheep = true;
            }
        }
        if(!thereIsSheep)
            assertTrue(true);
        else
            fail();
        
        
    }

/*
  
    @Test
    public void testRefreshTransformAnimal() {
        System.out.println("refreshTransformAnimal");
        int idAnimal = 0;
        String kind = "";
        GUIDinamic instance = null;
        instance.refreshTransformAnimal(idAnimal, kind);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testRefreshCard() {
        System.out.println("refreshCard");
        String typeOfTerrain = "";
        boolean isSold = false;
        GUIDinamic instance = null;
        instance.refreshCard(typeOfTerrain, isSold);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testRefreshCoin() {
        System.out.println("refreshCoin");
        int coinsChange = 0;
        boolean addCoin = false;
        GUIDinamic instance = null;
        instance.refreshCoin(coinsChange, addCoin);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testRefreshAddShepard() {
        System.out.println("refreshAddShepard");
        int idShepard = 0;
        int road = 0;
        boolean isMine = false;
        GUIDinamic instance = null;
        instance.refreshAddShepard(idShepard, road, isMine);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testRefreshMoveShepard() {
        System.out.println("refreshMoveShepard");
        int idShepard = 0;
        int roadTo = 0;
        GUIDinamic instance = null;
        instance.refreshMoveShepard(idShepard, roadTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetGUIDinamicState() {
        System.out.println("getGUIDinamicState");
        GUIDinamic instance = null;
        GUIDinamicState expResult = null;
        GUIDinamicState result = instance.getGUIDinamicState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSetGUIDinamicState() {
        System.out.println("setGUIDinamicState");
        GUIDinamicState state = null;
        GUIDinamic instance = null;
        instance.setGUIDinamicState(state);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetSubMenuOpen() {
        System.out.println("getSubMenuOpen");
        GUIDinamic instance = null;
        int expResult = 0;
        int result = instance.getSubMenuOpen();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSetSubMenuOpen() {
        System.out.println("setSubMenuOpen");
        int subMenuOpen = 0;
        GUIDinamic instance = null;
        instance.setSubMenuOpen(subMenuOpen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testMessageText() {
        System.out.println("messageText");
        String message = "";
        GUIDinamic instance = null;
        instance.messageText(message);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSendBuyCard() {
        System.out.println("sendBuyCard");
        String terrainType = "";
        GUIDinamic instance = null;
        instance.sendBuyCard(terrainType);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSendMoveSheep() {
        System.out.println("sendMoveSheep");
        int terrain = 0;
        GUIDinamic instance = null;
        instance.sendMoveSheep(terrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSendMoveShepard() {
        System.out.println("sendMoveShepard");
        int roadTo = 0;
        GUIDinamic instance = null;
        instance.sendMoveShepard(roadTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSendJoinSheeps() {
        System.out.println("sendJoinSheeps");
        int terrain = 0;
        GUIDinamic instance = null;
        instance.sendJoinSheeps(terrain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSendKillSheep() {
        System.out.println("sendKillSheep");
        GUIDinamic instance = null;
        instance.sendKillSheep();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSendPlaceShepard() {
        System.out.println("sendPlaceShepard");
        int road = 0;
        GUIDinamic instance = null;
        instance.sendPlaceShepard(road);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetTempShepard() {
        System.out.println("getTempShepard");
        GUIDinamic instance = null;
        int expResult = 0;
        int result = instance.getTempShepard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetShepards() {
        System.out.println("getShepards");
        GUIDinamic instance = null;
        ArrayList<ViewShepard> expResult = null;
        ArrayList<ViewShepard> result = instance.getShepards();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetTempRoad() {
        System.out.println("getTempRoad");
        GUIDinamic instance = null;
        int expResult = 0;
        int result = instance.getTempRoad();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSetTempRoad() {
        System.out.println("setTempRoad");
        int tempRoad = 0;
        GUIDinamic instance = null;
        instance.setTempRoad(tempRoad);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetTempIdShepard() {
        System.out.println("getTempIdShepard");
        GUIDinamic instance = null;
        int expResult = 0;
        int result = instance.getTempIdShepard();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testSetTempIdShepard() {
        System.out.println("setTempIdShepard");
        int tempIdShepard = 0;
        GUIDinamic instance = null;
        instance.setTempIdShepard(tempIdShepard);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSheepSelected() {
        System.out.println("getSheepSelected");
        GUIDinamic instance = null;
        ViewAnimal expResult = null;
        ViewAnimal result = instance.getSheepSelected();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSheepSelected() {
        System.out.println("setSheepSelected");
        ViewAnimal sheepSelected = null;
        GUIDinamic instance = null;
        instance.setSheepSelected(sheepSelected);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    @Test
    public void testGetAnimals() {
        System.out.println("getAnimals");
        GUIDinamic instance = null;
        ArrayList<ViewAnimal> expResult = null;
        ArrayList<ViewAnimal> result = instance.getAnimals();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetAnimals() {
        System.out.println("setAnimals");
        ArrayList<ViewAnimal> animals = null;
        GUIDinamic instance = null;
        instance.setAnimals(animals);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */
}
