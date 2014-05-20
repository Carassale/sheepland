/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.controller;

/**
 *
 * @author Francesco Corsini
 */
public class WrongDiceNumberException extends Exception{

    //TODO gestione del ritorno del numero uscito
    public WrongDiceNumberException(int diceNumber){
        
    }

    public WrongDiceNumberException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
