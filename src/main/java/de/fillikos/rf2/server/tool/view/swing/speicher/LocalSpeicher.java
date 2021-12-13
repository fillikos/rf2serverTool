package de.fillikos.rf2.server.tool.view.swing.speicher;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fillikos.rf2.server.tool.model.LocalTempSpeicher;

import java.io.IOException;
import java.nio.file.Paths;

public class LocalSpeicher {

    private LocalTempSpeicher localTempSpeicher;
    private final String tmpdir = System.getProperty("java.io.tmpdir");

    public LocalSpeicher() {
        super();
        System.out.println(tmpdir);
        loadLocalSpeicher();
    }

    public void loadLocalSpeicher() {
        ObjectMapper om = new ObjectMapper();
        try {
//            CollectionType type = TypeFactory.defaultInstance().constructCollectionType(List.class, LocalTempSpeicher.class);
            localTempSpeicher = om.readValue(Paths.get(tmpdir + "testLocalSpeicher.json").toFile(), LocalTempSpeicher.class);
        } catch (StreamWriteException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(localTempSpeicher == null) {
            localTempSpeicher = new LocalTempSpeicher();
        }
    }

    public void saveLocalSpeicher() {
        ObjectMapper om = new ObjectMapper();
        try {
            System.out.println("gespeichert");
            System.out.println(localTempSpeicher);
            om.writeValue(Paths.get(tmpdir + "testLocalSpeicher.json").toFile(), localTempSpeicher);
        } catch (StreamWriteException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LocalTempSpeicher getLocalTempSpeicher() {
        return localTempSpeicher;
    }

    public void setLocalTempSpeicher(LocalTempSpeicher localTempSpeicher) {
        this.localTempSpeicher = localTempSpeicher;
    }

    public String getTmpdir() {
        return tmpdir;
    }
}
