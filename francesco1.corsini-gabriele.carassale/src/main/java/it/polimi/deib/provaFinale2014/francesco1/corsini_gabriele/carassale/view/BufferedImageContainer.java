package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Class the holds all the image files
 *
 * @author Francesco Corsini
 */
public class BufferedImageContainer {

    private BufferedImage fence, redShepard, greenShepard, blueShepard,
            yellowShepard, transparent, coins, ram, whiteSheep, lamb, heart,
            lambPlus1, heartbroken, sadFace, winner, splat, bloodDrop;

    /**
     * Standard constructor
     */
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
            splat = ImageIO.read(new File(".\\src\\main\\resources\\splat.png"));
            bloodDrop = ImageIO.read(new File(".\\src\\main\\resources\\bloodDropping.png"));
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getFence() {

        return fence;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getRedShepard() {

        return redShepard;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getGreenShepard() {

        return greenShepard;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getBlueShepard() {

        return blueShepard;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getYellowShepard() {

        return yellowShepard;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getTransparent() {

        return transparent;
    }

    /**
     * Getter of the fence image
     *
     * @param num the number of fences remaining
     * @return BufferedImage with the correct fence number
     */
    public BufferedImage getFenceNumber(int num) {
        try {
            if (num < 0) {
                num = 0;
            }

            return ImageIO.read(new File(".\\src\\main\\resources\\Recinti\\recinto_" + num + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(BufferedImageContainer.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getCoins() {
        return coins;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getRam() {
        return ram;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getWhiteSheep() {
        return whiteSheep;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getLamb() {
        return lamb;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getHeart() {
        return heart;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getLambPlus1() {
        return lambPlus1;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getHeartbroken() {
        return heartbroken;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getSadFace() {
        return sadFace;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getWinner() {
        return winner;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getSplat() {
        return splat;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getBloodDrop() {
        return bloodDrop;
    }

}
