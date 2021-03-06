package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

/**
 * La classe Main è quella che viene avviata per prima, ha solo il compito di
 * far scegliere al gestore del server il tipo di connessione Client-Server che
 * vuole utilizzare.
 *
 * @author Carassale Gabriele
 */
public class MainServer {

    private MainServer() {
        //non fa nulla
    }

    /**
     * È il Main del server
     *
     * @param arg
     */
    public static void main(String[] arg) {
        MapServerPlayer map = new MapServerPlayer();
        new ServerManagerRMI(map);
        new ServerManagerSocket(map);
    }

}
