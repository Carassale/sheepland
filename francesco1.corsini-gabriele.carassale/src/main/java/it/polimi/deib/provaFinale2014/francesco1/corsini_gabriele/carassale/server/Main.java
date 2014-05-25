package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import java.util.Scanner;

/**
 * La classe Main è quella che viene avviata per prima, ha solo il compito di
 * far scegliere al gestore del server il tipo di connessione Client-Server che
 * vuole utilizzare.
 *
 * @author Carassale Gabriele
 */
public class Main {

    /**
     * Rimane in ascolto da tastiera di un numero per scegliere quale tipo di
     * connessione aviare tra Client e Server. Viene creato un ServerManager,
     * interfaccia implementata dalle due classe ServerManagerSocket e
     * ServerManagerRMI
     *
     * @param arg
     */
    /*public static void main(String arg[]) {
     Scanner keyboard = new Scanner(System.in);
     ServerManager serverManager;

     int n = 0;
     do {
     System.out.println("Messaggio di Benvenuto\n");
     System.out.println("Scegliere il modello di connessione Client-Server:\n");
     System.out.println("1 - Connessione tramite Socket\n");
     System.out.println("2 - Connessione tramite RMI\n");
     n = keyboard.nextInt();
     } while (n < 1 || n > 2);

     if (n == 1) {
     serverManager = new ServerManagerSocket();
     } else {
     serverManager = new ServerManagerRMI();
     }
     }*/
    public static void main(String arg[]) {
        ServerManagerSocket serverManagerSocket = new ServerManagerSocket();
        //ServerManagerRMI serverManagerRMI = new ServerManagerRMI();
    }

}
