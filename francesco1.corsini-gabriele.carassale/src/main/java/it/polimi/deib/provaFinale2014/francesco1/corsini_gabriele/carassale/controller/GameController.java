package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnection;
import java.util.ArrayList;

public class GameController {

    private PlayerPool playerPool;

    private GameTable gameTable;

    public GameController(ArrayList<? extends PlayerConnection> playerConnections) {
    }

    public void playRound() {
    }

    public PlayerPool getPlayerPool() {
        return playerPool;
    }

    public void playGame() {
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    public void setGameTable(GameTable val) {
        this.gameTable = val;
    }

    private void createMap() {
    }

    private void InitializeAnimals() {
    }

    private void InizializeTerrainCards() {
    }

    private void InizializeShepards() {
    }

    private void declareWinner() {
    }

    private void initializePlayers() {
    }

}