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
    PLAYER_DISCONNECTED("Player disconnesso"),
    CONNECTED("Connesso"),
    DISCONNECTED("Disconnected"),
    TYPE_SOCKET("Socket"),
    TYPE_RMI("RMI"),
    CORRECT_NICKNAME("Correct_nickname"),
    NOT_CORRECT_NICKNAME("Not_correct_nickname"),
    GAME_STARTED("Game_started"),
    DISCONNECTED_FOR_TIMEOUT("Disconnected_for_timeout"),
    SERVER_OFF("Si è spento il server.. bye bye");

    private final String value;

    private StatusMessage(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
