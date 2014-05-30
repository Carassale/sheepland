package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;

/**
 * Classe Percora che include pecore, agnelli e montoni
 *
 * @author Francesco Corsini
 */
public class Sheep extends Animal {

    //Età (numero di round) per far diventare vecchia la pecora
    private static final int AGETOBECOMEOLD = 4;

    // questi attributi bastano per capire se una Sheep è un Lamb o Ram
    private int age;
    private String sex;
    private boolean old;
    private int id;

    /**
     *
     * @param terrain : dove viene posizionato
     * @param initialization : serve a capire se sto creando pecore per
     * inizializzazione gioco(tipologia randomica) o se nasce da
     * accoppiamento(agnello)
     * @param num
     */
    public Sheep(Terrain terrain, boolean initialization, int num) {
        position = terrain;
        id = num;
        if (!initialization) {
            age = 0;
            sex = randomSex();
        } else {
            sex = randomSex();
            old = Math.random() >= 0.3;
            if (old) {
                age = 0;
            } else {
                age = 4;
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
            return TypeAnimal.FEMALE.toString();
        } else {
            return TypeAnimal.MALE.toString();
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

        old = age == AGETOBECOMEOLD;
    }

    /**
     * Controlla se è una semplce pecora bianca
     *
     * @return True se è pecora bianca
     */
    public boolean isWhiteSheep() {
        return old && TypeAnimal.FEMALE.toString().equals(sex);
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
        return old == true && TypeAnimal.MALE.toString().equals(sex);
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
