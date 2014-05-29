package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.Message;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAction;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.TypeOfInteraction;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Questa classe crea la connessione diretta con il GameController tramite la
 * ConnectionManager nel caso sia stato scelto il metodo Socket
 *
 * @author Carassale Gabriele
 */
public class ConnectionClientSocket implements ConnectionClient {

    private BufferedReader inSocket;
    private PrintWriter outSocket;

    private TypeOfInteraction typeOfInteraction;

    /**
     * Imposta il socket passato come parametro e lo rende pubblico alla classe,
     * inizializza le due printWriter e i due bufferReader, associa una
     * TableView, fa partire il metodo waitLine
     *
     * @param socket È il socket associato alla connessione con il Server, li
     * viene passato direttamente dal ConnectionClient
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ConnectionClientSocket(Socket socket) throws IOException, ClassNotFoundException {
        this.typeOfInteraction = null;

        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    /**
     * Imposta il tipo di interfaccia che desidera utilizzare il client
     *
     * @param typeOfInteraction interfaccia da utilizzare
     */
    public void setTypeOfInteraction(TypeOfInteraction typeOfInteraction) {
        this.typeOfInteraction = typeOfInteraction;
    }

    /**
     * Resta in attesa di un comando da parte del Server
     */
    public void waitLine() {
        while (true) {
            try {
                String s = inSocket.readLine();
                if (TypeAction.wakeUp.toString().equals(s)) {
                    typeOfInteraction.clickAction();
                } else if (TypeAction.setNikcnam.toString().equals(s)) {
                    typeOfInteraction.setNickname();
                } else if (TypeAction.errorCoin.toString().equals(s)) {
                    errorCoin();
                } else if (TypeAction.errorMove.toString().equals(s)) {
                    errorMove();
                } else if (TypeAction.errorDice.toString().equals(s)) {
                    errorDice();
                } else if (TypeAction.placeShepard.toString().equals(s)) {
                    Integer idShepard = new Integer(inSocket.readLine());
                    typeOfInteraction.placeShepard(idShepard);
                } else if (TypeAction.refreshMoveAnimal.toString().equals(s)) {
                    refreshMoveAnimal();
                } else if (TypeAction.refreshAddAnimal.toString().equals(s)) {
                    refreshAddAnimal();
                } else if (TypeAction.refreshKillAnimal.toString().equals(s)) {
                    refreshKillAnimal();
                } else if (TypeAction.refreshTransformAnimal.toString().equals(s)) {
                    refreshTransformAnimal();
                } else if (TypeAction.refreshAddShepard.toString().equals(s)) {
                    refreshAddShepard();
                } else if (TypeAction.refreshMoveShepard.toString().equals(s)) {
                    refreshMoveShepard();
                } else if (TypeAction.refreshCard.toString().equals(s)) {
                    refreshCard();
                } else if (TypeAction.refreshCoin.toString().equals(s)) {
                    refreshCoin();
                } else if (TypeAction.messageText.toString().equals(s)) {
                    messageText();
                }
            } catch (IOException ex) {
                Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Imposta il proprio nickname
     *
     * @param nickname Stringa da settare
     */
    public void setNickname(String nickname) {
        outSocket.println(nickname);
        outSocket.flush();
    }

    /**
     * Stampa un errore, oggetto: Coin
     */
    public void errorCoin() {
        typeOfInteraction.errorMessage(Message.impossibleNoMoney.toString());
    }

    /**
     * Stampa un errore, oggetto: Move
     */
    public void errorMove() {
        typeOfInteraction.errorMessage(Message.impossibleMove.toString());
    }

    /**
     * Stampa un errore, oggetto: Dice
     */
    public void errorDice() {
        typeOfInteraction.errorMessage(Message.impossibleDice.toString());
    }

    /**
     * Piazza un pastore
     *
     * @param idRoad Strada dove posizionare
     */
    public void placeShepard(int idRoad) {
        outSocket.println(idRoad);
        outSocket.flush();
    }

    /**
     * Invia al typeOfInteraction il movimento dell'animale
     */
    private void refreshMoveAnimal() {
        try {
            Integer idAnimal = new Integer(inSocket.readLine());
            Integer idTerrain = new Integer(inSocket.readLine());
            typeOfInteraction.refreshMoveAnimal(idAnimal, idTerrain);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction l'animale aggiunto
     */
    private void refreshAddAnimal() {
        try {
            Integer idAnimal = new Integer(inSocket.readLine());
            String kind = inSocket.readLine();
            typeOfInteraction.refreshAddAnimal(idAnimal, kind);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction l'animale cancellato
     */
    private void refreshKillAnimal() {
        try {
            Integer idAnimal = new Integer(inSocket.readLine());
            typeOfInteraction.refreshKillAnimal(idAnimal);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction l'animale trasformato
     */
    private void refreshTransformAnimal() {
        try {
            Integer idAnimal = new Integer(inSocket.readLine());
            String kind = inSocket.readLine();
            typeOfInteraction.refreshTransformAnimal(idAnimal, kind);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction il pastore aggiunto
     */
    private void refreshAddShepard() {
        try {
            Integer idShepard = new Integer(inSocket.readLine());
            Integer idRoad = new Integer(inSocket.readLine());
            typeOfInteraction.refreshAddShepard(idShepard, idRoad);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction il movimento del pastore
     */
    private void refreshMoveShepard() {
        try {
            Integer idShepard = new Integer(inSocket.readLine());
            Integer idRoad = new Integer(inSocket.readLine());
            typeOfInteraction.refreshMoveShepard(idShepard, idRoad);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction le carte cambiate
     */
    private void refreshCard() {
        try {
            String kind = inSocket.readLine();
            Integer i = new Integer(inSocket.readLine());
            boolean isSold;
            // isSold TRUE -> 0
            // isSold FALSE -> 1
            //boolean isSold = i == 0;
            if (i == 0) {
                isSold = true;
            } else {
                isSold = false;
            }
            typeOfInteraction.refreshCard(kind, isSold);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction i coin cambiati
     */
    private void refreshCoin() {
        try {
            Integer coins = new Integer(inSocket.readLine());
            Integer i = new Integer(inSocket.readLine());
            boolean addCoin;
            // addCoin TRUE -> 0
            // addCoin FALSE -> 1
            //boolean addCoin = i == 0;
            if (i == 0) {
                addCoin = true;
            } else {
                addCoin = false;
            }
            typeOfInteraction.refreshCoin(coins, addCoin);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Muove il pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     */
    public void moveShepard(int idShepard, int idRoad) {
        outSocket.println(TypeAction.moveShepard.toString());
        outSocket.flush();

        outSocket.println(idShepard);
        outSocket.flush();

        outSocket.println(idRoad);
        outSocket.flush();
    }

    /**
     * Muove la pecora
     *
     * @param idSheep Pecora da muovere
     * @param idTerrain Terreno destinazione
     */
    public void moveSheep(int idSheep, int idTerrain) {
        outSocket.println(TypeAction.moveSheep.toString());
        outSocket.flush();

        outSocket.println(idSheep);
        outSocket.flush();

        outSocket.println(idTerrain);
        outSocket.flush();
    }

    /**
     * Compra una carta
     *
     * @param typeOfTerrain Tipo di carta
     */
    public void buyCard(String typeOfTerrain) {
        outSocket.println(TypeAction.buyCard.toString());
        outSocket.flush();

        outSocket.println(typeOfTerrain);
        outSocket.flush();
    }

    /**
     * Uccide una pecora
     *
     * @param idSheep Pecora da uccidere
     */
    public void killSheep(int idSheep) {
        outSocket.println(TypeAction.killSheep.toString());
        outSocket.flush();

        outSocket.println(idSheep);
        outSocket.flush();
    }

    /**
     * Accoppia una pecora con un montone
     *
     * @param idTerrain Terreno in cui si trovano pecora e montone
     */
    public void joinSheep(int idTerrain) {
        outSocket.println(TypeAction.joinSheep.toString());
        outSocket.flush();

        outSocket.println(idTerrain);
        outSocket.flush();
    }

    /**
     * Invia un messaggio al client
     */
    private void messageText() {
        typeOfInteraction.messageText(inSocket.read());
    }
}
