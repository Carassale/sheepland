package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

/**
 * Questa classe serve a mantenere la connessione con il singolo client nel caso
 * sia stato scelto il metodo Socket
 *
 * @author Carassale Gabriele
 */
public class PlayerConnectionRMI extends PlayerConnection {

    private int idPlayer;

    public PlayerConnectionRMI(int idPlayer) {
        this.idPlayer = idPlayer;
    }

}
