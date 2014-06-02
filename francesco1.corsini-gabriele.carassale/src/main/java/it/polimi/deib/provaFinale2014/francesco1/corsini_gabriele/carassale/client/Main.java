package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ConnectionVariable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ServerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUIDinamic;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUISwingStatic;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.LineCommand;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * È il main del Client, serve a scegliere inizialmente come si desidera
 * giocare: il tipo di connessione e il tipo di interfaccia
 *
 * @author Carassale Gabriele
 */
public class Main {

    private static final String ADDRESS = "localhost";

    private static final String SOCKET = "socket";
    private static final String RMI = "rmi";

    ConnectionClient connectionClient;

    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    private boolean connected;
    private String nickname;

    /**
     * Inizializza il necessario per interagire con l'utente, chiede il tipo di
     * connessione e il tipo di interazione.
     *
     * @throws java.io.IOException
     */
    public Main() throws IOException {
        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);

        connected = false;
        connectionClient = null;
        nickname = "";

        print("Messaggio di Benvenuto");

        int typeConnection = 0;
        do {
            print("Scegliere il modello di connessione Client-Server:");
            print("1 - Connessione tramite Socket");
            print("2 - Connessione tramite RMI");
            typeConnection = readInt();
        } while (typeConnection < 1 || typeConnection > 2);

        if (typeConnection == 1) {
            connectToServer(SOCKET);
        } else {
            connectToServer(RMI);
        }

        int typeView = 0;
        do {
            print("Scegliere il modo di interegire:");
            print("1 - Linea di comando");
            print("2 - Interfaccia grafica statica");
            print("3 - Interfaccia grafica dinamica");
            typeView = readInt();
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
            default:
                print("Scelta non corretta.");
                break;
        }

        connectionClient.waitLine();
    }

    private void insertNickName() {
        do {
            print("Inserisci il tuo nickname");
            nickname = read();
        } while ("".equals(nickname));
    }

    public void sendNicknameSocket(Socket socket) throws IOException {
        //Invio il nickname
        PrintWriter outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        BufferedReader inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s;
        do {
            insertNickName();
            outSocket.println(nickname);
            outSocket.flush();

            s = inSocket.readLine();

            if (StatusMessage.NOT_CORRECT_NICKNAME.toString().equals(s)) {
                s = inSocket.readLine();
                print(s);
            }
        } while (!StatusMessage.CORRECT_NICKNAME.toString().equals(s));
    }

    public void sendNicknameRMI(ServerRMI serverRMI) throws RemoteException {
        String s;
        do {
            insertNickName();
            s = serverRMI.checkNickname(nickname);
            if (StatusMessage.NOT_CORRECT_NICKNAME.toString().equals(s)) {
                print("Questo nickname è già utilizzato da un altro player, controllare il tipo di connessione");
            }
        } while (!StatusMessage.CORRECT_NICKNAME.toString().equals(s));
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
            socket = new Socket(ADDRESS, ConnectionVariable.PORT_SOCKET);

            sendNicknameSocket(socket);

            //Client connesso
            connected = true;
        } catch (IOException ex) {
            //Connessione tramite Socket non riuscita
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
        String s;

        //Il client tenta di connettersi tramite RMI
        try {
            Registry registry = LocateRegistry.getRegistry(ADDRESS, ConnectionVariable.PORT_RMI);
            serverRMI = (ServerRMI) registry.lookup(ConnectionVariable.SERVER_NAME);

            s = serverRMI.connect();

            if (StatusMessage.CONNECTED.toString().equals(s)) {
                connected = true;

                sendNicknameRMI(serverRMI);

                connectionClient = new ConnectionClientRMI(nickname);

                String status;
                do {
                    //invia al server lo skeleton del client
                    status = serverRMI.addClient((ClientRMI) connectionClient, nickname);
                } while (!StatusMessage.PLAYER_ADDED.toString().equals(status));

            }

        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Implementa il metodo superiore e stampa a schermo una stringa
     *
     * @param string Stringa da stampare
     */
    private void print(String string) {
        outVideo.println(string);
    }

    /**
     * Legge una riga dal terminale
     *
     * @return Stringa letta
     */
    public String read() {
        String s = "";
        try {
            s = inKeyboard.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    /**
     * Legge una riga dal terminale
     *
     * @return Stringa letta
     */
    private int readInt() {
        String s = read();
        while (!isNumeric(s)) {
            print("Devi inserire un valore numerico!");
            s = read();
        }

        return Integer.parseInt(s);
    }

    /**
     * Controlla se una stringa in realtà può essere convertita a numero
     *
     * @param str Stringa da controllore
     * @return True se è numero
     */
    private boolean isNumeric(String str) {
        try {
            Integer i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, nfe);
            return false;
        }
        return true;
    }

    /**
     * Crea la classe Main, gestisce l'avvio del client
     *
     * @param arg
     */
    public static void main(String[] arg) {
        try {
            new Main();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
