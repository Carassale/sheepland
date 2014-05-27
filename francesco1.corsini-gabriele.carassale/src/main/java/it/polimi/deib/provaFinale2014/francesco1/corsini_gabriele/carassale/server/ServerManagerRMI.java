package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManagerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.StubRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.StubRMIImpl;
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
public class ServerManagerRMI implements ServerManager {

    private final static int PORT = 3001;
    private static ArrayList<PlayerConnectionRMI> playerConnection;
    private ArrayList<ConnectionManagerRMI> games;
    private Thread thread;
    private RMIWaitingTimer swt;
    private boolean canAccept;

    /**
     * È il nome del ServerManagerRMI, usato per le connessioni
     */
    public final static String SERVER_NAME = "managerRMI";

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
            StubRMI stubRMI = new StubRMIImpl(playerConnection);
            StubRMI stub = (StubRMI) UnicastRemoteObject.exportObject(stubRMI, PORT);
            Registry registry = LocateRegistry.createRegistry(PORT);
            registry.rebind(SERVER_NAME, stub);

            waitPlayer();
        } catch (RemoteException ex) {
            Logger.getLogger(ServerManagerRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Resta in attesa del numero di giocatori massimi per avviare una partita,
     * in parallelo avvia un thread per controllare l'avvio forzato da parte di
     * un utente
     *
     * @throws java.io.IOException
     */
    private void waitPlayer() {
        while (canAccept) {
            //Accetta connessione RMI
            if (playerConnection.size() == 1) {
                swt = new RMIWaitingTimer();
            }
            if (playerConnection.size() == PLAYER4GAME) {
                swt.stop();
                runNewGame();
            }
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
            games.add(new ConnectionManagerRMI(playerConnection));
            System.out.println("Gioco Avviato");
            playerConnection = new ArrayList<PlayerConnectionRMI>();
        }
        canAccept = true;
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
