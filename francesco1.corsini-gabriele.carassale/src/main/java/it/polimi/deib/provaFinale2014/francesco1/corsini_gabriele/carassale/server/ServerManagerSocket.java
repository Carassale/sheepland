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
    private final static int PORT = 3000;
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
     * prima di avviare forzatamente una partita
     */
    private final static int TIMEOUT = 2000;
    private ServerSocket serverSocket;

    public ServerManagerSocket() {
        games = new ArrayList<ConnectionManagerSocket>();
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        try {
            waitForPlayer();
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
    public void waitForPlayer() throws IOException {
        playerConnection = new ArrayList<PlayerConnectionSocket>();
        SocketWaitingForce swf = new SocketWaitingForce();
        while (true) {
            Socket socket = serverSocket.accept();
            if (playerConnection.size() == 0) {
                SocketWaitingTimer swt = new SocketWaitingTimer();
            }
            playerConnection.add(new PlayerConnectionSocket(socket));
            if (playerConnection.size() == PLAYER4GAME) {
                runNewGame();
            }
        }
    }

    public synchronized void runNewGame() {
        if (playerConnection.size() >= 2) {
            games.add(new ConnectionManagerSocket(playerConnection));
            playerConnection = new ArrayList<PlayerConnectionSocket>();
        }
    }

    /**
     * È un thread, viene avviato dal metodo waitForPlayer e ha lo scopo di
     * ascoltare in parallelo le richieste di avvio forzato
     */
    private class SocketWaitingForce implements Runnable {

        private Thread thread;

        public SocketWaitingForce() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (true) {
                for (PlayerConnectionSocket playerConnection1 : playerConnection) {
                    if ("ForceGame".equals(playerConnection1.getScanner().nextLine()) && playerConnection.size() >= 2) {
                        runNewGame();
                    }
                }
            }
        }

    }

    /**
     * È un thread, viene avviato dal metodo waitForPlayer e ha lo scopo di far
     * partire una partita nel caso sia scaduto un timout
     */
    private class SocketWaitingTimer implements Runnable {

        private Thread thread;

        public SocketWaitingTimer() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (true) {
                for (PlayerConnectionSocket playerConnection1 : playerConnection) {
                    if ("ForceGame".equals(playerConnection1.getScanner().nextLine()) && playerConnection.size() >= 2) {
                        runNewGame();
                    }
                }
            }
        }

    }
}
