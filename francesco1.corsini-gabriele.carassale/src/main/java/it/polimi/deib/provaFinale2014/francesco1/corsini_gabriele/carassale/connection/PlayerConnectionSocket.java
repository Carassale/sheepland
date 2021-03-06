package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.StatusMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Questa classe serve a mantenere la connessione con il singolo client nel caso
 * sia stato scelto il tipo Socket
 *
 * @author Carassale Gabriele
 */
public class PlayerConnectionSocket extends PlayerConnection {

    private Socket socket;
    private Scanner inSocket;
    private PrintWriter outSocket;
    private int idPlayer;
    private String nickname;

    /**
     * Crea un PlayerConnection e inizializza i socket Scanner e Printer per la
     * comunicazione in/out Client-Server
     *
     * @param socket Viene passato il socket con il quale verrà effettuato
     * l'accoppiamento
     * @param idPlayer Id del giocatore all'interno del round
     * @param nickname
     * @throws java.io.IOException
     */
    public PlayerConnectionSocket(Socket socket, int idPlayer, String nickname) throws IOException {
        this.nickname = nickname;
        this.socket = socket;
        this.idPlayer = idPlayer;
        inSocket = new Scanner(socket.getInputStream());
        outSocket = new PrintWriter(socket.getOutputStream());
    }

    /**
     * Restituisce il socket del player
     *
     * @return Socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Restituisce l'id player
     *
     * @return int IdPlayer
     */
    @Override
    public int getIdPlayer() {
        return idPlayer;
    }

    /**
     * In caso di riaggancio del client, viene settato il nuovo socket
     *
     * @param socket Socket da settare
     * @throws IOException
     */
    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        inSocket = new Scanner(socket.getInputStream());
        outSocket = new PrintWriter(socket.getOutputStream());
    }

    /**
     * Restituisce la prossima line ricevuta dal client via socket
     *
     * @return String
     * @throws PlayerDisconnect
     */
    public String getNextLine() throws PlayerDisconnect {
        try {
            return inSocket.nextLine();
        } catch (NoSuchElementException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            throw new PlayerDisconnect(StatusMessage.PLAYER_DISCONNECTED.toString());
        }
    }

    /**
     * Restituisce il prossimo numero ricevuto dal client via socket
     *
     * @return int
     * @throws PlayerDisconnect
     */
    public int getNextInt() throws PlayerDisconnect {
        try {
            return Integer.valueOf(inSocket.nextLine());
        } catch (NoSuchElementException ex) {
            Logger.getLogger(DebugLogger.class.getName()).log(DebugLogger.getLevel(), ex.getMessage(), ex);
            throw new PlayerDisconnect(StatusMessage.PLAYER_DISCONNECTED.toString());
        }
    }

    /**
     * Invia una line al client via sokcet
     *
     * @param string la string da inviare
     */
    public void printLn(String string) {
        outSocket.println(string);
        outSocket.flush();
    }

    /**
     * Invia un numero al client via socket
     *
     * @param i il numero da inviare
     */
    public void printLn(int i) {
        Integer integer = i;
        outSocket.println(integer.toString());
        outSocket.flush();
    }

    /**
     * Restituisce il nickname del player
     *
     * @return
     */
    @Override
    public String getNickname() {
        return nickname;
    }

    /**
     * Setta il nickname del player
     *
     * @param nickname Nickname da settare
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
