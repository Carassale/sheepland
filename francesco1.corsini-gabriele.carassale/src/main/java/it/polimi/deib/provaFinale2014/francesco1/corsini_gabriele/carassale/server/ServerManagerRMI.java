package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManagerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnectionRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionVariable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ServerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
    private RMIWaitingTimer swt;
    private boolean canAccept;
    private MapServerPlayer map;

    private PrintWriter outVideo;

    /**
     * Crea un ServerManager di tipo RMI, ancora da implementare
     *
     * @param map
     */
    public ServerManagerRMI(MapServerPlayer map) {
        this.map = map;
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        Thread threadManager = new Thread(this);
        threadManager.start();
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
            outVideo.println("RMI: Registrazione registry...");

            //Naming.bind(SERVER_NAME, this); OR
            UnicastRemoteObject.exportObject(this, ConnectionVariable.PORT_RMI);
            Registry registry = LocateRegistry.createRegistry(ConnectionVariable.PORT_RMI);
            registry.rebind(ConnectionVariable.SERVER_NAME, this);

            outVideo.println("RMI: Registry registrato, ora accetto richieste");
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
            outVideo.println("RMI: Gioco avviato, " + games.size());
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
        outVideo.println("RMI: Player collegato");
        return StatusMessage.CONNECTED.toString();
    }

    public String checkNickname(String nickname) {
        if ((map.existPlayer(nickname) && map.isOnLine(nickname))
                || (map.existPlayer(nickname)
                && !map.isOnLine(nickname)
                && !map.isTypeConnectionSocket(nickname))) {
            return StatusMessage.NOT_CORRECT_NICKNAME.toString();
        } else {
            return StatusMessage.CORRECT_NICKNAME.toString();
        }
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
    public String addClient(ClientRMI clientRMI, String nickname) throws RemoteException {
        if (!map.existPlayer(nickname)) {
            if (canAccept) {
                int id = playerConnection.size();

                //Aggiunge client RMI
                playerConnection.add(new PlayerConnectionRMI(clientRMI, id));

                if (playerConnection.size() == 1) {
                    swt = new RMIWaitingTimer();
                }
                if (playerConnection.size() == ServerVariable.PLAYER4GAME) {
                    swt.stop();
                    runNewGame();
                }

                return StatusMessage.PLAYER_ADDED.toString();
            } else {
                return StatusMessage.NO_PLAYER_ADDED.toString();
            }
        } else {
            pushToCorrectPlayer(nickname, clientRMI);
            return StatusMessage.PLAYER_ADDED.toString();
        }
    }

    private void pushToCorrectPlayer(String nickname, ClientRMI clientRMI) {
        int idGame = map.getIdGame(nickname);
        int idPlayer = map.getIdPlayer(nickname);
        for (PlayerConnectionRMI playerConnectionRMI : games.get(idGame).getPlayerConnections()) {
            if (playerConnectionRMI.getIdPlayer() == idPlayer) {
                playerConnectionRMI.setClientRMI(clientRMI);
                games.get(idGame).refreshAllToPlayer(idPlayer);
                return;
            }
        }
    }

    /**
     * Viene creato dal metodo waitPlayer e ha lo scopo di avviare la partita
     * nel caso scadi il TIMEOUT
     */
    private class RMIWaitingTimer implements Runnable {

        private Thread threadTimer;

        /**
         * Implementa un Runnable, ha come attributo un Thread, qui nel
         * costruttore viene inizializzato il Thread passandoli come parametro
         * This (Runnable) e viene effettuato lo start sul Thread, in questo
         * modo alla creazione della classe viene anche avviata.
         */
        public RMIWaitingTimer() {
            this.threadTimer = new Thread(this);
            this.threadTimer.start();
        }

        /**
         * Avvia un timer, una volta scaduto effettua la chiamata al metodo
         * runNewGame()
         */
        public void run() {
            try {
                outVideo.println("RMI: Timer avviato");
                this.threadTimer.sleep(ServerVariable.TIMEOUT);
                outVideo.println("RMI: Timer scaduto");
                if (playerConnection.size() >= 2) {
                    runNewGame();
                } else {
                    swt = new RMIWaitingTimer();
                }
            } catch (InterruptedException ex) {
                outVideo.println("RMI: Timer fermato");
                Logger.getLogger(ServerManagerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Blocca il timeout, viene chiamato dal metodo waitPlayer() nel caso
         * sia stato raggiunto il numero massimo di connessioni
         */
        public void stop() {
            this.threadTimer.interrupt();
            outVideo.println("RMI: Timer fermato");
        }
    }
}
