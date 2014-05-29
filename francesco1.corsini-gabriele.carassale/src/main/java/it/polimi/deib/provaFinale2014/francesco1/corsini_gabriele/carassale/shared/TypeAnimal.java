package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di animale
 *
 * @author Carassale Gabriele
 */
public enum TypeAnimal {

    wolf("Lupo"),
    blackSheep("Pecora Nera"),
    whiteSheep("Pecora"),
    ram("Montone"),
    lamb("Agnello"),
    male("Maschio"),
    female("Femmina");

    private String value;

    private TypeAnimal(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
