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

/**
 *
 * @author Francesco Corsini
 */
public class BufferedImageContainer {

    private BufferedImage fence, redShepard, greenShepard, blueShepard,
            yellowShepard, transparent, coins, ram, whiteSheep, lamb,heart,
            lambPlus1, heartbroken, sadFace, winner;

    public BufferedImageContainer() {
        try {
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
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BufferedImage getFence() {

        return fence;
    }

    public BufferedImage getRedShepard() {

        return redShepard;
    }

    public BufferedImage getGreenShepard() {

        return greenShepard;
    }

    public BufferedImage getBlueShepard() {

        return blueShepard;
    }

    public BufferedImage getYellowShepard() {

        return yellowShepard;
    }

    public BufferedImage getTransparent() {

        return transparent;
    }
    
    public BufferedImage getFenceNumber(int num){
        try {
            if(num < 0)
                num = 0;
            BufferedImage fenceNum = ImageIO.read(new File(".\\src\\main\\resources\\Recinti\\recinto_" + num + ".png"));
            return fenceNum;
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public BufferedImage getCoins(){
        return coins;
    }

    public BufferedImage getRam() {
        return ram;
    }

    public BufferedImage getWhiteSheep() {
        return whiteSheep;
    }

    public BufferedImage getLamb() {
        return lamb;
    }

    public BufferedImage getHeart() {
        return heart;
    }

    public BufferedImage getLambPlus1() {
        return lambPlus1;
    }

    public BufferedImage getHeartbroken() {
        return heartbroken;
    }

    public BufferedImage getSadFace() {
        return sadFace;
    }

    public BufferedImage getWinner() {
        return winner;
    }
    
    
    
}
