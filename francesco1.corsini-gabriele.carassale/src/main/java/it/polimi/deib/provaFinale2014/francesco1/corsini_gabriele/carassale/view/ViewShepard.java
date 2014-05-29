package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 *
 * @author Francesco Corsini
 */
public class ViewShepard {

    private int id;
    private int position;
    private boolean isOwned;
    private boolean isFirst;

    public ViewShepard(int id, int road) {
        this.id = id;
        this.position = road;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostition() {
        return position;
    }

    public void setPostition(int postition) {
        this.position = postition;
    }

    public boolean getIsOwned() {
        return isOwned;
    }

    public void setIsOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }

    public boolean isIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

}
