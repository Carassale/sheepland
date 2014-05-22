package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view.GUImain;
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
public class ConnectionClientSocket implements ConnectionClient{

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    private GUImain mainGUI;
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

        mainGUI = new GUImain(this);
        
        waitLine();
        
        tableView = new TableView();
    }

    private void waitLine() throws IOException, ClassNotFoundException {
        while (true) {
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
            }
        }
    }

    private void doAction() throws IOException {
        outVideo.println("Fai la tua mossa");
        String s = inKeyboard.readLine();
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

    public void moveShepard() throws IOException {
        String s = "";
        outSocket.println("moveShepard");
        outSocket.flush();

        outVideo.println("Quale pastore vuoi muovere?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();

        outVideo.println("In quale strada?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    public void moveSheep() throws IOException {
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

    public void buyCard() throws IOException {
        String s = "";
        outSocket.println("buyCard");
        outSocket.flush();

        outVideo.println("Quale tipo di carta vuoi acquistare?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    public void killSheep() throws IOException {
        String s = "";
        outSocket.println("killSheep");
        outSocket.flush();

        outVideo.println("Quale pecora vuoi ammazzare?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    public void joinSheep() throws IOException {
        String s = "";
        outSocket.println("joinShepard");
        outSocket.flush();

        outVideo.println("In quale terreno si trovano gli ovini?");
        s = inKeyboard.readLine();
        outSocket.println(s);
        outSocket.flush();
    }

    public void setNickname() throws IOException {
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
