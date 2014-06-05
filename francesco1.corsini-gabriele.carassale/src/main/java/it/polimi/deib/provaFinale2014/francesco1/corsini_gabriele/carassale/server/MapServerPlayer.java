package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * È la mappa utilizzata per le connessioni dei client, registra il nickname e
 * lo stato del client
 *
 * @author Carassale Gabriele
 */
public class MapServerPlayer {

    private Map<String, StatusPlayer> map;

    /**
     * Crea l'oggetto e inizializza l'hash map
     */
    public MapServerPlayer() {
        map = new HashMap<String, StatusPlayer>();
    }

    /**
     * Aggiunge uno Status player alla map
     *
     * @param nickname Nome del client
     * @param typeConnection Tipo di connessione utilizzata
     * @param idGame Id della partita
     * @param idPlayer Id del giocatore all'interno della partita
     */
    public void addPlayer(String nickname, String typeConnection, int idGame, int idPlayer) {
        StatusPlayer statusPlayer = new StatusPlayer(typeConnection, idGame, idPlayer, true);
        map.put(nickname, statusPlayer);
    }

    /**
     * Rimuove uno status player dalla map
     *
     * @param nickname Nome del client
     */
    public void removePlayer(String nickname) {
        map.remove(nickname);
    }

    /**
     * Restituisce il tipo di connessione utilizzata dal client
     *
     * @param nickname Nome del client
     * @return Tipo di connessione
     */
    public String getTypeConnection(String nickname) {
        return map.get(nickname).getTypeConnection();
    }

    /**
     * Restituisce l'id della partita nella quale sta giocando il player
     *
     * @param nickname Nome del client
     * @return Id del gioco
     */
    public int getIdGame(String nickname) {
        return map.get(nickname).getIdGame();
    }

    /**
     * Restituisce la posizione del player all'interno della partita
     *
     * @param nickname Nome del client
     * @return Id della partita
     */
    public int getIdPlayer(String nickname) {
        return map.get(nickname).getIdPlayer();
    }

    /**
     * Restituisce lo stato on Line del player
     *
     * @param nickname Nome del client
     * @return boolean is On line
     */
    public boolean isOnLine(String nickname) {
        return map.get(nickname).isOnLine();
    }

    /**
     * Imposta lo stato on line del player
     *
     * @param nickname Nome del client
     * @param onLine Stato on line del player
     */
    public void setOnLine(String nickname, boolean onLine) {
        map.get(nickname).setOnLineStatus(onLine);
    }

    /**
     * Controlla se è già presente il player nell'hash map
     *
     * @param nickname Nome del client
     * @return boolean True se esiste
     */
    public boolean existPlayer(String nickname) {
        if (nickname != null) {
            return map.get(nickname) != null;
        }
        return false;
    }

    /**
     * Controlla se il tipo di connessione del client è di tipo socket
     *
     * @param nickname Nome del client
     * @return boolean True se è di tipo socket
     */
    public boolean isTypeConnectionSocket(String nickname) {
        return map.get(nickname).getTypeConnection().equals(StatusMessage.TYPE_SOCKET.toString());
    }
}
