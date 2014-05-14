package connection;

import server.ArrayList<Socket>; 

// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.8A834F90-A9D4-CB6D-3844-7387559EED67]
// </editor-fold> 
public class ConnectionManagerSockets extends ConnectionManager {

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4DCF92B8-2542-3376-6F31-46C4324EC5BD]
    // </editor-fold> 
    private final ArrayList<PlayerConnection> playerConnections;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.FA1D4125-4135-2608-B8DE-A2C1BEAC3368]
    // </editor-fold> 
    public ConnectionManagerSockets (ArrayList<Socket> playerSockets) {
    }

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,regenBody=yes,id=DCE.7E42B397-719F-98A1-8184-B9CD085D5FBC]
    // </editor-fold> 
    public ArrayList<PlayerConnection> getPlayerConnections () {
        return playerConnections;
    }

}

