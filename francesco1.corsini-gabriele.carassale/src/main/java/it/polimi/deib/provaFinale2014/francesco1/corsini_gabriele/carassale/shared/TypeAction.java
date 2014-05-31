package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di carta
 *
 * @author Carassale Gabriele
 */
public enum TypeAction {

    MOVE_SHEPARD("Move_shepard"),
    MOVE_SHEEP("Move_sheep"),
    BUY_CARD("Buy_card"),
    KILL_SHEEP("Kill_sheep"),
    JOIN_SHEEP("Join_sheep"),
    WAKE_UP("Wake_up"),
    ERROR_COIN("Errore_coin"),
    ERROR_MOVE("Error_move"),
    ERROR_DICE("Error_dice"),
    ERROR_CARD("Error_card"),
    PLACE_SHEPARD("Place_shepard"),
    REFRESH_MOVE_ANIMAL("Refresh_move_animal"),
    REFRESH_ADD_ANIMAL("Refresh_add_animal"),
    REFRESH_KILL_ANIMAL("Refresh_kill_animal"),
    REFRESH_TRANSFORM_ANIMAL("Refresh_transform_animal"),
    REFRESH_ADD_SHEPARD("Refresh_add_shepard"),
    REFRESH_MOVE_SHEPARD("Refresh_move_shepard"),
    REFRESH_ADD_FENCE("Refresh_add_fence"),
    REFRESH_CARD("Refresh_card"),
    REFRESH_COIN("Refresh_coin"),
    REFRESH_WINNER("Refresh_winner"),
    ERROR_MESSAGE("Error_message:"),
    MESSAGE_TEXT("Message_text:");

    private String value;

    private TypeAction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
