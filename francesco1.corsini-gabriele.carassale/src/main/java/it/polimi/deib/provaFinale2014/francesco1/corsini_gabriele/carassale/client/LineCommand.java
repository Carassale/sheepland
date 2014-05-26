package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementa la classe TypeOfInteraction per gestire le interazioni tramite
 * linea di comando
 *
 * @author Gabriele Carassale
 */
public class LineCommand implements TypeOfInteraction {

    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    private ConnectionClient connectionClient;

    /**
     * Inizializza il BufferReader da tastiera e il PrintWriter a schermo
     */
    public LineCommand(ConnectionClient connectionClient) {
        this.connectionClient = connectionClient;
        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
    }

    /**
     * Implementa il metodo superiore e stampa a schermo una stringa
     *
     * @param string Stringa da stampare
     */
    public void print(String string) {
        outVideo.println(string);
    }

    /**
     * Implementa il metodo superiore e legge una riga dal terminale
     *
     * @return Stringa letta
     */
    public String read() {
        String s = "";
        try {
            s = inKeyboard.readLine();
        } catch (IOException ex) {
            Logger.getLogger(LineCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    public void refreshMoveAnimal(int idAnimal, int idTerrain) {
        if (idAnimal == -2) {
            outVideo.println("È stata mossa la pecora nera nel terreno " + idTerrain);
        } else if (idAnimal == -1) {
            outVideo.println("Si è mosso il lupo nel terreno " + idTerrain);
        } else {
            outVideo.println("È stata mossa una pecora nel terreno " + idTerrain);
        }
    }

    public void refreshAddAnimal(int idTerrain, String kind) {
        String k = null;
        if ("whiteSheep".equals(kind)) {
            k = "Aggiunta pecora";
        } else if ("blackSheep".equals(kind)) {
            k = "Aggiunta pecora nera";
        } else if ("ram".equals(kind)) {
            k = "Aggiunto montone";
        } else if ("lamb".equals(kind)) {
            k = "Aggiunto agnello";
        } else if ("wolf".equals(kind)) {
            k = "Aggiunto lupo";
        }
        outVideo.println(k + " nel terreno " + idTerrain);
    }

    public void refreshKillAnimal(int idAnimal) {
        outVideo.println("Uccisa pecora " + idAnimal);
    }

    public void refreshTransformAnimal(int idAnimal, String kind) {
        String k = null;
        if ("whiteSheep".equals(kind)) {
            k = "pecora";
        } else if ("ram".equals(kind)) {
            k = "montone";
        }
        outVideo.println("Trasformato agnello " + idAnimal + " in " + k);
    }

    public void refreshCard(String typeOfTerrain, boolean isSold) {
        String s = "Comprata";
        if (isSold) {
            s = "Venduta";
        }
        outVideo.println(s + " carta di tipo " + typeOfTerrain);
    }

    public void refreshCoin(int coins, boolean addCoin) {
        String s = "Rimossi";
        if (addCoin) {
            s = "Aggiunti";
        }
        outVideo.println(s + " " + coins + " moente.");
    }

    public void refreshAddShepard(int idShepard, int idRoad) {
        outVideo.println("Aggiunto pastore " + idShepard + " nella strada " + idRoad);

    }

    public void refreshMoveShepard(int idShepard, int idRoad) {
        outVideo.println("Mosso pastore " + idShepard + " nella strada " + idRoad);
    }

    /**
     * Esegue un azione chiedendola al giocatore
     */
    public void clickAction() {
        print("Fai la tua mossa");

        String s = read();
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
        print("Quale pastore vuoi muovere?");
        Integer idShepard = new Integer(read());

        print("In quale strada?");
        Integer idRoad = new Integer(read());

        connectionClient.moveShepard(idShepard, idRoad);
    }

    /**
     * Muove la pecora
     */
    public void moveSheep() {
        print("Quale pecora vuoi muovere?");
        Integer idSheep = new Integer(read());

        print("In quale terreno?");
        Integer idTerrain = new Integer(read());

        connectionClient.moveSheep(idSheep, idTerrain);
    }

    /**
     * Compra una carta
     */
    public void buyCard() {
        print("Quale tipo di carta vuoi acquistare?");
        String kind = read();

        connectionClient.buyCard(kind);
    }

    /**
     * Uccide una pecora
     */
    public void killSheep() {
        print("Quale pecora vuoi ammazzare?");
        Integer idSheep = new Integer(read());

        connectionClient.killSheep(idSheep);
    }

    /**
     * Accoppia una pecora con un montone
     */
    public void joinSheep() {
        print("In quale terreno si trovano gli ovini?");
        Integer idTerrain = new Integer(read());

        connectionClient.joinSheep(idTerrain);
    }

    public void setNickname() {
        print("Impostare il proprio Nickname");
        String s = read();

        connectionClient.setNickname(s);
    }

    public void errorMessage(String message) {
        print("ERRORE!!!");
        print(message);
    }

    public void placeShepard(int idShepard) {
        print("Seleziona una strada dove posizionare il pastore " + idShepard);
        Integer idRoad = new Integer(read());

        connectionClient.placeShepard(idRoad);
    }
}
