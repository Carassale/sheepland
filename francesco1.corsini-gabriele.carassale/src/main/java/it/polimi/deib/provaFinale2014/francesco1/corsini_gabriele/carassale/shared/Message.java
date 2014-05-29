package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Enum per standardizzare i messaggi
 *
 * @author Carassale Gabriele
 */
public enum Message {

    noMoney("Non hai abbastanza soldi"),
    noMoneyForMafia("Non abbastanza soldi per comprare silenzio di tutti i pastori"),
    noShepardOnTheRoad("Non c'è Shepard sulla strada"),
    noNearShepard("Non c'è vicino un pastore"),
    noJoinableAnimal("Non ci sono una pecora ed un montone nel territorio"),
    noRoadComunicat("Non esiste strada che comunica tra questi due territori"),
    errorDistributeCard("errore distribuzione carte"),
    impossibleNoMoney("Impossibile fare la mossa! Non hai abbastanza soldi."),
    impossibleMove("Impossibile fare la mossa! Movimento non valido."),
    impossibleDice("Impossibile fare la mossa! Errore dado.");

    private String value;

    private Message(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
