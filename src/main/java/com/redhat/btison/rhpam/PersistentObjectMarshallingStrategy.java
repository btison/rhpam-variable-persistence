package com.redhat.btison.rhpam;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.security.AccessController;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.drools.core.common.DroolsObjectInputStream;
import org.kie.api.marshalling.ObjectMarshallingStrategy;
import sun.security.action.GetPropertyAction;

public class PersistentObjectMarshallingStrategy implements ObjectMarshallingStrategy {

    private static final File tmpdir = new File(AccessController
            .doPrivileged(new GetPropertyAction("java.io.tmpdir")));

    public boolean accept(Object o) {
        return o instanceof PersistentObject;
    }

    public void write(ObjectOutputStream objectOutputStream, Object o) throws IOException {

    }

    public Object read(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        return null;
    }

    public byte[] marshal(Context context, ObjectOutputStream objectOutputStream, Object o) throws IOException {
        PersistentObject p = (PersistentObject) o;
        String uuid;
        if (p.getId() == null) {
            uuid = UUID.randomUUID().toString();
            p.setId(uuid);
        } else {
            uuid = p.getId();
        }
        byte[] bytes = new ObjectMapper().writeValueAsBytes(p);

        File f = new File(tmpdir, uuid);
        if (f.exists()) {
            f.delete();
        }
        Files.write(f.toPath(), bytes);

        ByteArrayOutputStream buff = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(buff);
        oos.writeUTF(uuid);
        oos.writeUTF(p.getClass().getName());
        oos.close();
        return buff.toByteArray();
    }

    public Object unmarshal(Context context, ObjectInputStream objectInputStream, byte[] object, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        DroolsObjectInputStream is = new DroolsObjectInputStream(new ByteArrayInputStream(object), classLoader);
        String uuid = is.readUTF();
        String className = is.readUTF();

        File f = new File(tmpdir, uuid);
        byte[] bytes = Files.readAllBytes(f.toPath());

        PersistentObject p = (PersistentObject) new ObjectMapper().readValue(bytes, Class.forName(className, true, classLoader));

        return p;
    }

    public Context createContext() {
        return null;
    }
}
