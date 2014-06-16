package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;
 
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.fail;
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
    }
    
    @After
    public void disassemble(){
        gui.getAnimals().clear();
        gui.getShepherds().clear();
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
        gui.placeShepherd(1);
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

    /**
     * Test per vedere che venga compiuta l'azione TrasformAnimal correttamente quando comandato dalla connection
     */
    @Test
    public void testRefreshTransformAnimal() {
        gui.refreshAddAnimal(1, 1, TypeAnimal.LAMB.toString());
        
        gui.refreshTransformAnimal(1, TypeAnimal.WHITE_SHEEP.toString());
        
        for(ViewAnimal ele : gui.getAnimals()){
            if(ele.getId() == 1){
                sheep = ele;
            }
        }  
        
        assertNotEquals(TypeAnimal.LAMB.toString(),sheep.getType());
        
    }


    /**
     * Test per vedere che vengano aggiornati bene i soldi rimanenti
     */
    @Test
    public void testRefreshCoin() {
        int coins = gui.getCoins();
        
        gui.refreshCoin(5, false);
        assertEquals(gui.getCoins(),coins-5);
    }

    /**
     * Test per vedere che venga compiuta l'azione di posizionamento del Pastore correttamente quando comandato dalla connection
     */
    @Test
    public void testRefreshAddShepard() {
        
        int road = 5;
        assertFalse(gui.getRoads()[road].isIsShepherd());
        
        
        
        gui.refreshAddShepherd(1, road, true);
        assertTrue(gui.getRoads()[road].isIsShepherd());
        
        //teardown test
        gui.getRoads()[road].setIsShepherd(false);
    }

    /**
     * Test per vedere che venga compiuta l'azione di muovere il Pastore correttamente quando comandato dalla connection
     */
    @Test
    public void testRefreshMoveShepard() {
        int roadfrom = 10;
        int roadto = 5;
        int idShepard = 1;
        gui.refreshAddShepherd(idShepard, roadfrom, true);
        
        assertFalse(gui.getRoads()[roadto].isIsShepherd());
        
        gui.refreshMoveShepherd(idShepard, roadto);
        
        assertTrue(gui.getRoads()[roadto].isIsShepherd());
        
        //teardown test
        gui.getRoads()[roadto].setIsShepherd(false);
        
    }

}
