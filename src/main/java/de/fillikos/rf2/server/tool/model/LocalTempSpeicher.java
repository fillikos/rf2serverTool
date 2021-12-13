package de.fillikos.rf2.server.tool.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class LocalTempSpeicher {

    private List<RF2Server> serverListe = new ArrayList<>();

    public LocalTempSpeicher() {

    }

    public List<RF2Server> getServerListe() {
        return serverListe;
    }

    public void setServerListe(List<RF2Server> serverListe) {
        this.serverListe = serverListe;
    }

    public void addNewServer(String serverName, String ip, String port) {
        if(!serverName.equals("")) {
            boolean speichern = true;
            for(RF2Server r: serverListe) {
                if( (r.getServerName().equals(serverName)) ) {
                    speichern = false;
                    JOptionPane.showMessageDialog(null, "Der Servername ist bereits vorhanden.");
                }
            }
            if(speichern) {
                serverListe.add(new RF2Server(serverName, ip, port));
            }
        } else {
            JOptionPane.showMessageDialog(null, "Bitte einen Servernamen eingeben.");
        }
    }
}
