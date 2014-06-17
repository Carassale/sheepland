package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManagerSocket;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnection;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnectionSocket;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionVariable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
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
import java.util.List;
import java.util.logging.Logger;

/**
 * Questa classe viene inizializzata direttamente dal main, ha il compito di
 * restare in attesa dei vari socket e ad ogni PAYER4GAME, o meno, avvia una
 * nuova partita. Questa classe viene creata nel sia stato scelto il method
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
    private static List<PlayerConnection> playerConnection;
    /**
     * È la lista delle parite avviate
     */
    private List<ConnectionManagerSocket> games;
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
     * e avvia il method waitPlayer (@override) che aspetta i socket.
     */
    public void run() {
        games = new ArrayList<ConnectionManagerSocket>();
        canAcceptSocket = true;
        try {
            outVideo.println("Socket: Inizializzazione socket...");
            serverSocket = new ServerSocket(ConnectionVariable.PORT_SOCKET);
            outVideo.println("Socket: Socket inizializzato, ora accetto richieste.");

            waitPlayer();
        } catch (IOException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
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
        playerConnection = new ArrayList<PlayerConnection>();
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
                playerConnection.add(new PlayerConnectionSocket(socket, id, nickname));

                //Se raggiungo il limite avvio il gioco
                if (playerConnection.size() == ServerVariable.PLAYER4GAME) {
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
     * È un method Synchronized per non permettere che sia uno o entrambi dei
     * thread e il method waitPlayer lo chiamino nello stesso istante, creando
     * quindi più partite con lo stesso Array di playerConnection (un client
     * gioca più partite).
     */
    public synchronized void runNewGame() {
        canAcceptSocket = false;
        if (playerConnection.size() >= 2) {
            games.add(new ConnectionManagerSocket(playerConnection, map));
            outVideo.println("Socket: Gioco avviato, " + games.size());
            playerConnection = new ArrayList<PlayerConnection>();
        }
        canAcceptSocket = true;
    }

    /**
     * Riceve una stringa dal socket contenente il nickname, controlla se è già
     * presente all'interno dell'hash map online o di un altro tipo di
     * connessione. Nel caso non possa collegarsi invia un messaggio di errore.
     *
     * @throws IOException
     */
    public void checkNickname() throws IOException {
        boolean doRepeat;
        do {
            BufferedReader inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            nickname = inSocket.readLine();

            if (playerIsOnLine(nickname)
                    || playerIsUncorrectType(nickname)) {
                outSocket.println(StatusMessage.NOT_CORRECT_NICKNAME.toString());
                outSocket.flush();
                outSocket.println("Socket: Questo nickname è già utilizzato da un altro player, controllare il tipo di connessione");
                outSocket.flush();

                doRepeat = true;
            } else {
                outSocket.println(StatusMessage.CORRECT_NICKNAME.toString());
                outSocket.flush();

                doRepeat = false;
            }
        } while (doRepeat);
    }

    private boolean playerIsOnLine(String nickname) {
        return map.existPlayer(nickname) && map.isOnLine(nickname);
    }

    private boolean playerIsUncorrectType(String nickname) {
        return map.existPlayer(nickname) && !map.isOnLine(nickname) && !map.isTypeConnectionSocket(nickname);
    }

    /**
     * Nel caso il client deve essere reindirizzato alla corretta partita,
     * questo method cerca il giocatore all'interno della partita e sostituisce
     * lo stub
     *
     * @param nickname Nome del client
     * @param socket socket del client
     * @throws IOException
     */
    private void pushToCorrectPlayer(String nickname, Socket socket) throws IOException {
        int idGame = map.getIdGame(nickname);
        int idPlayer = map.getIdPlayer(nickname);
        for (PlayerConnection player : games.get(idGame).getPlayerConnections()) {
            PlayerConnectionSocket playerConnectionSocket = (PlayerConnectionSocket) player;

            if (playerConnectionSocket.getIdPlayer() == idPlayer) {
                playerConnectionSocket.setSocket(socket);
                games.get(idGame).reconnectPlayer(idPlayer);
                return;
            }
        }
    }

    /**
     * Viene creato dal method waitPlayer e ha lo scopo di avviare la partita
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
         * Avvia un timer, una volta scaduto effettua la chiamata al method
         * runNewGame()
         */
        public void run() {
            try {
                outVideo.println("Socket: Timer avviato");
                this.threadTimer.sleep(ServerVariable.TIMEOUT);
                outVideo.println("Socket: Timer scaduto");
                if (playerConnection.size() >= 2) {
                    runNewGame();
                } else {
                    map.removePlayer(nickname);
                    outVideo.println("Socket: Non è stato raggiunto il minimo di giocatori, lista d'attesa azzerata");
                    ((PlayerConnectionSocket) playerConnection.get(0)).printLn(StatusMessage.DISCONNECTED_FOR_TIMEOUT.toString());
                    playerConnection = new ArrayList<PlayerConnection>();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            }
        }

        /**
         * Blocca il timeout, viene chiamato dal method waitPlayer() nel caso
         * sia stato raggiunto il numero massimo di connessioni
         */
        public void stop() {
            this.threadTimer.interrupt();
            outVideo.println("Socket: Timer fermato");
        }
    }
}
