package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManagerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ServerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe viene inizializzata direttamente dal main, ha il compito di
 * restare in attesa dei vari socket e ad ogni PAYER4GAME, o meno, avvia una
 * nuova partita. Questa classe viene creata nel sia stato scelto il metodo RMI
 *
 * @author Carassale Gabriele
 */
public class ServerManagerRMI implements ServerManager, ServerRMI {

    private static ArrayList<PlayerConnectionRMI> playerConnection;
    private ArrayList<ConnectionManagerRMI> games;
    private Thread thread;
    private RMIWaitingTimer swt;
    private boolean canAccept;

    /**
     * È il nome del ServerManagerRMI, usato per le connessioni
     */
    public static final String SERVER_NAME = "ServerManagerRMI";

    public static final int PORT = 3001;

    /**
     * Crea un ServerManager di tipo RMI, ancora da implementare
     */
    public ServerManagerRMI() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Inizializza l'array contente il numero di ConnctionManager di tipo RMI
     * (rappresentano il numero di partite avviate). Crea un serverRMI in grado
     * di ricevere connessioni in ingresso da parte dei Client e avvia il metodo
     * waitPlayer (@override).
     */
    public void run() {
        games = new ArrayList<ConnectionManagerRMI>();
        playerConnection = new ArrayList<PlayerConnectionRMI>();
        canAccept = true;

        try {
            System.out.println("RMI: Registrazione...");

            //Naming.bind(SERVER_NAME, this); OR
            UnicastRemoteObject.exportObject(this, PORT);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind(SERVER_NAME, this);

            System.out.println("RMI: Registrato");
        } catch (RemoteException ex) {
            Logger.getLogger(ServerManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * È un metodo Synchronized per non permettere che sia uno o entrambi dei
     * thread e il metodo waitPlayer lo chiamino nello stesso istante, creando
     * quindi più partite con lo stesso Array di playerConnection (un client
     * gioca più partite).
     */
    public synchronized void runNewGame() {
        canAccept = false;
        if (playerConnection.size() >= 2) {
            try {
                games.add(new ConnectionManagerRMI(playerConnection));
            } catch (RemoteException ex) {
                Logger.getLogger(ServerManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Gioco Avviato");
            playerConnection = new ArrayList<PlayerConnectionRMI>();
        }
        canAccept = true;
    }

    /**
     * Implementa il metodo dell'interfaccia server, riceve una chiamata dal
     * client e manda un messaggio di avenuta connessione
     *
     * @return Messaggio di avvenuta connessione "connected
     * @throws RemoteException
     */
    public String connect() throws RemoteException {
        return StatusMessage.connected.toString();
    }

    /**
     * Aggiunge giocatori alla partita a una loro richiesta, controlla il numero
     * di giocatore massimi per avviare una partita, in parallelo avvia un
     * thread per controllare l'avvio per scadenza di tempo
     *
     * @param clientRMI Interfaccia Client da aggiungere
     * @return "playerAdded" se aggiunto, "noPlayerAdded" se non aggiunto
     * @throws RemoteException
     */
    public String addClient(ClientRMI clientRMI) throws RemoteException {
        if (canAccept) {
            //Aggiunge client RMI
            playerConnection.add(new PlayerConnectionRMI(clientRMI));

            if (playerConnection.size() == 1) {
                swt = new RMIWaitingTimer();
            }
            if (playerConnection.size() == PLAYER4GAME) {
                swt.stop();
                runNewGame();
            }

            return StatusMessage.playerAdded.toString();
        }
        return StatusMessage.noPlayerAdded.toString();
    }

    /**
     * Viene creato dal metodo waitPlayer e ha lo scopo di avviare la partita
     * nel caso scadi il TIMEOUT
     */
    private class RMIWaitingTimer implements Runnable {

        private Thread thread;

        /**
         * Implementa un Runnable, ha come attributo un Thread, qui nel
         * costruttore viene inizializzato il Thread passandoli come parametro
         * This (Runnable) e viene effettuato lo start sul Thread, in questo
         * modo alla creazione della classe viene anche avviata.
         */
        public RMIWaitingTimer() {
            this.thread = new Thread(this);
            this.thread.start();
        }

        /**
         * Avvia un timer, una volta scaduto effettua la chiamata al metodo
         * runNewGame()
         */
        public void run() {
            try {
                System.out.println("Timer avviato");
                this.thread.sleep(TIMEOUT);
                System.out.println("Timer scaduto");
                runNewGame();
            } catch (InterruptedException ex) {
                Logger.getLogger(ServerManagerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Blocca il timeout, viene chiamato dal metodo waitPlayer() nel caso
         * sia stato raggiunto il numero massimo di connessioni
         */
        public void stop() {
            this.thread.interrupt();
            System.out.println("Timer fermato");
        }
    }
}
