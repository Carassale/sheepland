package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di carta
 *
 * @author Carassale Gabriele
 */
public enum TypeCard {

    plain("Plain"),
    forest("Forest"),
    river("River"),
    desert("Desert"),
    mountain("Mountain"),
    field("Field"),
    sheepsbourg("Sheepsbourg");

    private String value;

    private TypeCard(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
