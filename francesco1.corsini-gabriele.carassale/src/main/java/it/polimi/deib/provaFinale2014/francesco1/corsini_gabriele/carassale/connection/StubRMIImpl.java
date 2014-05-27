package it.polimi.deib.provaFinale2014.francesco1.corsini_gabriele.carassale.connection;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class StubRMIImpl implements StubRMI {

    private Integer currentId = 0;
    private ArrayList<PlayerConnectionRMI> playerConnection;

    public StubRMIImpl(ArrayList<PlayerConnectionRMI> playerConnection) {
        this.playerConnection = playerConnection;
    }

    @Override
    public String connect() {
        String id = currentId.toString();
        playerConnection.add(new PlayerConnectionRMI(currentId));

        System.out.println("Chiamato stub");

        currentId++;

        // Restituiamo al chiamante (il client) l'ID del client associato
        return id;
    }

    public String checkStatus(int idConnection) throws RemoteException {
        return "";
    }

}
