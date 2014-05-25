package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

/**
 * È il main per il Client, ha il compito di provare a collegarsi con uno dei
 * metodi, Socket o RMI, e avviare il ConnectionClient corrispondente
 *
 * @author Carassale Gabriele
 */
public interface ConnectionClient {

    void setTypeOfInteraction(TypeOfInteraction typeOfInteraction);

    void moveShepard();

    void moveSheep();

    void buyCard();

    void killSheep();

    void joinSheep();

    void setNickname();

    void waitLine();
}
