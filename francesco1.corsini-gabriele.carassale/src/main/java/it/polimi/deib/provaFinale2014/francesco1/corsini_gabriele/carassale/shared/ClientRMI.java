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
     * Viene chiamato dal serverManagerRMI nel caso non ci siano state
     * abbastanza connessioni e il client si disconnette
     *
     * @throws RemoteException
     */
    void disconnectForTimout() throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, risveglia il client e chiede di
     * fare le azioni
     *
     * @return 
     * @throws RemoteException
     */
    String wakeUp() throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, chiede di posizionare il pastore
     *
     * @param idShepherd Pastore da posizionare
     * @return Strada dove viene posizionato
     * @throws RemoteException
     */
    int getPlaceShepherd(int idShepherd) throws RemoteException;

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
     * @param idAnimal
     * @param idTerrain Terreno in cui aggiungere
     * @param kind Tipo di animale
     * @throws RemoteException
     */
    void refreshAddAnimal(int idAnimal, int idTerrain, String kind) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha l'aggiunta di una fence
     *
     * @param idRoad strada dove aggiungere
     * @throws RemoteException
     */
    void refreshAddFence(int idRoad) throws RemoteException;

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
     * @param idShepherd Pastore da aggiungere
     * @param idRoad Strada dove posizionare
     * @param isMine
     * @throws RemoteException
     */
    void refreshAddShepherd(int idShepherd, int idRoad, boolean isMine) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, refresha il movimento di un
     * pastore
     *
     * @param idShepherd Pastore da muovere
     * @param idRoad Strada destinazione
     * @throws RemoteException
     */
    void refreshMoveShepherd(int idShepherd, int idRoad) throws RemoteException;

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

    /**
     * Viene chiamato dal connectionManagerRMI, a fine partita viene inviato la
     * posizione finale in classifica e il punteggio del giocatore
     *
     * @param finalPosition int Posizione finale in classifica
     * @param finalScore int Punteggio finale del giocatore
     * @throws java.rmi.RemoteException
     */
    void refreshWinner(int finalPosition, int finalScore) throws RemoteException;

    /**
     * Invia il nickname e l'id del player collegato
     *
     * @param nickname
     * @param idPlayer
     * @throws RemoteException
     */
    void refreshAddPlayer(String nickname, int idPlayer) throws RemoteException;

    /**
     * Invia l'id del player che si è momentaneamente disconnesso
     *
     * @param idPlayer
     * @throws RemoteException
     */
    void refreshWaitPlayer(int idPlayer) throws RemoteException;

    /**
     * Invia l'id del player che si è disconnesso
     *
     * @param idPlayer
     * @param turnOff
     * @throws RemoteException
     */
    void refreshTurnOffPlayer(int idPlayer, boolean turnOff) throws RemoteException;

    /**
     * Invia l'id del current player
     *
     * @param idPlayer
     * @throws RemoteException
     */
    void refreshTurnPlayer(int idPlayer) throws RemoteException;

    /**
     * Viene chiamato dal connectionManagerRMI, controlla se il client è pronto
     * a ricevere comandi
     *
     * @return True se è pronto
     * @throws RemoteException
     */
    boolean isReady() throws RemoteException;

    /**
     * Stampa un messaggio di errore
     *
     * @param message
     * @throws RemoteException
     */
    void errorMessage(String message) throws RemoteException;

    /**
     * Stampa un messaggio
     *
     * @param message
     * @throws RemoteException
     */
    void messageText(String message) throws RemoteException;
}
