package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Francesco Corsini
 */
public class MoveException extends Exception {

    /**
     * Solleva un eccezione
     */
    public MoveException() {
        super();
    }

    /**
     * Solleva un eccezione passando una stringa
     *
     * @param s Stringa da sollevare
     */
    public MoveException(String s) {
        super(s);
    }

}
