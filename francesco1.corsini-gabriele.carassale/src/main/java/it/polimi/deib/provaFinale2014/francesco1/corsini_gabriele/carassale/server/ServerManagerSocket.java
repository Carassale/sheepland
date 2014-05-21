package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManagerSocket;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.PlayerConnectionSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
     * È la porta sulla quale avvengono le comunicazioni tra client e server
     */
    private final static int PORT = 3002;
    /**
     * È il numero massimo di giocatori per partita, una volta raggiunto tale
     * numero di connessioni viene avviata una nuova partita tramite
     * ConnectionManagerSocket
     */
    private final static int PLAYER4GAME = 4;
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
    /**
     * È il tempo di attesa massimo che il thread SocketWaitingTimer aspetta
     * prima di avviare forzatamente una partita 2min = 2*60*1000 = 240.000
     * millisec
     */
    private final static int TIMEOUT = 10000;
    private SocketWaitingTimer swt;
    private boolean canAcceptSocket;
    private ServerSocket serverSocket;

    public ServerManagerSocket() {
        games = new ArrayList<ConnectionManagerSocket>();
        canAcceptSocket = true;
        try {
            serverSocket = new ServerSocket(PORT);
            waitPlayer();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Resta in attesa del numero di giocatori massimi per avviare una partita,
     * in parallelo avvia un thread per controllare l'avvio forzato da parte di
     * un utente
     *
     * @throws java.io.IOException
     */
    @Override
    public void waitPlayer() throws IOException {
        playerConnection = new ArrayList<PlayerConnectionSocket>();
        while (canAcceptSocket) {
            Socket socket = serverSocket.accept();
            if (playerConnection.isEmpty()) {
                swt = new SocketWaitingTimer();
            }
            playerConnection.add(new PlayerConnectionSocket(socket));
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
        canAcceptSocket = false;
        if (playerConnection.size() >= 2) {
            games.add(new ConnectionManagerSocket(playerConnection));
            System.out.println("Gioco Avviato");
            playerConnection = new ArrayList<PlayerConnectionSocket>();
        }
        canAcceptSocket = true;
    }

    /**
     * Questa classe implementa un Runnable e ha come attributo un Thread, nel
     * costruttore della classe al Thread viene passato this (Runnable) e si
     * auto-avvia (.start()). Viene creato dal metodo waitPlayer e ha lo scopo
     * di avviare la partita nel caso scadi un TIMEOUT
     */
    private class SocketWaitingTimer implements Runnable {

        private Thread thread;

        public SocketWaitingTimer() {
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
