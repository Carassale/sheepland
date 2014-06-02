package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.server;

public class StatusPlayer {

    private String nickname;
    private String typeConnection;
    private int idGame;
    private int idPlayer;
    private boolean onLine;

    public StatusPlayer(String nickname, String typeConnection, int idGame, int idPlayer, boolean onLine) {
        this.nickname = nickname;
        this.typeConnection = typeConnection;
        this.idGame = idGame;
        this.idPlayer = idPlayer;
        this.onLine = onLine;
    }

    public String getTypeConnection() {
        return typeConnection;
    }

    public int getIdGame() {
        return idGame;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLineStatus(boolean onLine) {
        this.onLine = onLine;
    }

}
