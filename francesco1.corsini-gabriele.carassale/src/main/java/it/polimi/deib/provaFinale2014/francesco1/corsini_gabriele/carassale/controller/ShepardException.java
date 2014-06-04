package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Gabriele Carassale
 */
public class ShepardException extends Exception {

    /**
     * Solleva un eccezione
     */
    public ShepardException() {
        super();
    }

    /**
     * Solleva un eccezione passando una stringa
     *
     * @param s Stringa da sollevare
     */
    public ShepardException(String s) {
        super(s);
    }

}
