package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

/**
 * Crea un'interfaccia per dichiarare i metodi guida da utilizzare nei tre casi
 * di interfaccia: linea di comando, interfaccia grafica statica, interfaccia
 * grafica dinamica. Vengono chiamati dalle connectionClient sul proprio
 * typeOfInteraction
 *
 * @author Gabriele Carassale
 */
public interface TypeOfInteraction {

    /**
     * Risveglia l'interfaccia e fa fare la mossa
     */
    void clickAction();

    /**
     * Risveglia l'interfaccia e fa settare il nickname
     */
    void setNickname();

    /**
     * Risveglia l'interfaccia e mostra un messaggio di errore
     *
     * @param message Messaggio da visualizzare
     */
    void errorMessage(String message);

    /**
     * Risveglia l'interfaccia e fa posizionare il pastore
     *
     * @param idShepard Pastore da posizionare
     */
    void placeShepard(int idShepard);

    /**
     * Risveglia l'interfaccia e muove l'animale
     *
     * @param idAnimal Animale da spostare, -1 BlackSheep, -2 Wolf
     * @param idTerrain Terreno destinazione
     */
    void refreshMoveAnimal(int idAnimal, int idTerrain);

    /**
     * Risveglia l'interfaccia e aggiunge un animale
     *
     * @param idTerrain Terrno dove aggiungere
     * @param kind Tipo di animale da aggiungere: whiteSheep, ram, lamb, wolf,
     * blackSheep
     */
    void refreshAddAnimal(int idTerrain, String kind);

    /**
     * Risveglia l'interfaccia e uccide un animale
     *
     * @param idAnimal Animale da uccidere
     */
    void refreshKillAnimal(int idAnimal);

    /**
     * Risveglia l'interfaccia e trasforma un animale
     *
     * @param idAnimal Animale da trasformare
     * @param kind Tipo di trasformazione finale: ram, whiteSheep
     */
    void refreshTransformAnimal(int idAnimal, String kind);

    /**
     * Risveglia l'interfaccia e cambia le carte
     *
     * @param typeOfTerrain Tipo carta da cambiare
     * @param isSold True se venduta, False se comprata
     */
    void refreshCard(String typeOfTerrain, boolean isSold);

    /**
     * Risveglia l'interfaccia e cambia i coin
     *
     * @param coins Variazione di coin
     * @param addCoin True se deve aggiungere, False se deve levare
     */
    void refreshCoin(int coins, boolean addCoin);

    /**
     * Risveglia l'interfaccia e aggiunge un pastore
     *
     * @param idShepard Pastore da aggiungere
     * @param road Strada dove posizionare
     */
    void refreshAddShepard(int idShepard, int road);

    /**
     * Risveglia l'interfaccia e muove un pastore
     *
     * @param idShepard Pastore da muoverer
     * @param road Strada destinazione
     */
    void refreshMoveShepard(int idShepard, int road);

    /**
     * Invia un messaggio da far viusalizzare
     *
     * @param message Messaggio da visualizzare
     */
    void messageText(String message);

}
