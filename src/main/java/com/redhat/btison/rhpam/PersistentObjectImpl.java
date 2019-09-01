package com.redhat.btison.rhpam;

import java.io.Serializable;

public class PersistentObjectImpl implements PersistentObject, Serializable {

    private String value;

    public PersistentObjectImpl() {}

    public PersistentObjectImpl(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
