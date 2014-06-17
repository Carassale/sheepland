package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

/**
 * Definisce le costanti da utilizzare nei due server manager
 *
 * @author Gabriele Carassale
 */
public final class ServerVariable {

    /**
     * È il numero massimo di giocatori per partita, una volta raggiunto tale
     * numero di connessioni viene avviata una nuova partita tramite
     * ConnectionManagerSocket
     */
    public static final int PLAYER4GAME = 4;

    /**
     * È il tempo di attesa massimo che il thread SocketWaitingTimer aspetta
     * prima di avviare forzatamente una partita 30*1000 = 30 secondi
     */
    public static final int TIMEOUT = 30000;

    /**
     * Nasconde il costruttor di base
     */
    private ServerVariable() {
        //non deve fare nulla
    }

}
