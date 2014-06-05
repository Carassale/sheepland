package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

/**
 *
 * @author Gabriele Carassale
 */
public class PlayerDisconnect extends Exception {

    /**
     * Solleva un eccezione
     */
    public PlayerDisconnect() {
        super();
    }

    /**
     * Solleva un eccezione passandole la stringa dell'errore
     *
     * @param s Errore da sollevare
     */
    public PlayerDisconnect(String s) {
        super(s);
    }
}
