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
public class ViewShepard {
    
    private int id;
    private int position;
    
    public ViewShepard(int id, int road){
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

    
}
