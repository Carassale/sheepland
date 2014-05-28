package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

/**
 * È un'interfaccia, serve a definire i metodi guida da utilizzare nei due casi
 * Socket o RMI
 *
 * @author Carassale Gabriele
 */
public interface ServerManager extends Runnable {

    /**
     * È il numero massimo di giocatori per partita, una volta raggiunto tale
     * numero di connessioni viene avviata una nuova partita tramite
     * ConnectionManagerSocket
     */
    static final int PLAYER4GAME = 4;

    /**
     * È il tempo di attesa massimo che il thread SocketWaitingTimer aspetta
     * prima di avviare forzatamente una partita 2min = 2*60*1000 = 240.000
     * millisec
     */
    static final int TIMEOUT = 10000;

}
