package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 * Classe rappresentate un animale nella View(pecora, agnello,montone, lupo,
 * black)
 *
 * @author Francesco Corsini
 */
public class ViewAnimal {

    private int id;
    private int position;
    private String type;

    /**
     * costruttore per GUI Statica
     *
     * @param id id animale
     * @param position posizione dove inizializzarlo
     */
    public ViewAnimal(int id, int position) {
        this.id = id;
        this.position = position;
    }

    /**
     * Costruttore per GUI Dynamic
     *
     * @param id id animale
     * @param position posizione dove inizializzarlo
     * @param type tipologia di animale
     */
    public ViewAnimal(int id, int position, String type) {
        this.id = id;
        this.position = position;
        this.type = type;
    }

    /**
     * getter
     *
     * @return id animale
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id id animale
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     *
     * @return posizione
     */
    public int getPosition() {
        return position;
    }

    /**
     * Setter
     *
     * @param position id posizione
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Getter
     *
     * @return tipologia animale
     */
    public String getType() {
        return type;
    }

    /**
     * Setter
     *
     * @param type tipologia animale
     */
    public void setType(String type) {
        this.type = type;
    }

}
