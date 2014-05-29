package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

/**
 * La classe Main è quella che viene avviata per prima, ha solo il compito di
 * far scegliere al gestore del server il tipo di connessione Client-Server che
 * vuole utilizzare.
 *
 * @author Carassale Gabriele
 */
public class Main {

    /**
     * È il Main del server
     *
     * @param arg
     */
    public static void main(String arg[]) {
        ServerManagerRMI serverManagerRMI = new ServerManagerRMI();
        ServerManagerSocket serverManagerSocket = new ServerManagerSocket();
        
    }

}
