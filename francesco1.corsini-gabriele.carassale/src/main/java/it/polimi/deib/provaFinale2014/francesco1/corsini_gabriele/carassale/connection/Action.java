package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

public interface Action {

    public String moveShepard(String idShepard, String idRoad);

    public String moveSheep(String idSheep, String idTerrain);

    public String buyCard(String kind);

    public String killSheep(String idSheep);

    public String joinSheep(String idTerrain);

}
