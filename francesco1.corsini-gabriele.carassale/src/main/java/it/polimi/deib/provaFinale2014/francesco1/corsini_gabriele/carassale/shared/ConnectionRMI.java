package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.rmi.Remote;

/**
 *
 * @author Gabriele Carassale
 */
public interface ConnectionRMI extends Remote {

    public String moveShepard(int idShepard, int idRoad);

    public String moveSheep(int idSheep, int idTerrain);

    public String buyCard(String typeOfTerrain);

    public String joinSheep(int idTerrain);

    public String killSheep(int idSheep);

}
