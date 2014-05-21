package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection.ConnectionManager;
import java.io.IOException;
import java.util.ArrayList;

/**
 * È un'interfaccia, serve a definire i metodi guida da utilizzare nei due casi
 * Socket o RMI
 *
 * @author Carassale Gabriele
 */
public interface ServerManager {

    /**
     * È l'arrayList contente i le connectionManager, interfaccia implementata
     * dalle classi ConnectionManagerSocket e ConnectionManagerRMI,
     * rappresentano le partite avviate.
     */
    public static final ArrayList<ConnectionManager> games = null;

}
