package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia per lo skeleton del server
 *
 * @author Gabriele Carassale
 */
public interface ServerRMI extends Remote {

    /**
     * Permette al client di connettersi al server
     *
     * @return Messaggio di conferma connessione
     * @throws RemoteException
     */
    String connect() throws RemoteException;

    /**
     * Il client invia il proprio skeleton, il server lo associa ad un player
     *
     * @param clientRMI Skeleton del client
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    String addClient(ClientRMI clientRMI) throws RemoteException;

}
