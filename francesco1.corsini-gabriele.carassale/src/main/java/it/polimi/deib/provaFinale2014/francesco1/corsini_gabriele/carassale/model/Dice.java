package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model;


public class Dice {

    public Dice () {
    }

    // Ritorna un numero da 1 a 6
    public int getRandom () {
        int a = (int)(Math.random()*6)+1;
        return a;
    }

}

