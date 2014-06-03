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
     * Il client invia un nickname e il server controlla se è accettabile o no
     *
     * @param nickname
     * @return Sring CORRECT, NO_CORRECT
     * @throws RemoteException
     */
    String checkNickname(String nickname) throws RemoteException;

    /**
     * Il client invia il proprio skeleton, il server lo associa ad un player
     *
     * @param clientRMI Skeleton del client
     * @param nickname
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    String addClient(ClientRMI clientRMI, String nickname) throws RemoteException;

    /**
     * Questo method viene chiamato nel caso il client dev'essere spostato nella
     * partita già in corso dalla quale si era precedentemente disconnesso
     *
     * @param nickname
     * @throws RemoteException
     */
    void reconnect(String nickname) throws RemoteException;
}
