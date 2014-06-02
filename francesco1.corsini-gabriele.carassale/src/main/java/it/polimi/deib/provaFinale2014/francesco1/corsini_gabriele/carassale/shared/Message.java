package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i messaggi
 *
 * @author Carassale Gabriele
 */
public enum Message {

    NO_MONEY("Non hai abbastanza soldi"),
    NO_MONEY_FOR_MAFIA("Non abbastanza soldi per comprare silenzio di tutti i pastori"),
    NO_SHEPARD_ON_THE_ROAD("Non c'è Shepard sulla strada"),
    NO_NEAR_SHEPARD("Non c'è vicino un pastore"),
    NO_JOINABLE_ANIMAL("Non ci sono una pecora ed un montone nel territorio"),
    NO_ROAD_COMUNICANT("Non esiste strada che comunica tra questi due territori"),
    IMPOSSIBLE_NO_MONEY("Impossibile fare la mossa! Non hai abbastanza soldi."),
    IMPOSSIBLE_MOVE("Impossibile fare la mossa! Movimento non valido."),
    IMPOSSIBLE_CARD("Impossibile fare la mossa! Carte finite."),
    IMPOSSIBLE_DICE("Lancio del dado fallito."),
    IMPOSSIBLE_SELECTION("Selezionare oggetti esistenti.");

    private String value;

    private Message(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
