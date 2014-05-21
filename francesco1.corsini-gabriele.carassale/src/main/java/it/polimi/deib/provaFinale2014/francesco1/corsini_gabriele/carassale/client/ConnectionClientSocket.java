package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Questa classe crea la connessione diretta con il GameController tramite la
 * ConnectionManager nel caso sia stato scelto il metodo Socket
 *
 * @author Carassale Gabriele
 */
public class ConnectionClientSocket {

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    private TableView tableView;

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

        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);

        tableView = new TableView();
        waitLine();
    }

    private void waitLine() throws IOException, ClassNotFoundException {
        while (true) {
            String s = inSocket.readLine();
            if (s.equals("wakeUp")) {
                doAction();
            } else if (s.equals("setNikcnam")) {
                setNickname();
            } else if (s.equals("refresh")) {
                //TODO
                //refresh();
            } else if (s.equals("errorCoin")) {
                errorCoin();
            } else if (s.equals("errorMove")) {
                errorMove();
            } else if (s.equals("errorDice")) {
                errorDice();
            } else if (s.equals("PlaceShepard")) {
                placeShepard();
            } else if (s.equals("messageText")) {
                messageText();
            }
        }
    }

    private void doAction() throws IOException {
        outVideo.println("Fai la tua mossa");
        String s = inKeyboard.readLine();
        if (s.equals("moveShepard")) {
            moveShepard();
        } else if (s.equals("moveSheep")) {
            moveSheep();
        } else if (s.equals("buyCard")) {
            buyCard();
        } else if (s.equals("killSheep")) {
            killSheep();
        } else if (s.equals("joinSheep")) {
            joinSheep();
        }
    }

    private void moveShepard() throws IOException {
        String s = "";
        outSocket.println("moveShepard");
        outSocket.flush();

        outVideo.println("Quale pastore vuoi muovere?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();

        outVideo.println("In quale terreno?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    private void moveSheep() throws IOException {
        String s = "";
        outSocket.println("moveSheep");
        outSocket.flush();

        outVideo.println("Quale pecora vuoi muovere?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();

        outVideo.println("In quale terreno?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    private void buyCard() throws IOException {
        String s = "";
        outSocket.println("buyCard");
        outSocket.flush();

        outVideo.println("Quale tipo di carta vuoi acquistare?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    private void killSheep() throws IOException {
        String s = "";
        outSocket.println("killSheep");
        outSocket.flush();

        outVideo.println("Quale pecora vuoi ammazzare?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    private void joinSheep() throws IOException {
        String s = "";
        outSocket.println("joinShepard");
        outSocket.flush();

        outVideo.println("In quale terreno si trovano gli ovini?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    private void setNickname() throws IOException {
        outVideo.println("Impostare il proprio Nickname\n");
        outSocket.println(inKeyboard.readLine());
        outSocket.flush();
    }

    private void refresh() throws FileNotFoundException, IOException, ClassNotFoundException {
        System.out.println("Prova: Aggiorna");

        FileInputStream in = new FileInputStream("save.ser");
        ObjectInputStream ois = new ObjectInputStream(in);
        tableView.setGameTable((GameTable) ois.readObject());
        tableView.refresh();
        ois.close();
    }

    private void errorCoin() {
        outVideo.println("Impossibile fare la mossa! Non hai abbastanza soldi.");
    }

    private void errorMove() {
        outVideo.println("Impossibile fare la mossa! Movimento non valido.");
    }

    //??????
    private void errorDice() {
        outVideo.println("Impossibile fare la mossa! Errore dado.");
    }

    private void messageText() throws IOException {
        outVideo.println(inSocket.readLine());
    }

    private void placeShepard() throws IOException {
        outVideo.println("Seleziona una strada dove posizionare il Pastore.");
        outSocket.println(inKeyboard.readLine());
        outSocket.flush();
    }

}
