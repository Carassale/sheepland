/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

/**
 *
 * @author Carassale Gabriele
 */
public abstract class PlayerConnection {

    private int shepherdToPlace = 0;

    /**
     * Restituisce i pastori che il player deve ancora posizionare
     *
     * @return
     */
    public int getShepherdToPlace() {
        return shepherdToPlace;
    }

    /**
     * Imposta il numero di pastori che il player deve ancor posizionare
     *
     * @param shepherdToPlace
     */
    public void setShepherdToPlace(int shepherdToPlace) {
        this.shepherdToPlace = shepherdToPlace;
    }

    /**
     * Restituisce l'id player, non implementata qui ma nelle classi che la
     * estendono
     *
     * @return int IdPlayer
     */
    public int getIdPlayer() {
        return 0;
    }

    /**
     * Restituisce il nickname del player, non implementata qui ma nelle classi
     * che la estendono
     *
     * @return
     */
    public String getNickname() {
        return null;
    }

}
