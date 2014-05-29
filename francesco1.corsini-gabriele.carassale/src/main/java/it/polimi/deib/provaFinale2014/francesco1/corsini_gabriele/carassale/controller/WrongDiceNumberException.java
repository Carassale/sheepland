package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Francesco Corsini
 */
public class WrongDiceNumberException extends Exception {

    /**
     * Solleva un eccezione passando un intero
     *
     * @param diceNumber Intero da sollevare
     */
    public WrongDiceNumberException(int diceNumber) {
        super(Integer.toString(diceNumber));
    }

    /**
     * Solleva un eccezione
     */
    public WrongDiceNumberException() {
        //To change body of generated methods, choose Tools | Templates.
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
