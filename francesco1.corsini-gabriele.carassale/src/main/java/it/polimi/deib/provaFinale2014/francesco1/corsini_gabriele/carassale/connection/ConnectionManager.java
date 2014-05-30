package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;

/**
 * È un iterfaccia, serve a definire i metodi guida da utilizzare nei due casi
 * Socket o RMI
 *
 * @author Carassale Gabriele
 */
public interface ConnectionManager {

    /**
     * Fa eseguire i comandi necessari a gestire la connection, creato qui in
     * modo da non dover fare l'override acnhe del run
     */
    void startThread();

    /**
     * Inserito qui per creare un metodo guida, dice al connectionManager di
     * comunicare al Client che può eseguire le azioni e gestisce quindi le
     * chiamate Client-Server
     */
    void startAction();

    /**
     * Inserito qui per creare un metodo guida, dice al client di posizionareve
     * posizionare due pastori
     *
     * @param idShepard
     * @return Road nella quale è stato posizionato il pastore
     */
    Road getPlacedShepard(int idShepard);

    /**
     * Inserito qui per creare un metodo guida
     */
    void nextPlayerConnections();

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param idShepard
     * @param idRoad
     */
    void refreshAddShepard(int idShepard, int idRoad);

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param idTerrain
     * @param kind
     */
    void refreshAddAnimal(int idTerrain, String kind);

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param idAnimal
     * @param idTerrain
     */
    void refreshMoveAnimal(int idAnimal, int idTerrain);

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param idAnimal
     */
    void refreshKillAnimal(int idAnimal);

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param idAnimal
     * @param kindFinal
     */
    void refreshTransformAnimal(int idAnimal, String kindFinal);

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param coins
     * @param addCoin
     */
    void refreshCoin(int coins, boolean addCoin);

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param kind
     * @param isSold
     */
    void refreshCard(String kind, boolean isSold);

    /**
     * Inserito qui per creare un metodo guida
     *
     * @param idPlayer
     */
    void refreshAllToPlayer(int idPlayer);
}
