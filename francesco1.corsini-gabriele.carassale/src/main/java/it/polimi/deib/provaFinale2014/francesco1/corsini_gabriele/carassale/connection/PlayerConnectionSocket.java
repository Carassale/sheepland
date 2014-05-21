package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Questa classe serve a mantenere la connessione con il singolo client nel caso
 * sia stato scelto il metodo Socket
 *
 * @author Carassale Gabriele
 */
public class PlayerConnectionSocket extends PlayerConnection {

    private final Socket socket;
    private final Scanner inSocket;
    private final PrintWriter outSocket;

    /**
     * Crea un PlayerConnection e inizializza i socket Scanner e Printer per la
     * comunicazione in/out Client-Server
     *
     * @param socket Viene passato il socket con il quale verrà effettuato
     * l'accoppiamento
     * @throws IOException
     */
    public PlayerConnectionSocket(Socket socket) throws IOException {
        this.socket = socket;
        inSocket = new Scanner(socket.getInputStream());
        outSocket = new PrintWriter(socket.getOutputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    public String getNextLine() {
        return inSocket.nextLine();
    }

    public int getNextInt() {
        return new Integer(inSocket.nextLine());
    }

    public void printLn(String string) {
        outSocket.println(string);
        outSocket.flush();
    }

    public void printLn(int i) {
        Integer integer = i;
        outSocket.println(integer.toString());
        outSocket.flush();
    }

}
