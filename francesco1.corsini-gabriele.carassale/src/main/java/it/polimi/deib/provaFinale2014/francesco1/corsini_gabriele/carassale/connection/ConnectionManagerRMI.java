package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea il collegamento diretto tra il GameController e il Client
 * gestito da un ConnectionClient di tipo RMI
 *
 * @author Carassale Gabriele
 */
public class ConnectionManagerRMI extends ConnectionManager implements ConnectionRMI, Serializable {

    private final static int NUMACTION = 3;
    private final ArrayList<PlayerConnectionRMI> playerConnections;
    private PlayerConnectionRMI currentPlayer;
    private GameController gameController;
    private Thread thread;

    private boolean canDoAction;

    /**
     * Inizializza il Thread passandoli come parametro This (Runnable) e lo
     * avvia col la chiamata al metodo .start()
     *
     * @param playerConnections ArrayList contenente i player associati a questa
     * partita
     */
    public ConnectionManagerRMI(ArrayList<PlayerConnectionRMI> playerConnections) {
        this.playerConnections = playerConnections;
        this.canDoAction = true;

        changeReferenceToClient();

        thread = new Thread(this);
        thread.start();
    }

    public void changeReferenceToClient() {
        for (PlayerConnectionRMI playerConnectionRMI : playerConnections) {
            try {
                playerConnectionRMI.getClientRMI().setConnectionRMI(this);
            } catch (RemoteException ex) {
                Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Memorizza il currentPlayer prelevandolo dal primo elemento dell'Array,
     * crea il GameController e successivamente lo avvia
     */
    @Override
    public void startThread() {
        currentPlayer = playerConnections.get(0);
        gameController = new GameController(this);
        gameController.start(playerConnections.size());
        //setNickName();
    }

    /**
     * Cicla per il numero di azioni massime consentite il metodo doAction, in
     * caso di ritorno false dal doAction fa ripetere il metodo finchè non
     * vengono effettuate un numero corretto di azioni, alla fine chiama il
     * metodo nextPlayerConnection
     */
    @Override
    public void startAction() {
        for (int i = 0; i < NUMACTION; i++) {
            if (canDoAction) {
                doAction();
            } else {
                i--;
            }
        }
        nextPlayerConnections();
    }

    /**
     * Scorre la lista dei Player, sposta il primo in ultima posizione
     */
    @Override
    public void nextPlayerConnections() {
        playerConnections.add(playerConnections.get(0));
        playerConnections.remove(0);

        currentPlayer = playerConnections.get(0);
    }

    /**
     * Risveglia il currentPlayer, riceve indietro un valore se l'azione è
     * andata a buon fine
     *
     * @return True se l'azione è andata a buon fine
     */
    private void doAction() {
        canDoAction = false;
        try {
            currentPlayer.getClientRMI().wakeUp();
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo chiamato dal gameController per serializzare la comunicazione
     * iniziale degli Shepard dei vari giocatori
     *
     * @param idShepard
     * @return Road dove posizionare lo Shepard
     */
    @Override
    public Road getPlacedShepard(int idShepard) {
        try {
            //dice al client di piazzare Shepard
            Integer id = currentPlayer.getClientRMI().getPlaceShepard(idShepard);

            //ricava l'oggetto
            Road roadChoosen = gameController.getGameTable().idToRoad(id);

            return roadChoosen;
        } catch (RemoteException ex) {
            Logger.getLogger(ConnectionManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String moveShepard(int idShepard, int idRoad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String moveSheep(int idSheep, int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String buyCard(String typeOfTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String joinSheep(int idTerrain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String killSheep(int idSheep) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
