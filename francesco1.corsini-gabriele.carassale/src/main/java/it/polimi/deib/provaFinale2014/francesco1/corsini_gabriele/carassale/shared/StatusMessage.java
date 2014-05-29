package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i messaggi
 *
 * @author Carassale Gabriele
 */
public enum StatusMessage {

    playerAdded("Player aggiunto"),
    noPlayerAdded("Player non aggiunto"),
    connected("Connesso"),
    actionOK("Mossa effettua"),
    actionError("Non è possibile fare questa mossa, ricorda di muovere il pastore"),
    errorMove("Errore movimento"),
    errorDice("Errore dado"),
    errorCoin("Errore monete");

    private String value;

    private StatusMessage(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
