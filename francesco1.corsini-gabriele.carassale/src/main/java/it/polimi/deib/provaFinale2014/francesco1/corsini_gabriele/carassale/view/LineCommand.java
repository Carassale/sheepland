package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.view;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client.ConnectionClient;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.DebugLogger;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeAnimal;
import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared.TypeCard;
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
     * Implementa il method superiore e stampa a schermo una stringa
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
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
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
            Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            Logger.getLogger(DebugLogger.class.getName()).log(Level.SEVERE, "Non è stato inserito un valore numerico", nfe);
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
            print("Il Lupo si è mosso nel terreno " + idTerrain);
        } else if (idAnimal == -1) {
            print("La pecora nera si è mossa nel terreno " + idTerrain);
        } else {
            print("È stata mossa una pecora nel terreno " + idTerrain);
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
        print(k + " nel terreno " + idTerrain + ". ID: " + idAnimal);
    }

    /**
     * Visualizza a video l'animale cancellato
     *
     * @param idAnimal Animale cancellato
     */
    public void refreshKillAnimal(int idAnimal) {
        print("Uccisa pecora " + idAnimal);
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
        print("Trasformato agnello " + idAnimal + " in " + k);
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
        print(s + " carta di tipo " + typeOfTerrain);
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
            print(s + " " + coins + " monete.");
        } else {
            String s = "Rimossa";
            if (addCoin) {
                s = "Aggiunta";
            }
            print(s + " una moneta.");
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
        print("Aggiunto pastore " + idShepard + " nella strada " + idRoad + s);
    }

    /**
     * Visualizza a video il movimento del pastore
     *
     * @param idShepard Pastore da muovere
     * @param idRoad Strada destinazione
     */
    public void refreshMoveShepard(int idShepard, int idRoad) {
        print("Mosso pastore " + idShepard + " nella strada  " + idRoad);
    }

    /**
     * Esegue un azione chiedendola al giocatore
     */
    public void clickAction() {
        int i = 0;
        do {
            print("Fai la tua mossa:");
            print("");
            print("1 - Muovi pastore");
            print("2 - Muovi pecora");
            print("3 - Compra carta");
            print("4 - Accoppia ovini");
            print("5 - Uccidi pecora");
            print("");

            i = readInt();
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
            default:
                print("Qualcosa è andato storto... ");
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
        int i;
        String kind = "";
        do {
            print("Quale tipo di carta vuoi acquistare?");
            print("");
            print("1 - " + TypeCard.DESERT.toString());
            print("2 - " + TypeCard.FIELD.toString());
            print("3 - " + TypeCard.FOREST.toString());
            print("4 - " + TypeCard.MOUNTAIN.toString());
            print("6 - " + TypeCard.PLAIN.toString());
            print("5 - " + TypeCard.RIVER.toString());
            print("");

            i = readInt();
        } while (i < 1 || i > 6);

        switch (i) {
            case 1:
                kind = TypeCard.DESERT.toString();
                break;
            case 2:
                kind = TypeCard.FIELD.toString();
                break;
            case 3:
                kind = TypeCard.FOREST.toString();
                break;
            case 4:
                kind = TypeCard.MOUNTAIN.toString();
                break;
            case 5:
                kind = TypeCard.PLAIN.toString();
                break;
            case 6:
                kind = TypeCard.RIVER.toString();
                break;
            default:
                print("Qualcosa è andato storto...");
                break;
        }

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
        print("Errore:");
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

    /**
     * Stampa un messaggio passato come parametro
     *
     * @param message Messaggio da stampare
     */
    public void messageText(String message) {
        print(message);
    }

    /**
     * Riceve la posizione della fence aggiunta
     *
     * @param idRoad
     */
    public void refreshAddFence(int idRoad) {
        print("Aggiunta fence nella strada " + idRoad);
    }

    /**
     * Riceve lo stato finale
     *
     * @param finalPosition Posizione in classifica
     * @param finalScore Punteggio finale
     */
    public void refreshWinner(int finalPosition, int finalScore) {
        if (finalPosition == 1) {
            print("COMPLIMENTI HAI VINTO!!!");
        }
        print("Gioco completato in posizione " + finalPosition + " con un punteggio di " + finalScore + ".");
    }
}
