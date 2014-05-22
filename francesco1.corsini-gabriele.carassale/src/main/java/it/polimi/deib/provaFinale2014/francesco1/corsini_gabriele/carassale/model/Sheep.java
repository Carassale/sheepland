package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

/**
 * Classe Percora che include pecore, agnelli e montoni
 *
 * @author Francesco Corsini
 */
public class Sheep extends Animal {

    // questi attributi bastano per capire se una Sheep è un Lamb o Ram
    private int age;
    private String sex;
    private boolean old;
    private int id;

    //Età (numero di round) per far diventare vecchia la pecora
    private final static int AGETOBECOMEOLD = 4;

    /**
     *
     * @param terrain : dove viene posizionato
     * @param initialization : serve a capire se sto creando pecore per
     * inizializzazione gioco(tipologia randomica) o se nasce da
     * accoppiamento(agnello)
     */
    public Sheep(Terrain terrain, boolean initialization, int num) {
        position = terrain;
        id = num;
        if (initialization == false) {
            age = 0;
            sex = randomSex();
        } else {
            sex = randomSex();
            if (Math.random() < 0.3) {
                age = 0;
                old = false;
            } else {
                age = 4;
                old = true;
            }
        }
        position.addAnimal(this);
    }

    /**
     *
     * @return Stringa con maschi o femmina
     */
    private String randomSex() {
        if (Math.random() < 0.5) {
            return "female";
        } else {
            return "male";
        }
    }

    /**
     * Restituisce l'età della pecora
     *
     * @return int contenente l'età
     */
    public int getAge() {
        return age;
    }

    /**
     * Setta l'età della pecora
     *
     * @param age età da settare
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Restituisce il sesso della pecora
     *
     * @return Sesso in forma di String
     */
    public String getSex() {
        return sex;
    }

    /**
     * Setta il sesso della pecora
     *
     * @param sex sesso da settare
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * Fa crescere di un anno la pecora
     */
    public void growUpOneTurn() {
        age++;
        if (age == AGETOBECOMEOLD) {
            old = true;
        }
    }

    /**
     * Controlla se è una semplce pecora bianca
     *
     * @return True se è pecora bianca
     */
    public boolean isWhiteSheep() {
        return old && "Female".equals(sex);
    }

    /**
     * Controlla se è un agnello
     *
     * @return True se è agnello
     */
    public boolean isLamb() {
        return !old;
    }

    /**
     * Controlla se è un montone
     *
     * @return True se è montone
     */
    public boolean isRam() {
        return old == true && "Male".equals(sex);
    }

    /**
     * Controlla se è vecchio
     *
     * @return True se è vecchio
     */
    public boolean isOld() {
        return old;
    }

    /**
     * Setta se è vecchio o no
     *
     * @param isOld True se è vecchio
     */
    public void setOld(boolean isOld) {
        old = isOld;
    }

    /**
     * Restituisce l'id della pecora
     *
     * @return int id della pecora
     */
    public int getId() {
        return id;
    }

    /**
     * Setta l'id alla pecora
     *
     * @param id valore da settare all'id della pecora
     */
    public void setId(int id) {
        this.id = id;
    }
}
