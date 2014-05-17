package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;



public class Sheep extends Animal {

    //da questi due attributi poi possiamo capire se una Sheep è un Lamb o Ram
    private int age;
    private String sex;
    private boolean old;

    //il boolean serve a distinguere se sto creando la pecora per inizializzare il gioco(tipologia randomica)
    //o se nasce da accoppiamento(tipologia Lamb)
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
            else
                old = true;
        }
    }
    
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
        if(old == false && "Female".equals(sex))
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

