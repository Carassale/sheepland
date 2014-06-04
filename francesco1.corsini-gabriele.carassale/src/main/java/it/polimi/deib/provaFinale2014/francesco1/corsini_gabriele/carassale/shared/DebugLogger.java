package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.shared;

import java.util.logging.Logger;

/**
 *
 * @author Gabriele Carassale
 */
public class DebugLogger {

    private static final boolean DEBUG = true;
    private static final boolean PARENT_HANDLERS_ON = true;

    private DebugLogger() {
    }

    public static void println(String debugPrint) {
        //se il debug è attivo
        if (DEBUG) {
            System.out.println("debug: " + debugPrint);
        }
        //altrimenti nulla
    }

    public static void turnOffExceptionLog() {
        Logger.getLogger(DebugLogger.class
                .getName()).setUseParentHandlers(DebugLogger.PARENT_HANDLERS_ON);
    }
}
