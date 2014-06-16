package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.TypeOfInteraction;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea la connessione diretta con il GameController tramite la
 * ConnectionManager nel caso sia stato scelto il method RMI
 *
 * @author Carassale Gabriele
 */
public class ConnectionClientRMI extends UnicastRemoteObject implements ConnectionClient, ClientRMI {

    private TypeOfInteraction typeOfInteraction;
    private boolean isReady = false;

    private static final int PORT = 3001;

    /**
     * Questa variabile server solo per il method placeShepherd, serve per
     * aspettare la scelta del clinet
     */
    private Integer tempRoad = null;
    private Object objectSyncronize = new Object();

    /**
     * È il collegamento allo stub del serve sul quale il client può eseguire
     * dei metodi
     */
    private ConnectionRMI connectionRMI;
    private String nickname;

    /**
     * Crea un connection client di tipo RMI passando lo stub del server
     *
     * @param nickname
     * @throws java.rmi.RemoteException
     */
    public ConnectionClientRMI(String nickname) throws RemoteException {
        this.nickname = nickname;
        try {
            Registry registry = LocateRegistry.getRegistry(PORT);
            registry.rebind(nickname, this);

        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, StatusMessage.SERVER_OFF.toString(), ex);
            turnOff();
        }
    }

    /**
     * Setta lo stub del prorpio server
     *
     * @param connectionRMI Stub da settare
     */
    public void setConnectionRMI(ConnectionRMI connectionRMI) {
        this.connectionRMI = connectionRMI;
    }

    /**
     * Imposta il tipo di interfaccia che desidera utilizzare il client
     *
     * @param typeOfInteraction interfaccia da utilizzare
     */
    public void setTypeOfInteraction(TypeOfInteraction typeOfInteraction) {
        this.typeOfInteraction = typeOfInteraction;
        isReady = true;
    }

    /**
     * Viene chiamato dal connection client per sapere se è pronto a ricevere
     * comandi
     *
     * @return isReady
     */
    public boolean isReady() {
        return isReady;
    }

    /**
     * Non fa nulla in questa classe
     */
    public void waitLine() {
        // non fa nulla
    }

    /**
     * Riceve la scelta della strada dal client e la setta in tempRoad
     *
     * @param idRoad Strada scelta
     */
    public void placeShepherd(int idRoad) {
        synchronized (objectSyncronize) {
            tempRoad = idRoad;
            objectSyncronize.notifyAll();
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il method è muovi il pastore
     *
     * @param idShepherd Pastore da muovere
     * @param idRoad Strada destinazione
     */
    public void moveShepherd(int idShepherd, int idRoad) {
        try {
            connectionRMI.moveShepherd(idShepherd, idRoad);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, StatusMessage.SERVER_OFF.toString(), ex);
            turnOff();
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il method è muovi pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     */
    public void moveSheep(int idSheep, int idTerrain) {
        try {
            connectionRMI.moveSheep(idSheep, idTerrain);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, StatusMessage.SERVER_OFF.toString(), ex);
            turnOff();
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il method è compra una carta
     *
     * @param typeOfTerrain Tipo di carta da comprare
     */
    public void buyCard(String typeOfTerrain) {
        try {
            connectionRMI.buyCard(typeOfTerrain);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, StatusMessage.SERVER_OFF.toString(), ex);
            turnOff();
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il method è uccidi pecora
     *
     * @param idSheep Pecora da uccidere
     */
    public void killSheep(int idSheep) {
        try {
            connectionRMI.killSheep(idSheep);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, StatusMessage.SERVER_OFF.toString(), ex);
            turnOff();
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il method è accoppia ovini
     *
     * @param idTerrain Terreno dove sono presenti un montone e una pecora
     */
    public void joinSheep(int idTerrain) {
        try {
            connectionRMI.joinSheep(idTerrain);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, StatusMessage.SERVER_OFF.toString(), ex);
            turnOff();
        }
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è clickAction e server a risvegliare il client e
     * chiede la mossa che disidera fare
     *
     * @throws RemoteException
     */
    public void wakeUp() throws RemoteException {
        typeOfInteraction.clickAction();
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è posiziona pastore
     *
     * @param idShepherd Pastore da posizionare
     * @return Strada scelta dal client
     * @throws RemoteException
     */
    public int getPlaceShepherd(int idShepherd) throws RemoteException {
        tempRoad = null;
        typeOfInteraction.placeShepherd(idShepherd);

        synchronized (objectSyncronize) {
            while (tempRoad == null) {
                try {
                    objectSyncronize.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        }

        return Integer.valueOf(tempRoad.toString());
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh sul movimento dell'animale
     *
     * @param idAnimal Animale da spostare
     * @param idTerrain Terreno destinazione
     * @throws RemoteException
     */
    public void refreshMoveAnimal(int idAnimal, int idTerrain) throws RemoteException {
        typeOfInteraction.refreshMoveAnimal(idAnimal, idTerrain);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh sull'aggiunta dell'animale
     *
     * @param idAnimal Animale da aggiungere
     * @param idTerrain Terreno in cui aggiungere
     * @param kind Tipo di animale
     * @throws RemoteException
     */
    public void refreshAddAnimal(int idAnimal, int idTerrain, String kind) throws RemoteException {
        typeOfInteraction.refreshAddAnimal(idAnimal, idTerrain, kind);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh cancella animale
     *
     * @param idAnimal Animale da cancellare
     * @throws RemoteException
     */
    public void refreshKillAnimal(int idAnimal) throws RemoteException {
        typeOfInteraction.refreshKillAnimal(idAnimal);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh trasforma animale
     *
     * @param idAnimal Animale da trasformare
     * @param kind Tipo di trasformazione finale
     * @throws RemoteException
     */
    public void refreshTransformAnimal(int idAnimal, String kind) throws RemoteException {
        typeOfInteraction.refreshTransformAnimal(idAnimal, kind);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh aggiungi pastore
     *
     * @param idShepherd Pastore da aggiungere
     * @param idRoad Strada posizionamento
     * @param isMine
     * @throws RemoteException
     */
    public void refreshAddShepherd(int idShepherd, int idRoad, boolean isMine) throws RemoteException {
        typeOfInteraction.refreshAddShepherd(idShepherd, idRoad, isMine);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh muovi pastore
     *
     * @param idShepherd Pastore da muovere
     * @param idRoad Strada destinazione
     * @throws RemoteException
     */
    public void refreshMoveShepherd(int idShepherd, int idRoad) throws RemoteException {
        typeOfInteraction.refreshMoveShepherd(idShepherd, idRoad);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh carte
     *
     * @param kind Tipo di carta
     * @param isSold True se è venduta, False se è comprata
     * @throws RemoteException
     */
    public void refreshCard(String kind, boolean isSold) throws RemoteException {
        typeOfInteraction.refreshCard(kind, isSold);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh monete
     *
     * @param coins Monete cambiate
     * @param addCoin True se sono aggiunte, False se vanno levate
     * @throws RemoteException
     */
    public void refreshCoin(int coins, boolean addCoin) throws RemoteException {
        typeOfInteraction.refreshCoin(coins, addCoin);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh fence
     *
     * @param idRoad Strada dove posizionare la fence
     * @throws RemoteException
     */
    public void refreshAddFence(int idRoad) throws RemoteException {
        typeOfInteraction.refreshAddFence(idRoad);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il method è refresh winner e invia la posizione in classifica
     * e il punteggio
     *
     * @param finalPosition Posizione in classifica
     * @param finalScore Punteggio finale
     */
    public void refreshWinner(int finalPosition, int finalScore) throws RemoteException {
        typeOfInteraction.refreshWinner(finalPosition, finalScore);

        turnOff();
    }

    /**
     * Questo method serve al server a sapere se il client è ancora collegato,
     * non fa nulla perchè se il server non riesce a invocare il method ha già
     * la conferma che è scollegato
     *
     * @throws java.rmi.RemoteException
     */
    public void isAlive() throws RemoteException {
        //non fa nulla, serve al serve a sapere se è collegato
    }

    /**
     * Effettua l'unbind e spegne il client
     *
     * @throws RemoteException
     */
    public void disconnectForTimout() throws RemoteException {
        System.out.println(Message.DISCONNECT_FOR_TIMEOUT.toString());
        turnOff();
    }

    /**
     * Invia un messaggio al client
     *
     * @param message
     * @throws java.rmi.RemoteException
     */
    public void messageText(String message) throws RemoteException {
        typeOfInteraction.messageText(message);
    }

    /**
     * Invia un messaggio di errore al client
     *
     * @param message
     * @throws java.rmi.RemoteException
     */
    public void errorMessage(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    /**
     * Effettua l'unicast remote object e l'unbind
     */
    private void turnOff() {
        try {
            UnicastRemoteObject.unexportObject(this, true);
            Registry registry = LocateRegistry.getRegistry(PORT);
            registry.unbind(nickname);
        } catch (NoSuchObjectException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (RemoteException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Riceve dal server e invia al type of interaction il player aggiunto
     *
     * @param nickname
     * @param idPlayer
     * @throws RemoteException
     */
    public void refreshAddPlayer(String nickname, int idPlayer) throws RemoteException {
        typeOfInteraction.refreshAddPlayer(nickname, idPlayer);
    }

    /**
     * Riceve dal server e invia al type of interaction il player in attesa
     *
     * @param idPlayer
     * @throws RemoteException
     */
    public void refreshWaitPlayer(int idPlayer) throws RemoteException {
        typeOfInteraction.refreshWaitPlayer(idPlayer);
    }

    /**
     * Riceve dal server e invia al type of interaction il player disconnesso
     *
     * @param idPlayer
     * @param turnOff
     * @throws RemoteException
     */
    public void refreshTurnOffPlayer(int idPlayer, boolean turnOff) throws RemoteException {
        typeOfInteraction.refreshTurnOffPlayer(idPlayer, turnOff);
    }

    /**
     * Riceve dal server e invia al type of interaction il current player
     *
     * @param idPlayer
     * @throws RemoteException
     */
    public void refreshTurnPlayer(int idPlayer) throws RemoteException {
        typeOfInteraction.refreshTurnPlayer(idPlayer);
    }

}
