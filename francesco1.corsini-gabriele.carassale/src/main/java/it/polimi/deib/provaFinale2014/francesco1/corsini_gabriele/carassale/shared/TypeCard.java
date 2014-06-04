package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di carta
 *
 * @author Carassale Gabriele
 */
public enum TypeCard {

    PLAIN("Plain"),
    FOREST("Forest"),
    RIVER("River"),
    DESERT("Desert"),
    MOUNTAIN("Mountain"),
    FIELD("Field");

    private final String value;

    private TypeCard(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
