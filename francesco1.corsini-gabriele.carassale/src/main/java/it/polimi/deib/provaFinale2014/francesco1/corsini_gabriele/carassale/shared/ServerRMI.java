package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Gabriele Carassale
 */
public interface ServerRMI extends Remote {

    public String connect() throws RemoteException;

    public String addClient(ClientRMI clientRMI) throws RemoteException;

}
