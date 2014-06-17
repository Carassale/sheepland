package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Class the holds all the image files
 *
 * @author Francesco Corsini
 */
public class BufferedImageContainer {

    private BufferedImage fence, redShepherd, greenShepherd, blueShepherd,
            yellowShepherd, transparent, coins, borderRight, ram, whiteSheep,
            lamb, heart, lambPlus1, heartbroken, sadFace, winner, splat,
            bloodDrop, joinSheeps1, joinSheeps2, moveSheep1, moveSheep2,
            killSheep1, killSheep2, cursor, cursor2, backCurrentPlayer, backPlayer,
            redPlayer, bluPlayer, greenPlayer, yellowPlayer, arrow;

    /**
     * Standard constructor
     */
    public BufferedImageContainer() {
        try {
            fence = ImageIO.read(new File(".\\src\\main\\resources\\fence.png"));
            redShepherd = ImageIO.read(new File(".\\src\\main\\resources\\shepherdRedSmall.png"));
            greenShepherd = ImageIO.read(new File(".\\src\\main\\resources\\shepherdGreenSmall.png"));
            blueShepherd = ImageIO.read(new File(".\\src\\main\\resources\\shepherdBlueSmall.png"));
            yellowShepherd = ImageIO.read(new File(".\\src\\main\\resources\\shepherdYellowSmall.png"));
            transparent = ImageIO.read(new File(".\\src\\main\\resources\\transparent.png"));
            coins = ImageIO.read(new File(".\\src\\main\\resources\\coins.png"));
            borderRight = ImageIO.read(new File(".\\src\\main\\resources\\border_right.png"));
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
            redPlayer = ImageIO.read(new File(".\\src\\main\\resources\\redPlayer.png"));
            bluPlayer = ImageIO.read(new File(".\\src\\main\\resources\\bluPlayer.png"));
            greenPlayer = ImageIO.read(new File(".\\src\\main\\resources\\greenPlayer.png"));
            yellowPlayer = ImageIO.read(new File(".\\src\\main\\resources\\yellowPlayer.png"));
            arrow = ImageIO.read(new File(".\\src\\main\\resources\\arrow.png"));
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
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
    public BufferedImage getRedShepherd() {

        return redShepherd;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getGreenShepherd() {

        return greenShepherd;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getBlueShepherd() {

        return blueShepherd;
    }

    /**
     * Getter of the image
     *
     * @return BufferedImage
     */
    public BufferedImage getYellowShepherd() {

        return yellowShepherd;
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
            int i = num;
            if (num < 0) {
                i = 0;
            }
            return ImageIO.read(new File(".\\src\\main\\resources\\Recinti\\recinto_" + i + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
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
    public BufferedImage getBorderRight() {
        return borderRight;
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

    public BufferedImage getRedPlayer() {
        return redPlayer;
    }

    public BufferedImage getBluPlayer() {
        return bluPlayer;
    }

    public BufferedImage getGreenPlayer() {
        return greenPlayer;
    }

    public BufferedImage getYellowPlayer() {
        return yellowPlayer;
    }

    public BufferedImage getArrow() {
        return arrow;
    }

    
}
