package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.util.logging.Logger;

/**
 * Logger personale che posso attivare o meno
 *
 * @author Gabriele Carassale
 */
public class DebugLogger {

    private static final boolean DEBUG = true;
    private static final boolean PARENT_HANDLERS_ON = true;

    /**
     * Nasconde il costruttore di default
     */
    private DebugLogger() {
    }

    /**
     * Stampa una stringa. Nel caso in cui la variabile Debug sia settata a
     * false, non stampa nulla
     *
     * @param debugPrint Messaggio da stampare
     */
    public static void println(String debugPrint) {
        //se il debug è attivo
        if (DEBUG) {
            System.out.println("debug: " + debugPrint);
        }
        //altrimenti nulla
    }

    /**
     * Nasconde il logger dalle stampe
     */
    public static void turnOffExceptionLog() {
        Logger.getLogger(DebugLogger.class
                .getName()).setUseParentHandlers(DebugLogger.PARENT_HANDLERS_ON);
    }
}
