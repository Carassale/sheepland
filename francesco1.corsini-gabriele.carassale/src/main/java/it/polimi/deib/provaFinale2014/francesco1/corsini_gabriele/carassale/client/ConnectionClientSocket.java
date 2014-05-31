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
                if (TypeAction.WAKE_UP.toString().equals(s)) {
                    typeOfInteraction.clickAction();
                } else if (TypeAction.ERROR_COIN.toString().equals(s)) {
                    errorCoin();
                } else if (TypeAction.ERROR_MOVE.toString().equals(s)) {
                    errorMove();
                } else if (TypeAction.ERROR_DICE.toString().equals(s)) {
                    errorDice();
                } else if (TypeAction.ERROR_CARD.toString().equals(s)) {
                    errorCard();
                } else if (TypeAction.ERROR_MESSAGE.toString().equals(s)) {
                    errorMessage();
                } else if (TypeAction.MESSAGE_TEXT.toString().equals(s)) {
                    messageText();
                } else if (TypeAction.PLACE_SHEPARD.toString().equals(s)) {
                    typeOfInteraction.placeShepard(Integer.valueOf(inSocket.readLine()));
                } else if (TypeAction.REFRESH_MOVE_ANIMAL.toString().equals(s)) {
                    refreshMoveAnimal();
                } else if (TypeAction.REFRESH_ADD_ANIMAL.toString().equals(s)) {
                    refreshAddAnimal();
                } else if (TypeAction.REFRESH_KILL_ANIMAL.toString().equals(s)) {
                    refreshKillAnimal();
                } else if (TypeAction.REFRESH_TRANSFORM_ANIMAL.toString().equals(s)) {
                    refreshTransformAnimal();
                } else if (TypeAction.REFRESH_ADD_SHEPARD.toString().equals(s)) {
                    refreshAddShepard();
                } else if (TypeAction.REFRESH_MOVE_SHEPARD.toString().equals(s)) {
                    refreshMoveShepard();
                } else if (TypeAction.REFRESH_CARD.toString().equals(s)) {
                    refreshCard();
                } else if (TypeAction.REFRESH_COIN.toString().equals(s)) {
                    refreshCoin();
                }
            } catch (IOException ex) {
                Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Stampa un errore, oggetto: Coin
     */
    public void errorCoin() {
        typeOfInteraction.errorMessage(Message.IMPOSSIBLE_NO_MONEY.toString());
    }

    /**
     * Stampa un errore, oggetto: Move
     */
    public void errorMove() {
        typeOfInteraction.errorMessage(Message.IMPOSSIBLE_MOVE.toString());
    }

    /**
     * Stampa un errore, oggetto: Dice
     */
    public void errorDice() {
        typeOfInteraction.errorMessage(Message.IMPOSSIBLE_DICE.toString());
    }

    /**
     * Stampa un errore, oggetto: Card
     */
    public void errorCard() {
        typeOfInteraction.errorMessage(Message.IMPOSSIBLE_CARD.toString());
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
            int idAnimal = Integer.valueOf(inSocket.readLine());
            int idTerrain = Integer.valueOf(inSocket.readLine());

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
            int idAnimal = Integer.valueOf(inSocket.readLine());
            int idTerrain = Integer.valueOf(inSocket.readLine());
            String kind = inSocket.readLine();

            typeOfInteraction.refreshAddAnimal(idAnimal, idTerrain, kind);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction l'animale cancellato
     */
    private void refreshKillAnimal() {
        try {
            int idAnimal = Integer.valueOf(inSocket.readLine());
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
            int idAnimal = Integer.valueOf(inSocket.readLine());
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
            int idShepard = Integer.valueOf(inSocket.readLine());
            int idRoad = Integer.valueOf(inSocket.readLine());
            int intIsMine = Integer.valueOf(inSocket.readLine());

            boolean isMine = intIsMine == 0;

            typeOfInteraction.refreshAddShepard(idShepard, idRoad, isMine);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia al typeOfInteraction il movimento del pastore
     */
    private void refreshMoveShepard() {
        try {
            int idShepard = Integer.valueOf(inSocket.readLine());
            int idRoad = Integer.valueOf(inSocket.readLine());

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

            boolean isSold;
            // isSold TRUE -> 0
            // isSold FALSE -> 1
            isSold = Integer.valueOf(inSocket.readLine()) == 0;

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
            int coins = Integer.valueOf(inSocket.readLine());

            boolean addCoin;
            // addCoin TRUE -> 0
            // addCoin FALSE -> 1
            addCoin = Integer.valueOf(inSocket.readLine()) == 0;

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
        outSocket.println(TypeAction.MOVE_SHEPARD.toString());
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
        outSocket.println(TypeAction.MOVE_SHEEP.toString());
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
        outSocket.println(TypeAction.BUY_CARD.toString());
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
        outSocket.println(TypeAction.KILL_SHEEP.toString());
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
        outSocket.println(TypeAction.JOIN_SHEEP.toString());
        outSocket.flush();

        outSocket.println(idTerrain);
        outSocket.flush();
    }

    /**
     * Invia un messaggio al client
     */
    private void messageText() {
        try {
            String s = inSocket.readLine();
            typeOfInteraction.messageText(s);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Invia un messaggio di errore al client
     */
    private void errorMessage() {
        try {
            String s = inSocket.readLine();
            typeOfInteraction.messageText(s);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
