package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Francesco Corsini
 */
public class WrongDiceNumberException extends Exception {

    /**
     * Solleva un eccezione
     */
    public WrongDiceNumberException() {
        super();
    }

    /**
     * Solleva un eccezione passando un intero
     *
     * @param diceNumber Intero da sollevare
     */
    public WrongDiceNumberException(int diceNumber) {
        super(Integer.toString(diceNumber));
    }

    /**
     * Solleva un eccezione passando una stringa
     *
     * @param s Stringa da sollevare
     */
    public WrongDiceNumberException(String s) {
        super(s);
    }
}
