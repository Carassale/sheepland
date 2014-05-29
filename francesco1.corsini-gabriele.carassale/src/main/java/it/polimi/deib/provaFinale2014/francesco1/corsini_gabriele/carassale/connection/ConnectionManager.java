package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;

/**
 * È unclasse abstract, serve a definire i metodi guida da utilizzare nei due
 * casi Socket o RMI
 *
 * @author Carassale Gabriele
 */
public interface ConnectionManager {

    /**
     * Fa eseguire i comandi necessari a gestire la connection, creato qui in
     * modo da non dover fare l'override acnhe del run
     */
    public void startThread();

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract, dice
     * al connectionManager di comunicare al Client che può eseguire le azioni e
     * gestisce quindi le chiamate Client-Server
     */
    public void startAction();

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract, dice
     * al client di posizionareve posizionare due pastori
     *
     * @param idShepard
     * @return Road nella quale è stato posizionato il pastore
     */
    public Road getPlacedShepard(int idShepard);

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     */
    public void nextPlayerConnections();

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param idShepard
     * @param idRoad
     */
    public void refreshAddShepard(int idShepard, int idRoad);

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param idTerrain
     * @param kind
     */
    public void refreshAddAnimal(int idTerrain, String kind);

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param idAnimal
     * @param idTerrain
     */
    public void refreshMoveAnimal(int idAnimal, int idTerrain);

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param idAnimal
     */
    public void refreshKillAnimal(int idAnimal);

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param idAnimal
     * @param kindFinal
     */
    public void refreshTransformAnimal(int idAnimal, String kindFinal);

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param coins
     * @param addCoin
     */
    public void refreshCoin(int coins, boolean addCoin);

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param kind
     * @param isSold
     */
    public void refreshCard(String kind, boolean isSold);

}
