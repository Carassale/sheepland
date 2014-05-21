package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import java.io.IOException;
import java.net.Socket;

/**
 * È il main per il Client, ha il compito di provare a collegarsi con uno dei
 * metodi, Socket o RMI, e avviare il ConnectionClient corrispondente
 *
 * @author Carassale Gabriele
 */
public class ConnectionClient {

    private final static int PORT = 3002;
    private final static String address = "localhost";

    private boolean connected;

    /**
     * Fa partire il programma del Client
     *
     * @param arg
     */
    public static void main(String arg[]) {
        ConnectionClient cc = new ConnectionClient();
    }

    /**
     * Prova "all'infinito" a stabilire una connessione con i metodi
     * tryConnectionSocket e tryConnectionRMI
     */
    public ConnectionClient() {
        connected = false;
        while (!connected) {
            tryConnectionSocket();

            if (!connected) {
                tryConnectionRMI();
            }
        }
    }

    private void tryConnectionSocket() {
        System.out.println("Il client tenta di connettersi tramite socket");
        Socket socket = null;
        try {
            socket = new Socket(address, PORT);
            System.out.println("Client connesso");
            connected = true;
        } catch (Exception e) {
            System.out.println("Connessione tramite Socket non riuscita.");
        }
        if (connected) {
            try {
                ConnectionClientSocket ccs = new ConnectionClientSocket(socket);
            } catch (IOException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void tryConnectionRMI() {
        System.out.println("Il client tenta di connettersi tramite RMI");
        //TODO
        System.out.println("Connessione tramite RMI non riuscita.");
    }

}
