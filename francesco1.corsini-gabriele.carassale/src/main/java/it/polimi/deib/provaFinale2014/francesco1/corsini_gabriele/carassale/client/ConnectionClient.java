package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

/**
 * È il main per il Client, ha il compito di provare a collegarsi con uno dei
 * metodi, Socket o RMI, e avviare il ConnectionClient corrispondente
 *
 * @author Carassale Gabriele
 */
public interface ConnectionClient {

    void setTypeOfInteraction(TypeOfInteraction typeOfInteraction);

    void setNickname(String nickname);
    
    void placeShepard(int idRoad);

    void waitLine();

    void moveShepard(int idShepard, int idRoad);

    void moveSheep(int idSheep, int idTerrain);

    void buyCard(String typeOfTerrain);

    void killSheep(int idSheep);

    void joinSheep(int idTerrain);
}
