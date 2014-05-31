package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.TypeOfInteraction;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea la connessione diretta con il GameController tramite la
 * ConnectionManager nel caso sia stato scelto il metodo RMI
 *
 * @author Carassale Gabriele
 */
public class ConnectionClientRMI extends UnicastRemoteObject implements ConnectionClient, ClientRMI {

    private TypeOfInteraction typeOfInteraction;

    private static final int PORT = 3001;

    /**
     * Questa variabile server solo per il metodo placeShepard, serve per
     * aspettare la scelta del clinet
     */
    private Object tempRoad = null;

    /**
     * È il collegamento allo stub del serve sul quale il client può eseguire
     * dei metodi
     */
    private ConnectionRMI connectionRMI;

    /**
     * Crea un connection client di tipo RMI passando lo stub del server
     *
     * @param nickname
     * @throws java.rmi.RemoteException
     */
    public ConnectionClientRMI(String nickname) throws RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry(PORT);
            registry.rebind(nickname, this);

        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
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
    public void placeShepard(int idRoad) {
        tempRoad = idRoad;
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il metodo è muovi il pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     */
    public void moveShepard(int idShepard, int idRoad) {
        try {
            String s = connectionRMI.moveShepard(idShepard, idRoad);
            if (StatusMessage.ACTION_ERROR.toString().equals(s)) {
                typeOfInteraction.errorMessage(s);
            } else {
                typeOfInteraction.messageText(s);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il metodo è muovi pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     */
    public void moveSheep(int idSheep, int idTerrain) {
        try {
            String s = connectionRMI.moveSheep(idSheep, idTerrain);
            if (StatusMessage.ACTION_ERROR.toString().equals(s)) {
                typeOfInteraction.errorMessage(s);
            } else {
                typeOfInteraction.messageText(s);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il metodo è compra una carta
     *
     * @param typeOfTerrain Tipo di carta da comprare
     */
    public void buyCard(String typeOfTerrain) {
        try {
            String s = connectionRMI.buyCard(typeOfTerrain);
            if (StatusMessage.ACTION_ERROR.toString().equals(s)) {
                typeOfInteraction.errorMessage(s);
            } else {
                typeOfInteraction.messageText(s);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il metodo è uccidi pecora
     *
     * @param idSheep Pecora da uccidere
     */
    public void killSheep(int idSheep) {
        try {
            String s = connectionRMI.killSheep(idSheep);
            if (StatusMessage.ACTION_ERROR.toString().equals(s)) {
                typeOfInteraction.errorMessage(s);
            } else {
                typeOfInteraction.messageText(s);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Viene invocato dal typeOfInteraction e inoltra la chiamata al server, in
     * questo caso il metodo è accoppia ovini
     *
     * @param idTerrain Terreno dove sono presenti un montone e una pecora
     */
    public void joinSheep(int idTerrain) {
        try {
            String s = connectionRMI.joinSheep(idTerrain);
            if (StatusMessage.ACTION_ERROR.toString().equals(s)) {
                typeOfInteraction.errorMessage(s);
            } else {
                typeOfInteraction.messageText(s);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è clickAction e server a risvegliare il client e
     * chiede la mossa che disidera fare
     *
     * @throws RemoteException
     */
    public void wakeUp() throws RemoteException {
        typeOfInteraction.clickAction();
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è un messaggio per errore di monete
     *
     * @param message Messaggio da mostrare
     * @throws RemoteException
     */
    public void errorCoin(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è un messaggio per errore di movimento
     *
     * @param message Messaggio da mostrare
     * @throws RemoteException
     */
    public void errorMove(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è un messaggio per errore di fine carte
     *
     * @param message Messaggio da mostrare
     * @throws RemoteException
     */
    public void errorCard(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è un messaggio per errore di dado
     *
     * @param message Messaggio da mostrare
     * @throws RemoteException
     */
    public void errorDice(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è posiziona pastore
     *
     * @param idShepard Pastore da posizionare
     * @return Strada scelta dal client
     * @throws RemoteException
     */
    public int getPlaceShepard(int idShepard) throws RemoteException {
        typeOfInteraction.placeShepard(idShepard);

        return Integer.valueOf(tempRoad.toString());
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è refresh sul movimento dell'animale
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
     * questo caso il metodo è refresh sull'aggiunta dell'animale
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
     * questo caso il metodo è refresh cancella animale
     *
     * @param idAnimal Animale da cancellare
     * @throws RemoteException
     */
    public void refreshKillAnimal(int idAnimal) throws RemoteException {
        typeOfInteraction.refreshKillAnimal(idAnimal);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è refresh trasforma animale
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
     * questo caso il metodo è refresh aggiungi pastore
     *
     * @param idShepard Pastore da aggiungere
     * @param idRoad Strada posizionamento
     * @throws RemoteException
     */
    public void refreshAddShepard(int idShepard, int idRoad) throws RemoteException {
        typeOfInteraction.refreshAddShepard(idShepard, idRoad);
    }

    public void refreshAddShepard(int idShepard, int idRoad, boolean isMine) throws RemoteException {
        typeOfInteraction.refreshAddShepard(idShepard, idRoad, isMine);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è refresh muovi pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     * @throws RemoteException
     */
    public void refreshMoveShepard(int idShepard, int idRoad) throws RemoteException {
        typeOfInteraction.refreshMoveShepard(idShepard, idRoad);
    }

    /**
     * Viene invocato dal server inoltra la chiamata al typeOfInteraction, in
     * questo caso il metodo è refresh carte
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
     * questo caso il metodo è refresh monete
     *
     * @param coins Monete cambiate
     * @param addCoin True se sono aggiunte, False se vanno levate
     * @throws RemoteException
     */
    public void refreshCoin(int coins, boolean addCoin) throws RemoteException {
        typeOfInteraction.refreshCoin(coins, addCoin);
    }
}
