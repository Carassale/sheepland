package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

/**
 *
 * @author Gabriele Carassale
 */
public class FinishGame extends Exception {

    /**
     * Solleva un eccezione
     */
    public FinishGame() {
        super();
    }

    /**
     * Solleva un eccezione passandole la stringa dell'errore
     *
     * @param s Errore da sollevare
     */
    public FinishGame(String s) {
        super(s);
    }
}
