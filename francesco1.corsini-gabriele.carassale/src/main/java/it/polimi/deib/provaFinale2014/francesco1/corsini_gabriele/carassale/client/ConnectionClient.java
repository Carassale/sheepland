package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import java.io.IOException;

/**
 * È il main per il Client, ha il compito di provare a collegarsi con uno dei
 * metodi, Socket o RMI, e avviare il ConnectionClient corrispondente
 *
 * @author Carassale Gabriele
 */
public interface ConnectionClient {

    void setTypeOfInteraction(TypeOfInteraction typeOfInteraction);

    void moveShepard() throws IOException;

    void moveSheep() throws IOException;

    void buyCard() throws IOException;

    void killSheep() throws IOException;

    void joinSheep() throws IOException;

    void setNickname() throws IOException;

    void waitLine();
}
