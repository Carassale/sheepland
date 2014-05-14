package server;

import java.util.ArrayList;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.481BD1D1-11A2-4F82-C6CA-1756EBD1468A]
// </editor-fold> 
public interface ServerManager {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.46BA4C72-4F6C-BC13-8B0B-B7F68A42D94D]
    // </editor-fold> 
    public static final ArrayList<connectionManager> games = null;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.B128DB4A-1C01-73E3-2FBA-8535418840E1]
    // </editor-fold> 
    public void waitForPlayer ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.650B1F23-A7E3-81B1-51A9-35E5F40C175A]
    // </editor-fold> 
    public void waitForForcedStart ();

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.C9C68FDB-82F1-0900-8648-C635E0837700]
    // </editor-fold> 
    public void startGame ();

}

