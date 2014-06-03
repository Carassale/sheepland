/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fefa
 */
public class BufferedImageContainerTest {

    private BufferedImageContainer pool;
    private BufferedImage icon,icon2,fence, redShepard, greenShepard, blueShepard,
            yellowShepard, transparent, coins, ram, whiteSheep, lamb,heart,
            lambPlus1, heartbroken, sadFace, winner;;
    
    @Before
    public void setUp() {
        pool = new BufferedImageContainer();
    }

    /**
     * Test per vedere se le immagini vengono caricate bene 
     */
    /*@Test
    public void testLoadImages() {
        try {
            icon = ImageIO.read(new File(".\\src\\main\\resources\\redShepard.png"));
            fence = ImageIO.read(new File(".\\src\\main\\resources\\fence.png"));
            redShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardRedSmall.png"));
            greenShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardGreenSmall.png"));
            blueShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardBlueSmall.png"));
            yellowShepard = ImageIO.read(new File(".\\src\\main\\resources\\shepardYellowSmall.png"));
            transparent = ImageIO.read(new File(".\\src\\main\\resources\\transparent.png"));
            coins = ImageIO.read(new File(".\\src\\main\\resources\\coins.png"));
            ram = ImageIO.read(new File(".\\src\\main\\resources\\ram.png"));
            whiteSheep = ImageIO.read(new File(".\\src\\main\\resources\\whiteSheep.png"));
            lamb = ImageIO.read(new File(".\\src\\main\\resources\\lamb.png"));
            heart = ImageIO.read(new File(".\\src\\main\\resources\\heart.png"));
            lambPlus1 = ImageIO.read(new File(".\\src\\main\\resources\\lambPlus1.png"));
            heartbroken = ImageIO.read(new File(".\\src\\main\\resources\\heartbroken.png"));
            sadFace = ImageIO.read(new File(".\\src\\main\\resources\\sadFace.png"));
            winner = ImageIO.read(new File(".\\src\\main\\resources\\winner.png"));
            
            
            
            assertSame(redShepard,pool.getRedShepard());
            assertSame(blueShepard,pool.getBlueShepard());
            assertSame(coins,pool.getCoins());
            assertSame(fence,pool.getFence());
            assertSame(greenShepard,pool.getGreenShepard());
            assertSame(heart,pool.getHeart());
            assertSame(heartbroken,pool.getHeartbroken());
            assertSame(lamb,pool.getLamb());
            assertSame(lambPlus1,pool.getLambPlus1());
            assertSame(ram,pool.getRam());
            assertSame(redShepard,pool.getRedShepard());
            assertSame(sadFace,pool.getSadFace());
            assertSame(transparent,pool.getTransparent());
            assertSame(whiteSheep,pool.getWhiteSheep());
            assertSame(winner,pool.getWinner());
            assertSame(yellowShepard,pool.getYellowShepard());
            
            
        } catch (IOException ex) {
            //nel caso fallisci
            assertTrue(false);
        }
    }
*/
}
