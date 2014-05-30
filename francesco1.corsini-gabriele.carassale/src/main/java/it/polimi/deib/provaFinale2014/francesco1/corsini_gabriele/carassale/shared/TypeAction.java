package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di carta
 *
 * @author Carassale Gabriele
 */
public enum TypeAction {

    MOVE_SHEPARD("Muovi Pastore"),
    MOVE_SHEEP("Muovi Pecora"),
    BUY_CARD("Compra Carta"),
    KILL_SHEEP("Uccidi Pecora"),
    JOIN_SHEEP("Accoppia Animali"),
    WAKE_UP("Sveglia"),
    SET_NICKNAME("Imposta il Nickname"),
    ERROR_COIN("Errore monete"),
    ERROR_MOVE("Errore movimento"),
    ERROR_DICE("Errore dado"),
    PLACE_SHEPARD("Posiziona pastore"),
    REFRESH_MOVE_ANIMAL("Refresh muovi animale"),
    REFRESH_ADD_ANIMAL("Refresh aggiungi animale"),
    REFRESH_KILL_ANIMAL("Refresh uccidi animale"),
    REFRESH_TRANSFORM_ANIMAL("Refresh trasforma animale"),
    REFRESH_ADD_SHEPARD("Refresh aggiungi pastore"),
    REFRESH_MOVE_SHEPARD("Refresh muovi pastore"),
    REFRESH_CARD("Refresh carte"),
    REFRESH_COIN("Refresh monete"),
    MESSAGE_TEXT("Messaggio di testo");

    private String value;

    private TypeAction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
