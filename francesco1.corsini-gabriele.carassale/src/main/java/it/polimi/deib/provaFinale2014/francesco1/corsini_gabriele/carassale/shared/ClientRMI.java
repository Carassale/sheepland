package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaccia per lo skeleton del ClientRMI
 *
 * @author Gabriele Carassale
 */
public interface ClientRMI extends Remote {

    /**
     * Viene chiamato dal connectionManagerRMI, setta il connectionRMi al quale
     * fare richieste
     *
     * @param connectionRMI ConnectionRMI da settare
     * @throws RemoteException
     */
    void setConnectionRMI(ConnectionRMI connectionRMI) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, risveglia il client e chiede di
     * fare le azioni
     *
     * @throws RemoteException
     */
    void wakeUp() throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, invia un messaggio di errore
     * coin
     *
     * @param message Messaggio da inviare
     * @throws RemoteException
     */
    void errorCoin(String message) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, invia un messaggio di errore
     * movimento
     *
     * @param message Messaggio da inviare
     * @throws RemoteException
     */
    void errorMove(String message) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, invia un messaggio di errore
     * dado
     *
     * @param message Messaggio da inviare
     * @throws RemoteException
     */
    void errorDice(String message) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, chiede di posizionare il pastore
     *
     * @param idShepard Pastore da posizionare
     * @return Strada dove viene posizionato
     * @throws RemoteException
     */
    int getPlaceShepard(int idShepard) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha il movimento
     * dell'animale
     *
     * @param idAnimal Animale da spostare
     * @param idTerrain Terreno destinazione
     * @throws RemoteException
     */
    void refreshMoveAnimal(int idAnimal, int idTerrain) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha l'aggiunta di un
     * animale
     *
     * @param idAnimal Animale da aggiungere
     * @param kind Tipo di animale
     * @throws RemoteException
     */
    void refreshAddAnimal(int idAnimal, String kind) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha cancella animale
     *
     * @param idAnimal Animale da cancellare
     * @throws RemoteException
     */
    void refreshKillAnimal(int idAnimal) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha trasformazione animale
     *
     * @param idAnimal Animale da trasformare
     * @param kind Tipo di trasformazione finale
     * @throws RemoteException
     */
    void refreshTransformAnimal(int idAnimal, String kind) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha l'aggiunta di un
     * pastore
     *
     * @param idShepard Pastore da aggiungere
     * @param idRoad Strada dove posizionare
     * @throws RemoteException
     */
    void refreshAddShepard(int idShepard, int idRoad) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha il movimento di un
     * pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     * @throws RemoteException
     */
    void refreshMoveShepard(int idShepard, int idRoad) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha le carta
     *
     * @param kind Tipo di carta
     * @param isSold True se è venduta, False se è comprata
     * @throws RemoteException
     */
    void refreshCard(String kind, boolean isSold) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha le monete
     *
     * @param coins Monete cambiate
     * @param addCoin True se vanno aggiunte, False se vanno levate
     * @throws RemoteException
     */
    void refreshCoin(int coins, boolean addCoin) throws RemoteException;
}
