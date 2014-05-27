package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StubRMI extends Remote {

    public String connect() throws RemoteException;

    public String checkStatus(int idConnection) throws RemoteException;

}
