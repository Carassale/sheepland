package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.TypeOfInteraction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;

public class GUIDinamic implements TypeOfInteraction {

    private GameTable gameTable;

    public GUIDinamic() {
        gameTable = null;
    }

    public void setGameTable(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    public void print(String string) {
    }

    public String read() {
        return "";
    }

}
