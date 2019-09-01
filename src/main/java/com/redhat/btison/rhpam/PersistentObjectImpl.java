package com.redhat.btison.rhpam;

import java.io.Serializable;

public class PersistentObjectImpl implements PersistentObject, Serializable {

    private String id;

    private String value;

    public PersistentObjectImpl() {}

    public PersistentObjectImpl(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }
}
