package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

/**
 * Gestisce lo stato del player all'interno dell'hash map
 *
 * @author Carassale Gabriele
 */
public class StatusPlayer {

    private final String typeConnection;
    private final int idGame;
    private final int idPlayer;
    private boolean onLine;

    /**
     * Crea uno status player e setta i parametri
     *
     * @param typeConnection Tipo di connessione usata
     * @param idGame Id del gioco
     * @param idPlayer Id del giocatore all'interno del gioco
     * @param onLine Stato on line
     */
    public StatusPlayer(String typeConnection, int idGame, int idPlayer, boolean onLine) {
        this.typeConnection = typeConnection;
        this.idGame = idGame;
        this.idPlayer = idPlayer;
        this.onLine = onLine;
    }

    /**
     * Restituisce il tipo di connessione
     *
     * @return String Status Messagge TYPE_RMI, TYPE_SOCKET
     */
    public String getTypeConnection() {
        return typeConnection;
    }

    /**
     * Restituisce l'id della partita nella quale sta giocando il player
     *
     * @return Int di game
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * Restituisce l'id del player all'interno della partita nella quale sta
     * giocando
     *
     * @return Int id player
     */
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * Restituisce lo stato on line
     *
     * @return boolean True se il giocatore è on line
     */
    public boolean isOnLine() {
        return onLine;
    }

    /**
     * Setta lo stato on line del giocatore
     *
     * @param onLine boolean True se il giocatore deve essere settato on line
     */
    public void setOnLineStatus(boolean onLine) {
        this.onLine = onLine;
    }

}
