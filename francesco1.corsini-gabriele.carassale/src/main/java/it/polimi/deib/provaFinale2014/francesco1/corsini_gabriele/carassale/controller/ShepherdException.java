package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Gabriele Carassale
 */
public class ShepherdException extends Exception {

    /**
     * Solleva un eccezione
     */
    public ShepherdException() {
        super();
    }

    /**
     * Solleva un eccezione passando una stringa
     *
     * @param s Stringa da sollevare
     */
    public ShepherdException(String s) {
        super(s);
    }

}
