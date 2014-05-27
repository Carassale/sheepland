package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import java.io.Serializable;

public class ActionImpl implements Action, Serializable {

    private static final long serialVersionUID = 3838927269722999085L;

    private int count;
    private String string;

    public ActionImpl(String string, int count) {
        this.count = count;
        this.string = string;
    }

    @Override
    public String moveShepard(String idShepard, String idRoad) {
        if (true) { //tryToMoveShepard()) {
            return "actionDone";
        } else {
            return "errorAction";
        }
    }

    @Override
    public String moveSheep(String idSheep, String idTerrain) {
        return "";
    }

    @Override
    public String buyCard(String kind) {
        return "";
    }

    @Override
    public String killSheep(String idSheep) {
        return "";
    }

    @Override
    public String joinSheep(String idTerrain) {
        return "";
    }
}
