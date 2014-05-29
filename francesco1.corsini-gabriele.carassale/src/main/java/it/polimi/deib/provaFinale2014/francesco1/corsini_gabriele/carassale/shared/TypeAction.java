package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i tipi di carta
 *
 * @author Carassale Gabriele
 */
public enum TypeAction {

    moveShepard("Muovi Pastore"),
    moveSheep("Muovi Pecora"),
    buyCard("Compra Carta"),
    killSheep("Uccidi Pecora"),
    joinSheep("Accoppia Animali"),
    wakeUp("Sveglia"),
    setNikcnam("Imposta il Nickname"),
    errorCoin("Errore monete"),
    errorMove("Errore movimento"),
    errorDice("Errore dado"),
    placeShepard("PosizionaPastore"),
    refreshMoveAnimal("Refresh muovi animale"),
    refreshAddAnimal("Refresh aggiungi animale"),
    refreshKillAnimal("Refresh uccidi animale"),
    refreshTransformAnimal("Refresh trasforma animale"),
    refreshAddShepard("Refresh aggiungi pastore"),
    refreshMoveShepard("Refresh muovi pastore"),
    refreshCard("Refresh carte"),
    refreshCoin("Refresh monete"),
    messageText("Messaggio di testo");

    private String value;

    private TypeAction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
