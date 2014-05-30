package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import java.util.HashMap;

public class MapServerPlayer {

    private HashMap<String, StatusPlayer> map;

    public MapServerPlayer() {
        map = new HashMap<String, StatusPlayer>();
    }

    public void addPlayer(String nickname, String typeConnection, int idGame, int idPlayer) {
        StatusPlayer statusPlayer = new StatusPlayer(nickname, typeConnection, idGame, idPlayer, true);
        map.put(nickname, statusPlayer);
    }

    public void removePlayer(String nickname) {
        map.remove(nickname);
    }

    public String getTypeConnection(String nickname) {
        return map.get(nickname).getTypeConnection();
    }

    public int getIdGame(String nickname) {
        return map.get(nickname).getIdGame();
    }

    public int getIdPlayer(String nickname) {
        return map.get(nickname).getIdPlayer();
    }

    public boolean isOnLine(String nickname) {
        return map.get(nickname).isOnLine();
    }

    public void setOnLine(String nickname, boolean onLine) {
        map.get(nickname).setOnLineStatus(onLine);
    }

    public boolean existPlayer(String nickname) {
        if (nickname != null) {
            return map.get(nickname) != null;
        }
        return false;
    }

    public boolean isTypeConnectionSocket(String nickname) {
        return map.get(nickname).getTypeConnection().equals(StatusMessage.TYPE_SOCKET.toString());
    }
}
