package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 * Class representing the various possible states of the GUI
 *
 * @author Francesco Corsini
 */
public enum GUIState {

    MOVESHEPARDSELECTION(0),
    MOVESHEEPFROM(1),
    KILLSHEEP(2),
    JOINSHEEPS(3),
    BUYCARD(4),
    WAITINGFOROTHERPLAYER(5),
    MOVESHEEPTO(6),
    MOVESHEEPSELECTION(7),
    KILLSHEEPSELECTION(8),
    PLACESHEPARD(9),
    MOVESHEPARDTO(10);

    private final int index;

    private GUIState(int index) {
        this.index = index;
    }

    public int getValue() {
        return index;
    }

}
