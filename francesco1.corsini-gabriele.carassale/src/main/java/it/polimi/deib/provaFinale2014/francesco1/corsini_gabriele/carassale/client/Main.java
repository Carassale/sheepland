package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server.ServerManagerRMI;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUIDinamic;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUIStatic;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUISwingStatic;
import java.io.IOException;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final static int PORT_RMI = 3001;
    private final static int PORT_SOCKET = 3002;
    private final static String address = "localhost";

    private boolean connected;

    ConnectionClient connectionClient;

    public static void main(String arg[]) {
        Main main = new Main();
    }

    public Main() {
        connected = false;
        connectionClient = null;

        System.out.println("Messaggio di Benvenuto");
        Scanner keyboard = new Scanner(System.in);

        int typeConnection = 0;
        do {
            System.out.println("Scegliere il modello di connessione Client-Server:");
            System.out.println("1 - Connessione tramite Socket");
            System.out.println("2 - Connessione tramite RMI");
            typeConnection = keyboard.nextInt();
        } while (typeConnection < 1 || typeConnection > 2);

        if (typeConnection == 1) {
            connectToServer("Socket");
        } else {
            connectToServer("RMI");
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
                connectionClient.setTypeOfInteraction(new LineCommand());
                break;
            case 2:
                connectionClient.setTypeOfInteraction(new GUISwingStatic());
                break;
            case 3:
                connectionClient.setTypeOfInteraction(new GUIDinamic());
                break;
        }

        connectionClient.waitLine();
    }

    private void connectToServer(String typeConnection) {
        connected = false;
        if ("Socket".equals(typeConnection)) {
            while (!connected) {
                tryConnectionSocket();
            }
        } else {
            while (!connected) {
                tryConnectionRMI();
            }
        }
    }

    private void tryConnectionSocket() {
        //Il client tenta di connettersi tramite socket
        Socket socket = null;
        try {
            socket = new Socket(address, PORT_SOCKET);
            //Client connesso
            connected = true;
        } catch (IOException e) {
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

    private void tryConnectionRMI() {
        //Il client tenta di connettersi tramite RMI
/*
        try {
            Registry registry = LocateRegistry.getRegistry(address, PORT_RMI);
            StubRMI stubRMI = (StubRMI) registry.lookup(ServerManagerRMI.SERVER_NAME);

            String result = stubRMI.managerRMI(ac);
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Connessione tramite RMI non riuscita
        if (connected) {
            ConnectionClientRMI ccrmi = new ConnectionClientRMI();
        }*/
        
    }
}
