package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.TypeOfInteraction;

/**
 * Crea un'interfaccia per dichiarare i metodi guida da utilizzare nei due casi
 * di connessione, RMI o Socket. Vengono chiamati dalle type of interaction sui
 * propri connectionClient
 *
 * @author Carassale Gabriele
 */
public interface ConnectionClient {

    /**
     * Definisce il method per settare il tipo di interfaccia
     *
     * @param typeOfInteraction LineCommand, GUISwingStatic, GUIDinamic
     */
    void setTypeOfInteraction(TypeOfInteraction typeOfInteraction);

    /**
     * Definisce il method per posizionare il pastore
     *
     * @param idRoad Strada dove posizionare
     */
    void placeShepard(int idRoad);

    /**
     * Definisce il method per aspettare un comando dal server
     */
    void waitLine();

    /**
     * Definisce il method per muovere il pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada dove posizionare
     */
    void moveShepard(int idShepard, int idRoad);

    /**
     * Definisce il method per muovere la pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno dove posizionare
     */
    void moveSheep(int idSheep, int idTerrain);

    /**
     * Definisce il method per comprare una carta
     *
     * @param typeOfTerrain Tipo di carta da comprare
     */
    void buyCard(String typeOfTerrain);

    /**
     * Definisce il method per uccidere una pecora
     *
     * @param idSheep Pecora da uccidere
     */
    void killSheep(int idSheep);

    /**
     * Definisce il method per accoppiare una pecora e un montone
     *
     * @param idTerrain Terreno dove avviene l'accoppiamento
     */
    void joinSheep(int idTerrain);

}
