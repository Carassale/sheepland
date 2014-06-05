package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i messaggi
 *
 * @author Carassale Gabriele
 */
public enum Message {

    NO_MONEY("Non hai abbastanza soldi."),
    NO_MONEY_FOR_MAFIA("Non abbastanza soldi per comprare silenzio di tutti i pastori."),
    NO_SHEPARD_ON_THE_ROAD("Non c'è Shepard sulla strada."),
    NO_NEAR_SHEPARD("Non c'è vicino un pastore."),
    NO_JOINABLE_ANIMAL("Non ci sono una pecora ed un montone nel territorio."),
    NO_ROAD_COMUNICANT("Non esiste strada che comunica tra questi due territori."),
    NO_YOUR_SHEPARD("Non è il tuo pastore."),
    NO_OTHER_CARD("Le carte di questa tipologia sono finite."),
    ROAD_OCCUPIED("La strada di destinazione è già occuopata."),
    ACTION_OK("Mossa effettuata."),
    ACTION_ERROR("Non è possibile fare questa mossa, ricorda di muovere il pastore e che non è consentito fare due mosse consecutive uguali."),
    NO_CORRECT_DICE("Lancio del dado fallito. È uscito il numero: "),
    IMPOSSIBLE_SELECTION("Selezionare oggetti esistenti."),
    DISCONNECT_FOR_TIMEOUT("Sei stato disconnesso dal server, non è stato raggiunto il numero minimo di giocatori."),
    RECONNECTED("Sei stato ricollegato alla partita."),
    DISCONNECTED("Client disconnesso.");

    private final String value;

    private Message(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
