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
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String moveShepard(int idShepard, int idRoad) throws RemoteException;

    /**
     * Viene chiamato dal client, muove la pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String moveSheep(int idSheep, int idTerrain) throws RemoteException;

    /**
     * Viene chiamato dal client, compra una carta
     *
     * @param typeOfTerrain Tipo di carta da comprare
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String buyCard(String typeOfTerrain) throws RemoteException;

    /**
     * Viene chiamato dal client, accoppia pecora e montone
     *
     * @param idTerrain Terreno dove avviene l'accoppiamento
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String joinSheep(int idTerrain) throws RemoteException;

    /**
     * Viene chiamato dal client, uccide una pecora
     *
     * @param idSheep Pecora da uccidere
     * @return Messaggio di conferma
     * @throws RemoteException
     */
    public String killSheep(int idSheep) throws RemoteException;

}
