package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

/**
 * viene generato nel caso sia assegnato il 3° territorio ad una strada
 *
 * @author Francesco Corsini
 */
public class TerrainBoundariesExeption extends Exception {

    /**
     * Solleva un eccezione
     */
    public TerrainBoundariesExeption() {
        super();
    }

    /**
     * Solleva un eccezione passandole la stringa dell'errore
     *
     * @param s Errore da sollevare
     */
    public TerrainBoundariesExeption(String s) {
        super(s);
    }
}
