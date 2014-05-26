/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

/**
 *
 * @author Gabriele Carassale
 */
public interface TypeOfInteraction {

    public void clickAction();

    public void setNickname();

    public void errorMessage(String message);
    
    public void placeShepard(int idShepard);

    // id -1 per la BlackSheep, -2 per il Wolf
    public void refreshMoveAnimal(int idAnimal, int idTerrain);

    //kind: whiteSheep, ram, lamb, wolf, blackSheep
    public void refreshAddAnimal(int idTerrain, String kind);

    public void refreshKillAnimal(int idAnimal);

    public void refreshTransformAnimal(int idAnimal, String kind);

    public void refreshCard(String typeOfTerrain, boolean isSold);

    //true aggiunge, false toglie
    public void refreshCoin(int coins, boolean addCoin);

    public void refreshAddShepard(int idShepard, int road);

    public void refreshMoveShepard(int idShepard, int road);

}
