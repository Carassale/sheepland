package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import java.util.ArrayList;

public class PlayerPool {

    private ArrayList<Player> players;

    public PlayerPool() {
        players = new ArrayList<Player>();
    }

    public Player getFirstPlayer() {
        return players.get(0);
    }

    public void addPlayer(Player newPlayer){
        players.add(newPlayer);
    }
    
    /**
     * 
     * @return true se è finito il turno(sono scorsi tutti i giocatori) 
     */
    public boolean nextPlayer() {
        
        boolean newTurn = false;
        
        Player temp = players.get(0);
        players.remove(temp);
        players.add(temp);
        
        if(players.get(0).isFirstPlayer())
            newTurn = true;
        
        return newTurn;
    }
    
    public ArrayList<Player> getPlayers(){
        return players;
    }

}
