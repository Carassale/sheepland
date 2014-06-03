package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 * Class representing the various possible states of the GUI
 * @author Francesco Corsini
 */
public enum GUIDinamicState {

    SUBMENUOPEN(0), WAITINGFORPLAYER(1), PLACESHEPARD(2),WAITINGFORSERVER(3), MOVESHEPARDTO(4),MOVESHEEP(5),INITIALIZATION(6);

    private int index;

    private GUIDinamicState(int index) {
        this.index = index;
    }

}
