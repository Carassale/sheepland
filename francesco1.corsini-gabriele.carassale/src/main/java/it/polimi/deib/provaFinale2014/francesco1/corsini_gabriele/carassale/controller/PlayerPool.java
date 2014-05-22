package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import java.util.ArrayList;

/**
 * Crea un oggetto contenente la lista dei giocatori
 *
 * @author Carassale Gabriele
 */
public class PlayerPool {

    private ArrayList<Player> players;

    /**
     * Crea l'oggetto e inizializza l'ArreyList contenente i player
     */
    public PlayerPool() {
        players = new ArrayList<Player>();
    }

    /**
     * Restituisce il primo player dell'Array
     *
     * @return Player
     */
    public Player getFirstPlayer() {
        return players.get(0);
    }

    /**
     * Aggiunge un Player all'Array
     *
     * @param newPlayer player da aggiungere
     */
    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
    }

    /**
     * Fa scorre l'arrey di una posizione
     *
     * @return true se è finito il turno(sono scorsi tutti i giocatori)
     */
    public boolean nextPlayer() {
        players.add(players.get(0));
        players.remove(0);

        return players.get(0).isFirstPlayer();
    }

    /**
     * Restituisce l'Arrey contenente i player
     *
     * @return Arrey di player
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

}
