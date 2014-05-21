package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;

/**
 * Questa classe è ancora tutta da gestire e implementare, andrà a rappresentare
 * la view del gameTable per far interfacciare il Client
 *
 * @author Carassale Gabriele
 */
public class TableView {

    private GameTable gameTable;

    /**
     * crea un tableView nulla
     */
    public TableView() {
        gameTable = null;
    }

    /**
     * Associa una tableView a quella globale della classe
     *
     * @param gameTable
     */
    public TableView(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    /**
     * Sostituisce la gameTable con una gameTable passata come prametro
     *
     * @param gameTable
     */
    public void setGameTable(GameTable gameTable) {
        this.gameTable = gameTable;
    }

    /**
     * Andrà a effettuare una refresh a schermo della tableView modificando
     * quindi la posizione, movimenti ecc.. dei componenti del gameTable sullo
     * schermo
     */
    public void refresh() {
    }

}
