package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManagerSocket;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnectionSocket;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Connection_Variable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe viene inizializzata direttamente dal main, ha il compito di
 * restare in attesa dei vari socket e ad ogni PAYER4GAME, o meno, avvia una
 * nuova partita. Questa classe viene creata nel sia stato scelto il metodo
 * Socket
 *
 * @author Carassale Gabriele
 */
public class ServerManagerSocket implements ServerManager {

    /**
     * È la lista dei giocatori in attesa (sempre minore di PLAYER4GAME), è
     * static perchè condivisa con il thread parallelo per l'avvio forzato della
     * partita
     */
    private static ArrayList<PlayerConnectionSocket> playerConnection;
    /**
     * È la lista delle parite avviate
     */
    private ArrayList<ConnectionManagerSocket> games;
    private SocketWaitingTimer swt;
    private boolean canAcceptSocket;
    private ServerSocket serverSocket;
    private MapServerPlayer map;

    private Socket socket;
    private String nickname;

    private PrintWriter outVideo;

    /**
     * Crea un serverManager di tipo Socket e avvia il thread
     *
     * @param map
     */
    public ServerManagerSocket(MapServerPlayer map) {
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        this.map = map;
        Thread threadManager = new Thread(this);
        threadManager.start();
    }

    /**
     * Inizializza l'array contente il numero di ConnctionManager di tipo Socket
     * (rappresentano il numero di partite avviate). Crea un serverSocket in
     * grado di ricevere connessioni in ingresso da parte dei socket dei Client
     * e avvia il metodo waitPlayer (@override) che aspetta i socket.
     */
    public void run() {
        games = new ArrayList<ConnectionManagerSocket>();
        canAcceptSocket = true;
        try {
            outVideo.println("Socket: Inizializzazione socket...");
            serverSocket = new ServerSocket(Connection_Variable.PORT_SOCKET);
            outVideo.println("Socket: Socket inizializzato, ora accetto richieste.");

            waitPlayer();
        } catch (IOException ex) {
            Logger.getLogger(ServerManagerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Resta in attesa del numero di giocatori massimi per avviare una partita,
     * in parallelo avvia un thread per controllare l'avvio per scadenza di
     * tempo
     *
     * @throws java.io.IOException
     */
    public void waitPlayer() throws IOException {
        playerConnection = new ArrayList<PlayerConnectionSocket>();
        int id;
        while (canAcceptSocket) {
            socket = serverSocket.accept();
            outVideo.println("Socket: Player collegato");

            //Controllo il nickname, se continua significa che o è nuovo, o era offline in connessione socket
            checkNickname();

            if (!map.existPlayer(nickname)) {

                id = playerConnection.size();
                //Aggiungo il player alla mappa dei client collegati
                map.addPlayer(nickname, StatusMessage.TYPE_SOCKET.toString(), games.size(), id);

                //Se è il primo avvio il timer
                if (playerConnection.isEmpty()) {
                    swt = new SocketWaitingTimer();
                }
                playerConnection.add(new PlayerConnectionSocket(socket, id));

                //Se raggiungo il limite avvio il gioco
                if (playerConnection.size() == Server_Variable.PLAYER4GAME) {
                    swt.stop();
                    runNewGame();
                }
            } else {
                //Sposto il socket nel gioco corretto
                pushToCorrectPlayer(nickname, socket);
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
        canAcceptSocket = false;
        if (playerConnection.size() >= 2) {
            games.add(new ConnectionManagerSocket(playerConnection));
            outVideo.println("Socket: Gioco avviato, " + games.size());
            playerConnection = new ArrayList<PlayerConnectionSocket>();
        }
        canAcceptSocket = true;
    }

    public void checkNickname() throws IOException {
        boolean doRepeat;
        do {
            BufferedReader inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            nickname = inSocket.readLine();

            if (nickname == null
                    || (map.existPlayer(nickname) && map.isOnLine(nickname))
                    || (map.existPlayer(nickname)
                    && !map.isOnLine(nickname)
                    && !map.isTypeConnectionSocket(nickname))) {
                outSocket.println(StatusMessage.NOT_CORRECT_NICKNAME.toString());
                outSocket.flush();
                outSocket.println("Questo nickname è già utilizzato da un altro player, controllare il tipo di connessione");
                outSocket.flush();

                doRepeat = true;
            } else {
                outSocket.println(StatusMessage.CORRECT_NICKNAME.toString());
                outSocket.flush();

                doRepeat = false;
            }
        } while (doRepeat);
    }

    private void pushToCorrectPlayer(String nickname, Socket socket) throws IOException {
        int idGame = map.getIdGame(nickname);
        int idPlayer = map.getIdPlayer(nickname);
        for (PlayerConnectionSocket playerConnectionSocket : games.get(idGame).getPlayerConnections()) {
            if (playerConnectionSocket.getIdPlayer() == idPlayer) {
                playerConnectionSocket.setSocket(socket);
                games.get(idGame).refreshAllToPlayer(idPlayer);
                return;
            }
        }
    }

    /**
     * Viene creato dal metodo waitPlayer e ha lo scopo di avviare la partita
     * nel caso scadi il TIMEOUT
     */
    private class SocketWaitingTimer implements Runnable {

        private Thread threadTimer;

        /**
         * Implementa un Runnable, ha come attributo un Thread, qui nel
         * costruttore viene inizializzato il Thread passandoli come parametro
         * This (Runnable) e viene effettuato lo start sul Thread, in questo
         * modo alla creazione della classe viene anche avviata.
         */
        public SocketWaitingTimer() {
            this.threadTimer = new Thread(this);
            this.threadTimer.start();
        }

        /**
         * Avvia un timer, una volta scaduto effettua la chiamata al metodo
         * runNewGame()
         */
        public void run() {
            try {
                outVideo.println("Socket: Timer avviato");
                this.threadTimer.sleep(Server_Variable.TIMEOUT);
                outVideo.println("Socket: Timer scaduto");
                if (playerConnection.size() >= 2) {
                    runNewGame();
                } else {
                    swt = new SocketWaitingTimer();
                }
            } catch (InterruptedException ex) {
                outVideo.println("Socket: Timer fermato");
                Logger.getLogger(ServerManagerSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * Blocca il timeout, viene chiamato dal metodo waitPlayer() nel caso
         * sia stato raggiunto il numero massimo di connessioni
         */
        public void stop() {
            this.threadTimer.interrupt();
            outVideo.println("Socket: Timer fermato");
        }
    }
}
