package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Gabriele Carassale
 */
public interface ClientRMI extends Remote {

    public void setConnectionRMI(ConnectionRMI connectionRMI) throws RemoteException;

    public void wakeUp() throws RemoteException;

    public void setNikcname() throws RemoteException;

    public void errorCoin(String message) throws RemoteException;

    public void errorMove(String message) throws RemoteException;

    public void errorDice(String message) throws RemoteException;

    public int getPlaceShepard(int idShepard) throws RemoteException;

    public void refreshMoveAnimal(int idAnimal, int idTerrain) throws RemoteException;

    public void refreshAddAnimal(int idAnimal, String kind) throws RemoteException;

    public void refreshKillAnimal(int idAnimal) throws RemoteException;

    public void refreshTransformAnimal(int idAnimal, String kind) throws RemoteException;

    public void refreshAddShepard(int idShepard, int idRoad) throws RemoteException;

    public void refreshMoveShepard(int idShepard, int idRoad) throws RemoteException;

    public void refreshCard(String kind, boolean isSold) throws RemoteException;

    public void refreshCoin(int coins, boolean addCoin) throws RemoteException;
}
