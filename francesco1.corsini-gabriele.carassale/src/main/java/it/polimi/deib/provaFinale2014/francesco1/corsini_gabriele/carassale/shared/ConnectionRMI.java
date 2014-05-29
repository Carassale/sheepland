package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Gabriele Carassale
 */
public interface ConnectionRMI extends Remote {

    public String moveShepard(int idShepard, int idRoad) throws RemoteException;

    public String moveSheep(int idSheep, int idTerrain) throws RemoteException;

    public String buyCard(String typeOfTerrain) throws RemoteException;

    public String joinSheep(int idTerrain) throws RemoteException;

    public String killSheep(int idSheep) throws RemoteException;

}
