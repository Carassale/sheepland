package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import java.io.IOException;
import java.net.Socket;

/**
 * È il main per il Client, ha il compito di provare a collegarsi con uno dei
 metodi, Socket o RMI, e avviare il ConnectionInitializer corrispondente
 *
 * @author Carassale Gabriele
 */
public class ConnectionInitializer {

    private final static int PORT = 3002;
    private final static String address = "localhost";

    private boolean connected;



   
    public ConnectionInitializer() {
        
    }
    
     /**
     * Prova "all'infinito" a stabilire una connessione con i metodi
     * tryConnectionSocket e tryConnectionRMI
     */
    public void connectToServer(String string){
        connected = false;
        
        if("Socket".equals(string))
            
            while (!connected) {
                tryConnectionSocket();
            }
        else

            while (!connected) {
                tryConnectionRMI();
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
