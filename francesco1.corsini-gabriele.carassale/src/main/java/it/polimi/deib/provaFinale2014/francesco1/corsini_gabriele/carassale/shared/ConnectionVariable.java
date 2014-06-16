package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

/**
 * Definisce le costanti da utilizzare nella connessione tra Client e Server
 *
 * @author Carassale Gabriele
 */
public final class ConnectionVariable {

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

    /**
     * È il tempo massimo di attesa del client disconnesso. 1.000*30 = -> 30
     * secondi
     */
    public static final int TIME_MAX = 120000;

    /**
     * Nasconde il costruttor di base
     */
    private ConnectionVariable() {
        //non deve fare nulla
    }
}
