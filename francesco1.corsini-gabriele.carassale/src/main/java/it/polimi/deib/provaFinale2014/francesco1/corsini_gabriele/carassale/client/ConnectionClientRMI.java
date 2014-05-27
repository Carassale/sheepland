package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.StubRMI;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea la connessione diretta con il GameController tramite la
 * ConnectionManager nel caso sia stato scelto il metodo RMI
 *
 * @author Carassale Gabriele
 */
public class ConnectionClientRMI implements ConnectionClient {

    private TypeOfInteraction typeOfInteraction;
    private int idConnection;
    private Registry registry;
    private StubRMI stubRMI;

    /**
     * Crea un connection client di tipo RMI passando l'id della connessione
     *
     * @param idConnection Id della connessione
     * @param registry
     * @param stubRMI
     */
    public ConnectionClientRMI(int idConnection, Registry registry, StubRMI stubRMI) {
        this.idConnection = idConnection;
        this.registry = registry;
        this.stubRMI = stubRMI;
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
     * Imposta il proprio nickname
     *
     * @param nickname Stringa da settare
     */
    public void setNickname(String nickname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Resta in attesa di un comando da parte del Server
     */
    public void waitLine() {
        while (true) {
            try {
                String result = stubRMI.checkStatus(idConnection);
                System.out.println(result);
            } catch (RemoteException ex) {
                Logger.getLogger(ConnectionClientRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Muove il pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     */
    public void moveShepard(int idShepard, int idRoad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Muove la pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     */
    public void moveSheep(int idSheep, int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Compra una carta
     *
     * @param typeOfTerrain Tipo di carta
     */
    public void buyCard(String typeOfTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Uccide una pecora
     *
     * @param idSheep Pecora da uccidere
     */
    public void killSheep(int idSheep) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Accoppia una pecora con un montone
     *
     * @param idTerrain Terreno in cui si trovano pecora e montone
     */
    public void joinSheep(int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Piazza un pastore
     *
     * @param idRoad Strada dove posizionare
     */
    public void placeShepard(int idRoad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
