package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 * Classe rappresentante uno Shepherd nella View
 *
 * @author Francesco Corsini
 */
public class ViewShepherd {

    private int id;
    private int position;
    private boolean isOwned;
    private boolean isFirst;

    /**
     * costruttore
     *
     * @param id id shepherd
     * @param road id road dove posizionarlo
     */
    public ViewShepherd(int id, int road) {
        this.id = id;
        this.position = road;
    }

    /**
     * Getter
     *
     * @return id shepherd
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id id shepherd
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return id road
     */
    public int getPostition() {
        return position;
    }

    /**
     * Setter
     *
     * @param postition id road
     */
    public void setPostition(int postition) {
        this.position = postition;
    }

    /**
     * Getter
     *
     * @return true se posseduto
     */
    public boolean getIsOwned() {
        return isOwned;
    }

    /**
     * Setter
     *
     * @param isOwned true se posseduto
     */
    public void setIsOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }

    /**
     * Getter
     *
     * @return true se è primo
     */
    public boolean isIsFirst() {
        return isFirst;
    }

    /**
     * Setter
     *
     * @param isFirst true se è primo
     */
    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

}
