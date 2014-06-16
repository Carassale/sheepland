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

    /**
     * Serve a controlare se il plyaer è ancora connesso, non implementata qui
     * ma nelle classi che la estendono
     *
     * @return True se è connesso
     */
    public boolean isStillConnected() {
        return false;
    }
}
