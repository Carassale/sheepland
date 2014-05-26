package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.Road;

/**
 * È unclasse abstract, serve a definire i metodi guida da utilizzare nei due
 * casi Socket o RMI
 *
 * @author Carassale Gabriele
 */
public abstract class ConnectionManager implements Runnable {

    /**
     * È il costruttore di default, non viene implementato perchè le due
     * tipologie di connessioni hanno molte differenze e preferisco fare gestire
     * tutto a loro
     */
    public ConnectionManager() {
        //implementato nelle varie classi
    }

    /**
     * Questa classe implementa un Runnable, le due classi che la estendono
     * hanno entrambe un attributo Thread creato passando come parametro This e
     * successivamente avviato con la chiamata .start()
     */
    @Override
    public void run() {
        startThread();
    }

    /**
     * Fa eseguire i comandi necessari a gestire la connection, creato qui in
     * modo da non dover fare l'override acnhe del run
     */
    public void startThread() {
        //implementato nelle varie classi
    }

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract, dice
     * al connectionManager di comunicare al Client che può eseguire le azioni e
     * gestisce quindi le chiamate Client-Server
     */
    public void startAction() {
        //implementato nelle varie classi
    }

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract, dice
     * al client di posizionareve posizionare due pastori
     *
     * @return Road nella quale è stato posizionato il pastore
     */
    public Road getPlacedShepard() {
        return null;
    }

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract, dice
     * al client di tirare un dado per far muovere la pecora nera
     */
    public void allertToMoveBlackSheep() {
        //implementato nelle varie classi
    }

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     */
    public void nextPlayerConnections() {
        //implementato nelle varie classi
    }

    /**
     * Inserito qui per creare un metodo guida per questa classe abstract
     *
     * @param idShepard
     * @param idRoad
     */
    public void refreshAddShepard(int idShepard, int idRoad) {
        //implementato nelle varie classi
    }
}
