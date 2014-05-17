package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

/**
 * La classe Main è quella che viene avviata per prima, ha solo il compito di
 * far scegliere al gestore del server il tipo di connessione Client-Server che
 * vuole utilizzare.
 *
 * @author Gabriele Carassale
 */
public class Main {

    public Main() {
        int n = 0;
        ServerManager sm;
        do {
            System.out.println("Messaggio di Benvenuto\n");
            System.out.println("Scegliere il modello di connessione Client-Server:\n");
            System.out.println("1 - Connessione tramite Socket\n");
            System.out.println("2 - Connessione tramite RMI\n");
        } while (n < 1 || n > 2);

        if (n == 1) {
            sm = new ServerManagerSocket();
        } else {
            sm = new ServerManagerRMI();
        }
    }

}
