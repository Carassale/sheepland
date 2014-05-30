package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

public final class Connection_variable {

    /**
     * È il numero massimo di giocatori per partita, una volta raggiunto tale
     * numero di connessioni viene avviata una nuova partita tramite
     * ConnectionManagerSocket
     */
    public static final int PLAYER4GAME = 4;

    /**
     * È il tempo di attesa massimo che il thread SocketWaitingTimer aspetta
     * prima di avviare forzatamente una partita 2min = 2*60*1000 = 240.000
     * millisec
     */
    public static final int TIMEOUT = 10000;

    /**
     * È la porta sulla quale avvengono le comunicazioni tra client e server in
     * connessione SOCKET
     */
    public static final int PORT_SOCKET = 3002;

    /**
     * È la porta sulla quale avvengono le comunicazioni tra client e server in
     * connessione RMI
     */
    public static final int PORT_RMI = 3001;

    /**
     * È il nome del ServerManagerRMI, usato per le connessioni
     */
    public static final String SERVER_NAME = "ServerManagerRMI";
}
