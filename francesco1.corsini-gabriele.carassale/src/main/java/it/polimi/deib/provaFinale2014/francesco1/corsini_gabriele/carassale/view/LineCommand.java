package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
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
     *
     * @param connectionClient
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
     * Legge una riga dal terminale
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

    /**
     * Legge una riga dal terminale
     *
     * @return Stringa letta
     */
    public int readInt() {
        String s = read();
        while (!isNumeric(s)) {
            print("Devi inserire un valore numerico!");
            s = read();
        }

        return Integer.parseInt(s);
    }

    /**
     * Controlla se una stringa in realtà può essere convertita a numero
     *
     * @param str Stringa da controllore
     * @return True se è numero
     */
    private boolean isNumeric(String str) {
        try {
            Integer i = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Visualizza a video il movimento dell'animale
     *
     * @param idAnimal Animale spostatp
     * @param idTerrain Terreno destinazione
     */
    public void refreshMoveAnimal(int idAnimal, int idTerrain) {
        if (idAnimal == -2) {
            outVideo.println("Il Lupo si è mosso nel terreno " + idTerrain);
        } else if (idAnimal == -1) {
            outVideo.println("La pecora nera si è mossa nel terreno " + idTerrain);
        } else {
            outVideo.println("È stata mossa una pecora nel terreno " + idTerrain);
        }
    }

    /**
     * Visualizza a video l'animale aggiunto
     *
     * @param idAnimal
     * @param idTerrain Terreno dove posizionare
     * @param kind Tipo di animale
     */
    public void refreshAddAnimal(int idAnimal, int idTerrain, String kind) {
        String k = null;
        if (TypeAnimal.WHITE_SHEEP.toString().equals(kind)) {
            k = "Aggiunta pecora";
        } else if (TypeAnimal.BLACK_SHEEP.toString().equals(kind)) {
            k = "Aggiunta pecora nera";
        } else if (TypeAnimal.RAM.toString().equals(kind)) {
            k = "Aggiunto montone";
        } else if (TypeAnimal.LAMB.toString().equals(kind)) {
            k = "Aggiunto agnello";
        } else if (TypeAnimal.WOLF.toString().equals(kind)) {
            k = "Aggiunto lupo";
        }
        outVideo.println(k + " nel terreno " + idTerrain + ". ID: " + idAnimal);
    }

    /**
     * Visualizza a video l'animale cancellato
     *
     * @param idAnimal Animale cancellato
     */
    public void refreshKillAnimal(int idAnimal) {
        outVideo.println("Uccisa pecora " + idAnimal);
    }

    /**
     * Visualizza a video l'animale trasformato
     *
     * @param idAnimal Animale trasformato
     * @param kind Tipo di trasformazione finale
     */
    public void refreshTransformAnimal(int idAnimal, String kind) {
        String k = null;
        if (TypeAnimal.WHITE_SHEEP.toString().equals(kind)) {
            k = "pecora";
        } else if (TypeAnimal.RAM.toString().equals(kind)) {
            k = "montone";
        }
        outVideo.println("Trasformato agnello " + idAnimal + " in " + k);
    }

    /**
     * Visualizza a video le carte cambiate
     *
     * @param typeOfTerrain Tipo della carta
     * @param isSold True se è stata venduta
     */
    public void refreshCard(String typeOfTerrain, boolean isSold) {
        String s = "Comprata";
        if (isSold) {
            s = "Venduta";
        }
        outVideo.println(s + " carta di tipo " + typeOfTerrain);
    }

    /**
     * Visualizza a video i coin cambiati
     *
     * @param coins Coin variati
     * @param addCoin True se deve aggiungere
     */
    public void refreshCoin(int coins, boolean addCoin) {
        if (coins > 1) {
            String s = "Rimosse";
            if (addCoin) {
                s = "Aggiunte";
            }
            outVideo.println(s + " " + coins + " monete.");
        } else {
            String s = "Rimossa";
            if (addCoin) {
                s = "Aggiunta";
            }
            outVideo.println(s + " una moneta.");
        }
    }

    /**
     * Visualizza a video il pastore aggiunto
     *
     * @param idShepard Pastore da aggiungere
     * @param idRoad Strada dove posizionare
     * @param isMine True se è il suo pastore
     */
    public void refreshAddShepard(int idShepard, int idRoad, boolean isMine) {
        String s = "";
        if (isMine) {
            s = ": è il tuo";
        }
        outVideo.println("Aggiunto pastore " + idShepard + " nella strada " + idRoad + s);
    }

    /**
     * Visualizza a video il movimento del pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     */
    public void refreshMoveShepard(int idShepard, int idRoad) {
        outVideo.println("Mosso pastore " + idShepard + " nella strada " + idRoad);
    }

    /**
     * Esegue un azione chiedendola al giocatore
     */
    public void clickAction() {
        Integer i = 0;
        do {
            print("Fai la tua mossa:");
            print("");
            print("1 - Muovi pastore");
            print("2 - Muovi pecora");
            print("3 - Compra carta");
            print("4 - Accoppia ovini");
            print("5 - Uccidi pecora");
            print("");

            i = new Integer(read());
        } while (i < 1 || i > 5);

        switch (i) {
            case 1:
                moveShepard();
                break;
            case 2:
                moveSheep();
                break;
            case 3:
                buyCard();
                break;
            case 4:
                joinSheep();
                break;
            case 5:
                killSheep();
                break;
        }
    }

    /**
     * Muove il pastore
     */
    public void moveShepard() {
        print("Quale pastore vuoi muovere?");

        int idShepard = readInt();

        print("In quale strada?");
        int idRoad = readInt();

        connectionClient.moveShepard(idShepard, idRoad);
    }

    /**
     * Muove la pecora
     */
    public void moveSheep() {
        print("Quale pecora vuoi muovere?");
        int idSheep = readInt();

        print("In quale terreno?");
        int idTerrain = readInt();

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
        int idSheep = readInt();

        connectionClient.killSheep(idSheep);
    }

    /**
     * Accoppia una pecora con un montone
     */
    public void joinSheep() {
        print("In quale terreno si trovano gli ovini?");
        int idTerrain = readInt();

        connectionClient.joinSheep(idTerrain);
    }

    /**
     * Stamoa a video un messaggio di errore
     *
     * @param message Messaggio da stampare
     */
    public void errorMessage(String message) {
        print("ERRORE!!!");
        print(message);
    }

    /**
     * Chiede a video di posizionare un pastore
     *
     * @param idShepard Pastore da posizionare
     */
    public void placeShepard(int idShepard) {
        print("Seleziona una strada dove posizionare il pastore " + idShepard);
        int idRoad = readInt();

        connectionClient.placeShepard(idRoad);
    }

    public void messageText(String message) {
        print(message);
    }
}
