package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.TypeOfInteraction;
import java.io.Serializable;
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
public class ConnectionClientRMI extends UnicastRemoteObject implements ConnectionClient, ClientRMI, Serializable {

    private TypeOfInteraction typeOfInteraction;

    private final static int PORT = 3001;
    private String nickname;

    private Object tempRoad = null;

    private ConnectionRMI connectionRMI;

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
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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

    public void setNickname(String nickname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void placeShepard(int idRoad) {
        tempRoad = idRoad;
    }

    public void moveShepard(int idShepard, int idRoad) {
        try {
            connectionRMI.moveShepard(idShepard, idRoad);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void moveSheep(int idSheep, int idTerrain) {
        try {
            connectionRMI.moveSheep(idSheep, idTerrain);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buyCard(String typeOfTerrain) {
        try {
            connectionRMI.buyCard(typeOfTerrain);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void killSheep(int idSheep) {
        try {
            connectionRMI.killSheep(idSheep);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void joinSheep(int idTerrain) {
        try {
            connectionRMI.joinSheep(idTerrain);
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void wakeUp() throws RemoteException {
        typeOfInteraction.clickAction();
    }

    public void setNikcname() throws RemoteException {
        typeOfInteraction.setNickname();
    }

    public void errorCoin(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    public void errorMove(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    public void errorDice(String message) throws RemoteException {
        typeOfInteraction.errorMessage(message);
    }

    public int getPlaceShepard(int idShepard) throws RemoteException {
        typeOfInteraction.placeShepard(idShepard);

        Integer road = new Integer(tempRoad.toString());
        return road;
    }

    public void refreshMoveAnimal(int idAnimal, int idTerrain) throws RemoteException {
        typeOfInteraction.refreshMoveAnimal(idAnimal, idTerrain);
    }

    public void refreshAddAnimal(int idAnimal, String kind) throws RemoteException {
        typeOfInteraction.refreshAddAnimal(idAnimal, kind);
    }

    public void refreshKillAnimal(int idAnimal) throws RemoteException {
        typeOfInteraction.refreshKillAnimal(idAnimal);
    }

    public void refreshTransformAnimal(int idAnimal, String kind) throws RemoteException {
        typeOfInteraction.refreshTransformAnimal(idAnimal, kind);
    }

    public void refreshAddShepard(int idShepard, int idRoad) throws RemoteException {
        typeOfInteraction.refreshAddShepard(idShepard, idRoad);
    }

    public void refreshMoveShepard(int idShepard, int idRoad) throws RemoteException {
        typeOfInteraction.refreshMoveShepard(idShepard, idRoad);
    }

    public void refreshCard(String kind, boolean isSold) throws RemoteException {
        typeOfInteraction.refreshCard(kind, isSold);
    }

    public void refreshCoin(int coins, boolean addCoin) throws RemoteException {
        typeOfInteraction.refreshCoin(coins, addCoin);
    }
}
