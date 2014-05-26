package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.client;

import it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.model.GameTable;
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

    /**
     * Inizializza il BufferReader da tastiera e il PrintWriter a schermo
     */
    public LineCommand() {
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

}
