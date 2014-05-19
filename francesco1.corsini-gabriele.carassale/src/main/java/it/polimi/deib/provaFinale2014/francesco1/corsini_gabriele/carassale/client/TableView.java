package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;

/**
 *
 * @author Carassale Gabriele
 */
public class TableView {

    private GameTable gameTable;

    public TableView() {
        gameTable = null;
    }

    public TableView(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    public void setGameTable(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    public void refresh() {
    }

}
