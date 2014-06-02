package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i messaggi
 *
 * @author Carassale Gabriele
 */
public enum StatusMessage {

    PLAYER_ADDED("Player aggiunto"),
    NO_PLAYER_ADDED("Player non aggiunto"),
    PLAYER_TRANSFER("Player trasferito"),
    CONNECTED("Connesso"),
    ACTION_OK("Mossa effettuata"),
    ACTION_ERROR("Non è possibile fare questa mossa, ricorda di muovere il pastore"),
    ERROR_MOVE("Error_move"),
    ERROR_DICE("Error_dice"),
    ERROR_COIN("Error_coin"),
    ERROR_CARD("Error_card"),
    TYPE_SOCKET("Type_socket"),
    TYPE_RMI("Type_rmi"),
    CORRECT_NICKNAME("Correct_nickname"),
    NOT_CORRECT_NICKNAME("Not_correct_nickname"),
    DISCONNECTED("Disconnected");

    private String value;

    private StatusMessage(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
