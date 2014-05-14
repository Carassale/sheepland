package connection;

import server.ArrayList<Socket>; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.7C400A2B-A471-40F1-CCCD-636F365DD2B2]
// </editor-fold> 
public class PlayerConnection {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.11767FF2-F246-E5EF-4936-0886B9A4862D]
    // </editor-fold> 
    private final ArrayList<Socket> sockets;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.6F8035AE-FA31-67E0-D690-5386868CBB43]
    // </editor-fold> 
    private final ArrayList<Scanner> scanner;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.1A9D7174-6A73-1BDA-36BD-7A6627982011]
    // </editor-fold> 
    private final ArrayList<PrintWriter> outSockets;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.AB6305E8-513B-4643-C743-495E407F7FA9]
    // </editor-fold> 
    private final ArrayList<Connection> connections;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.BB205A84-F4E3-4CC5-F480-CB4E662328CD]
    // </editor-fold> 
    public PlayerConnection () {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7AE6166E-DB42-BE70-82BF-46F118696CCC]
    // </editor-fold> 
    public ArrayList<Connection> getConnections () {
        return connections;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7973B597-81B4-AC09-2E67-8198E72E4E91]
    // </editor-fold> 
    public ArrayList<PrintWriter> getOutSockets () {
        return outSockets;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.76C38A3D-B6C0-4386-9DAE-83D6D06CCCD1]
    // </editor-fold> 
    public ArrayList<Scanner> getScanner () {
        return scanner;
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.EB48AA0E-86DE-C276-E6CE-A2068D4FAAFE]
    // </editor-fold> 
    public ArrayList<Socket> getSockets () {
        return sockets;
    }

}

