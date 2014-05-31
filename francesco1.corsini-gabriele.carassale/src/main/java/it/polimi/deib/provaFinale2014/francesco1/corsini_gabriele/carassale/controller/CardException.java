package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Carassale Gabriele
 */
public class CardException extends Exception {

    /**
     * Sollleva un eccezione
     */
    public CardException() {
        super();
    }

    /**
     * Solleva un eccezione passando una stringa
     *
     * @param s Stringa da sollevare
     */
    public CardException(String s) {
        super(s);
    }
}
