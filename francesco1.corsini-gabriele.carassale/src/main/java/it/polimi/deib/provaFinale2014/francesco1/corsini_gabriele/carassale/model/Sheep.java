package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;


/**
 * Classe Percora che include pecore, agnelli e montoni
 * @author Francesco Corsini
 */
public class Sheep extends Animal {

    // questi attributi bastano per capire se una Sheep è un Lamb o Ram
    private int age;
    private String sex;
    private boolean old;


    /**
     * 
     * @param terrain : dove viene posizionato
     * @param initialization : serve a capire se sto creando pecore per inizializzazione gioco(tipologia randomica) o se nasce da accoppiamento(agnello)
     */
    public Sheep (Terrain terrain, boolean initialization) {
        position = terrain;
        if(initialization == false){
            age = 0;
            sex = randomSex();
        }
        else{
            sex = randomSex();
            if(Math.random() < 0.3){
                age = 0;
                old = false;
            }    
            else{
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
    private String randomSex(){
        if(Math.random() < 0.5)
            return "female";
        else       
            return "male";
    }
    
    public int getAge () {
        return age;
    }


    public void setAge (int val) {
        this.age = val;
    }


    public String getSex () {
        return sex;
    }


    public void setSex (String val) {
        this.sex = val;
    }


    public void growUpOneTurn () {
        age++;
        if(age == 4)
            old= true;
    }


    public boolean isWhiteSheep () {
        if(old == true && "Female".equals(sex))
            return true;
        else
            return false;
    }


    public boolean isLamb () {
        if(old == false)
            return true;
        else
            return false;
    }


    public boolean isRam () {
        if(old == true && "Male".equals(sex))
            return true;
        else
            return false;
    }

    public boolean  isOld(  ){
        return old;
    }
    
    public void  setOld( boolean val ){
        old = val;
    }
}

