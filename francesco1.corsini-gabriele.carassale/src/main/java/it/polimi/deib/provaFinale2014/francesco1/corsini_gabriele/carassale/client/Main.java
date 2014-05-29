package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ServerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUIDinamic;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUISwingStatic;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.LineCommand;
import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * È il main del Client, serve a scegliere inizialmente come si desidera
 * giocare: il tipo di connessione e il tipo di interfaccia
 *
 * @author Carassale Gabriele
 */
public class Main {

    private static final int PORT_SOCKET = 3002;
    private static final int PORT_RMI = 3001;
    private static final String address = "localhost";

    private String SOCKET = "socket";
    private String RMI = "rmi";

    /**
     * È il nome del ServerManagerRMI, usato per le connessioni
     */
    public static final String SERVER_NAME = "ServerManagerRMI";

    ConnectionClient connectionClient;

    private boolean connected;
    private String nickname;

    /**
     * Crea la classe Main, gestisce l'avvio del client
     *
     * @param arg
     */
    public static void main(String arg[]) {
        Main main = new Main();
    }

    /**
     * Inizializza il necessario per interagire con l'utente, chiede il tipo di
     * connessione e il tipo di interazione.
     */
    public Main() {
        Scanner keyboard = new Scanner(System.in);
        connected = false;
        connectionClient = null;

        System.out.println("Messaggio di Benvenuto");

        System.out.println("Inserisci il tuo nickname");
        nickname = keyboard.nextLine();

        int typeConnection = 0;
        do {
            System.out.println("Scegliere il modello di connessione Client-Server:");
            System.out.println("1 - Connessione tramite Socket");
            System.out.println("2 - Connessione tramite RMI");
            typeConnection = keyboard.nextInt();
        } while (typeConnection < 1 || typeConnection > 2);

        if (typeConnection == 1) {
            connectToServer(SOCKET);
        } else {
            connectToServer(RMI);
        }

        int typeView = 0;
        do {
            System.out.println("Scegliere il modo di interegire:");
            System.out.println("1 - Linea di comando");
            System.out.println("2 - Interfaccia grafica statica");
            System.out.println("3 - Interfaccia grafica dinamica");
            typeView = keyboard.nextInt();
        } while (typeView < 1 || typeView > 3);

        switch (typeView) {
            case 1:
                connectionClient.setTypeOfInteraction(new LineCommand(connectionClient));
                break;
            case 2:
                connectionClient.setTypeOfInteraction(new GUISwingStatic(connectionClient));
                break;
            case 3:
                connectionClient.setTypeOfInteraction(new GUIDinamic(connectionClient));
                break;
        }

        connectionClient.waitLine();
    }

    /**
     * Prova "all'infinito" a connettere il client al server
     *
     * @param typeConnection Tipo di connessione da usare
     */
    private void connectToServer(String typeConnection) {
        connected = false;
        if (SOCKET.equals(typeConnection)) {
            while (!connected) {
                tryConnectionSocket();
            }
        } else if (RMI.equals(typeConnection)) {
            while (!connected) {
                tryConnectionRMI();
            }
        }
    }

    /**
     * Prova a creare una connessione tramite socket
     */
    private void tryConnectionSocket() {
        //Il client tenta di connettersi tramite socket
        Socket socket = null;
        try {
            socket = new Socket(address, PORT_SOCKET);
            //Client connesso
            connected = true;
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            //Connessione tramite Socket non riuscita
        }

        if (connected) {
            try {
                connectionClient = new ConnectionClientSocket(socket);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Prova a creare una connessione tramite RMI
     */
    private void tryConnectionRMI() {
        ServerRMI serverRMI;
        String s = "";

        //Il client tenta di connettersi tramite RMI
        try {
            Registry registry = LocateRegistry.getRegistry(address, PORT_RMI);
            serverRMI = (ServerRMI) registry.lookup(SERVER_NAME);

            s = serverRMI.connect();

            if (StatusMessage.connected.toString().equals(s)) {
                connected = true;

                connectionClient = new ConnectionClientRMI(nickname);

                String status = "";
                do {
                    //invia al server lo skeleton del client
                    status = serverRMI.addClient((ClientRMI) connectionClient);
                } while (!StatusMessage.playerAdded.toString().equals(status));

            }

        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
