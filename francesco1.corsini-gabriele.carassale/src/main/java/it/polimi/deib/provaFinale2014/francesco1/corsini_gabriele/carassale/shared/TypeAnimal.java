package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di animale
 *
 * @author Carassale Gabriele
 */
public enum TypeAnimal {

    WOLF("wolf"),
    BLACK_SHEEP("blackSheep"),
    WHITE_SHEEP("whiteSheep"),
    RAM("ram"),
    LAMB("lamb"),
    MALE("male"),
    FEMALE("female");

    private String value;

    private TypeAnimal(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return value;
    }
}
