package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.TypeOfInteraction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ServerRMI;
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
public class ConnectionClientRMI implements ConnectionClient, ClientRMI, Serializable {

    private TypeOfInteraction typeOfInteraction;

    /**
     * Viene usato solo per inizializzare poi li verrà assegnato un
     * CconnectionRMI
     */
    private ServerRMI serverRMI;
    private final static int PORT = 3001;
    private String nickname;

    private ConnectionRMI connectionRMI;

    /**
     * Crea un connection client di tipo RMI passando lo stub del server
     *
     * @param serverRMI
     * @param nickname
     */
    public ConnectionClientRMI(ServerRMI serverRMI, String nickname) {
        this.serverRMI = serverRMI;
        this.nickname = nickname;
    }

    public void createBind() {
        try {
            ClientRMI clientRmi = (ClientRMI) UnicastRemoteObject.exportObject(this);
            
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

    public void wakeUp() throws RemoteException {
        typeOfInteraction.clickAction();
    }

    public void setNickname(String nickname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void placeShepard(int idRoad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void moveShepard(int idShepard, int idRoad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void moveSheep(int idSheep, int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void buyCard(String typeOfTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void killSheep(int idSheep) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void joinSheep(int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setNikcname() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void errorCoin(String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void errorMove(String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void errorDice(String message) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshMoveAnimal(int idAnimal, int idTerrain) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshAddAnimal(int idAnimal, String kind) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshKillAnimal(int idAnimal) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshTransformAnimal(int idAnimal, String kind) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshAddShepard(int idShepard, int idRoad) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshMoveShepard(int idShepard, int idRoad) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshCard(String kind, boolean isSold) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshCoin(int coins, boolean addCoin) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
