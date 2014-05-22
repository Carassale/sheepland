package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

/**
 * Dado
 *
 * @author Ferancesco Corsini
 */
public class Dice {

    /**
     * Crea un dado
     */
    public Dice() {
    }

    /**
     * Restituisce il risutato del lancio simulato di un dado
     *
     * @return numero da 1 a 6
     */
    public int getRandom() {
        return (int) ((Math.random() * 6) + 1);
    }

}
