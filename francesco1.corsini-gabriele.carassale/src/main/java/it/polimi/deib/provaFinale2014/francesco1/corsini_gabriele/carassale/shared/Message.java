package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i messaggi
 *
 * @author Carassale Gabriele
 */
public enum Message {

    NO_MONEY("Non hai abbastanza soldi."),
    NO_MONEY_FOR_MAFIA("<html>Non abbastanza soldi per comprare <br />silenzio di tutti i pastori.</html>"),
    NO_SHEPARD_ON_THE_ROAD("Non c'è Shepherd sulla strada."),
    NO_NEAR_SHEPARD("Non c'è vicino un pastore."),
    NO_JOINABLE_ANIMAL("<html>Non ci sono una pecora ed<br /> un montone nel territorio.</html>"),
    NO_ROAD_COMUNICANT("Non esiste strada che comunica tra questi due territori."),
    NO_YOUR_SHEPARD("Non è il tuo pastore."),
    NO_OTHER_CARD("Le carte di questa tipologia sono finite."),
    NO_SHEPARD_BUY_CARD("<html>I terreni adiacenti al pastore non sono<br /> del tipo della carta scelta.</html>"),
    ROAD_OCCUPIED("La strada di destinazione è già occuopata."),
    ACTION_OK("Mossa Effettuata"),
    ACTION_ERROR("<html>Non è possibile fare questa mossa, ricorda di muovere<br /> il pastore e che non è consentito fare due mosse consecutive uguali.</html>"),
    NO_CORRECT_DICE("Lancio del dado fallito.<br /> È uscito il numero: "),
    IMPOSSIBLE_SELECTION("Selezionare oggetti esistenti."),
    DISCONNECT_FOR_TIMEOUT("<html>Sei stato disconnesso dal server, non è stato<br /> raggiunto il numero minimo di giocatori.</html>"),
    RECONNECTED("Sei stato ricollegato alla partita."),
    DISCONNECTED("Client disconnesso."),
    ISTRUCTION("Come effettuare le mosse:\n\n- Muvoi pastore: clicca sul pastore\n che desideri spostare e poi sulla\nstrada di destinazione\n\n- Compra una carta: clicca\n direttamente sul tipo di\n carta che desideri comprare\n\n- Muovi/Accoppia/Uccidi pecora:\nclicca sulla pecora di un\nterreno, clicca sul tipo di pecora,\ninfine clicca sull'azione da \ncompiere");

    private final String value;

    private Message(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
