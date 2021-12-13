package de.fillikos.rf2.server.tool.model;

public class RF2Server {

    // Standing, SessionInfo, alle Aufrufe hier mit rein? !!! implement !!! andere Klassen
    private String ip, port, serverName;
    private int counter;

    public RF2Server() {

    }

    public RF2Server(String serverName, String ip, String port) {
        this.serverName = serverName;
        this.ip = ip;
        this.port = port;
        this.counter = 0;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void addCounter() {
        this.counter++;
    }
}
