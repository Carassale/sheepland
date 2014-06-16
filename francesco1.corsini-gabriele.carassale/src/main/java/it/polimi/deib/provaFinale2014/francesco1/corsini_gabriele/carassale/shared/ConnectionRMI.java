package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia per lo skeleton del connectionManagerRMI
 *
 * @author Gabriele Carassale
 */
public interface ConnectionRMI extends Remote {

    /**
     * Viene chiamato dal client, muove il pastore
     *
     * @param idShepherd Pastore da muovere
     * @param idRoad Strada destinazione
     * @throws RemoteException
     */
    void moveShepherd(int idShepherd, int idRoad) throws RemoteException;

    /**
     * Viene chiamato dal client, muove la pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     * @throws RemoteException
     */
    void moveSheep(int idSheep, int idTerrain) throws RemoteException;

    /**
     * Viene chiamato dal client, compra una carta
     *
     * @param typeOfTerrain Tipo di carta da comprare
     * @throws RemoteException
     */
    void buyCard(String typeOfTerrain) throws RemoteException;

    /**
     * Viene chiamato dal client, accoppia pecora e montone
     *
     * @param idTerrain Terreno dove avviene l'accoppiamento
     * @throws RemoteException
     */
    void joinSheep(int idTerrain) throws RemoteException;

    /**
     * Viene chiamato dal client, uccide una pecora
     *
     * @param idSheep Pecora da uccidere
     * @throws RemoteException
     */
    void killSheep(int idSheep) throws RemoteException;

}
