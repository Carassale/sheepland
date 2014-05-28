package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller.GameController;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionRMI;
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
public class ConnectionManagerRMI extends ConnectionManager implements ConnectionRMI {

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
        thread = new Thread(this);
        thread.start();
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
            }
        }
        nextPlayerConnections();
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
