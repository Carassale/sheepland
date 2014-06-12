package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
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
            yellowShepard, transparent, coins, border_right, ram, whiteSheep,
            lamb, heart, lambPlus1, heartbroken, sadFace, winner, splat,
            bloodDrop, joinSheeps1, joinSheeps2, moveSheep1, moveSheep2,
            killSheep1, killSheep2, cursor, cursor2, backCurrentPlayer, backPlayer;

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
            border_right = ImageIO.read(new File(".\\src\\main\\resources\\border_right.png"));
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
            joinSheeps1 = ImageIO.read(new File(".\\src\\main\\resources\\joinSheeps.png"));
            joinSheeps2 = ImageIO.read(new File(".\\src\\main\\resources\\joinSheeps2.png"));
            moveSheep1 = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_1.png"));
            moveSheep2 = ImageIO.read(new File(".\\src\\main\\resources\\Pecore\\runningLeft_2.png"));
            killSheep1 = ImageIO.read(new File(".\\src\\main\\resources\\killSheep.png"));
            killSheep2 = ImageIO.read(new File(".\\src\\main\\resources\\killSheep2.png"));
            cursor = ImageIO.read(new File(".\\src\\main\\resources\\cursor.png"));
            cursor2 = ImageIO.read(new File(".\\src\\main\\resources\\cursor2.png"));
            backCurrentPlayer = ImageIO.read(new File(".\\src\\main\\resources\\back_current_player.png"));
            backPlayer = ImageIO.read(new File(".\\src\\main\\resources\\back_player.png"));
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
                return ImageIO.read(new File(".\\src\\main\\resources\\Recinti\\recinto_" + 0 + ".png"));
            } else {
                return ImageIO.read(new File(".\\src\\main\\resources\\Recinti\\recinto_" + num + ".png"));
            }
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
    public BufferedImage getBorder_right() {
        return border_right;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getBackCurrentPlayer() {
        return backCurrentPlayer;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getBackPlayer() {
        return backPlayer;
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

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getJoinSheeps1() {
        return joinSheeps1;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getJoinSheeps2() {
        return joinSheeps2;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getMoveSheep1() {
        return moveSheep1;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getMoveSheep2() {
        return moveSheep2;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getKillSheep1() {
        return killSheep1;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getKillSheep2() {
        return killSheep2;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getCursor() {
        return cursor;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getCursor2() {
        return cursor2;
    }

}
