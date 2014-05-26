/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 *
 * @author Francesco Corsini
 */
public class ViewAnimal {
    
    private int id;
    private int position;
    private String type;
    
    public ViewAnimal(int id, int position){
        this.id = id;
        this.position = position;
    }
    
    public ViewAnimal(int id, int position, String type){
        this.id = id;
        this.position = position;
        this.type = type;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
    
    
}
