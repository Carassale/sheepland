package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

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

    private Socket socket;
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
        this.socket = socket;
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
                if ("wakeUp".equals(s)) {
                    doAction();
                } else if ("setNikcnam".equals(s)) {
                    setNickname();
                } else if ("refresh".equals(s)) {
                    //TODO
                    //refresh();
                } else if ("errorCoin".equals(s)) {
                    errorCoin();
                } else if ("errorMove".equals(s)) {
                    errorMove();
                } else if ("errorDice".equals(s)) {
                    errorDice();
                } else if ("PlaceShepard".equals(s)) {
                    placeShepard();
                } else if ("messageText".equals(s)) {
                    messageText();
                } else if ("moveBlackSheep".equals(s)) {
                    moveBlackSheep();
                }
            } catch (IOException ex) {
                Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Esegue un azione chiedendola al giocatore
     */
    private void doAction() {
        typeOfInteraction.print("Fai la tua mossa");

        String s = typeOfInteraction.read();
        if ("moveShepard".equals(s)) {
            moveShepard();
        } else if ("moveSheep".equals(s)) {
            moveSheep();
        } else if ("buyCard".equals(s)) {
            buyCard();
        } else if ("killSheep".equals(s)) {
            killSheep();
        } else if ("joinSheep".equals(s)) {
            joinSheep();
        }
    }

    /**
     * Muove il pastore
     */
    public void moveShepard() {
        String s = "";
        outSocket.println("moveShepard");
        outSocket.flush();

        typeOfInteraction.print("Quale pastore vuoi muovere?");
        s = typeOfInteraction.read();
        outSocket.println(s);
        outSocket.flush();

        typeOfInteraction.print("In quale strada?");
        s = typeOfInteraction.read();
        outSocket.println(s);
        outSocket.flush();
    }

    /**
     * Muove la pecora
     */
    public void moveSheep() {
        String s = "";
        outSocket.println("moveSheep");
        outSocket.flush();

        typeOfInteraction.print("Quale pecora vuoi muovere?");
        s = typeOfInteraction.read();
        outSocket.println(s);
        outSocket.flush();

        typeOfInteraction.print("In quale terreno?");
        s = typeOfInteraction.read();
        outSocket.println(s);
        outSocket.flush();
    }

    /**
     * Compra una carta
     */
    public void buyCard() {
        String s = "";
        outSocket.println("buyCard");
        outSocket.flush();

        typeOfInteraction.print("Quale tipo di carta vuoi acquistare?");
        s = typeOfInteraction.read();
        outSocket.println(s);
        outSocket.flush();
    }

    /**
     * Uccide una pecora
     */
    public void killSheep() {
        String s = "";
        outSocket.println("killSheep");
        outSocket.flush();

        typeOfInteraction.print("Quale pecora vuoi ammazzare?");
        s = typeOfInteraction.read();
        outSocket.println(s);
        outSocket.flush();
    }

    /**
     * Accoppia una pecora con un montone
     */
    public void joinSheep() {
        String s = "";
        outSocket.println("joinShepard");
        outSocket.flush();

        typeOfInteraction.print("In quale terreno si trovano gli ovini?");
        s = typeOfInteraction.read();
        outSocket.println(s);
        outSocket.flush();
    }

    /**
     * Imposta il proprio nickname
     */
    public void setNickname() {
        typeOfInteraction.print("Impostare il proprio Nickname\n");
        outSocket.println(typeOfInteraction.read());
        outSocket.flush();
    }

    /**
     * Stampa un errore, oggetto: Coin
     */
    public void errorCoin() {
        typeOfInteraction.print("Impossibile fare la mossa! Non hai abbastanza soldi.");
    }

    /**
     * Stampa un errore, oggetto: Move
     */
    public void errorMove() {
        typeOfInteraction.print("Impossibile fare la mossa! Movimento non valido.");
    }

    /**
     * Stampa un errore, oggetto: Dice
     */
    public void errorDice() {
        typeOfInteraction.print("Impossibile fare la mossa! Errore dado.");
    }

    /**
     * Stampa un messaggio
     */
    public void messageText() {
        try {
            typeOfInteraction.print(inSocket.readLine());
        } catch (IOException ex) {
            Logger.getLogger(ConnectionClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Piazza un pastore
     */
    public void placeShepard() {
        typeOfInteraction.print("Seleziona una strada dove posizionare il Pastore.");
        outSocket.println(typeOfInteraction.read());
        outSocket.flush();
    }

    /**
     * Muove la pecora nera
     */
    private void moveBlackSheep() {
        typeOfInteraction.print("Allert: vine mossa la pecora nera");
        outSocket.println("ok");
    }

}
