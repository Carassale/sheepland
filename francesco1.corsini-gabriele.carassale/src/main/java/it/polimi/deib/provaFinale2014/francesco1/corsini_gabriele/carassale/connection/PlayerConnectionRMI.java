package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;
import java.io.Serializable;

/**
 * Questa classe serve a mantenere la connessione con il singolo client nel caso
 * sia stato scelto il metodo RMI
 *
 * @author Carassale Gabriele
 */
public class PlayerConnectionRMI extends PlayerConnection implements Serializable {

    private ClientRMI clientRMI;
    private int idPlayer;

    /**
     * Crea e inizializza il Player, setta lo stub del clinet per la
     * comunicazione da server a client
     *
     * @param clientRMI
     * @param idPlayer
     */
    public PlayerConnectionRMI(ClientRMI clientRMI, int idPlayer) {
        this.clientRMI = clientRMI;
        this.idPlayer = idPlayer;
    }

    /**
     * Restituisce lo stub del Client
     *
     * @return ClientRMI
     */
    public ClientRMI getClientRMI() {
        return clientRMI;
    }

    /**
     * Setta lo stub del Client
     *
     * @param clientRMI Stub da settare
     */
    public void setClientRMI(ClientRMI clientRMI) {
        this.clientRMI = clientRMI;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

}
