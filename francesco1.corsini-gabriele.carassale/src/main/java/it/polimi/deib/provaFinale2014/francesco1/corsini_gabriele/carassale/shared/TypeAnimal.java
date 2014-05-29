package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di animale
 *
 * @author Carassale Gabriele
 */
public enum TypeAnimal {

    wolf("wolf"),
    blackSheep("blackSheep"),
    whiteSheep("whiteSheep"),
    ram("ram"),
    lamb("lamb"),
    male("male"),
    female("female");

    private String value;

    private TypeAnimal(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
