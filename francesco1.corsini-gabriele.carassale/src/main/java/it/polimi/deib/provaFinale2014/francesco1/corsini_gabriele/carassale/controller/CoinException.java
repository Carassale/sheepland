package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Francesco Corsini
 */
public class CoinException extends Exception {

    /**
     * Sollleva un eccezione
     */
    public CoinException() {
        super();
    }

    /**
     * Solleva un eccezione passando una stringa
     *
     * @param s Stringa da sollevare
     */
    public CoinException(String s) {
        super(s);
    }
}
