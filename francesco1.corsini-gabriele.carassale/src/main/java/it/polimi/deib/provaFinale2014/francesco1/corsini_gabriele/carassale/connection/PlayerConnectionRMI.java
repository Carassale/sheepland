package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.ClientRMI;

/**
 * Questa classe serve a mantenere la connessione con il singolo client nel caso
 * sia stato scelto il metodo Socket
 *
 * @author Carassale Gabriele
 */
public class PlayerConnectionRMI extends PlayerConnection {

    private ClientRMI clientRMI;

    public PlayerConnectionRMI(ClientRMI clientRMI) {
        this.clientRMI = clientRMI;
    }

    public ClientRMI getClientRMI() {
        return clientRMI;
    }

    public void setClientRMI(ClientRMI clientRMI) {
        this.clientRMI = clientRMI;
    }

}
