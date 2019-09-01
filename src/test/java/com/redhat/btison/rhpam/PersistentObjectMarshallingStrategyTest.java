package com.redhat.btison.rhpam;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public class PersistentObjectMarshallingStrategyTest {

    @Test
    public void testMarshallUnMarshall() throws Exception {

        PersistentObjectMarshallingStrategy strategy = new PersistentObjectMarshallingStrategy();
        PersistentObject p = new PersistentObjectImpl("test");

        byte[] marshalled = strategy.marshal(null, null, p);

        PersistentObjectImpl unmarshalled = (PersistentObjectImpl) strategy.unmarshal(null, null, marshalled, Thread.currentThread().getContextClassLoader());

        MatcherAssert.assertThat(unmarshalled.getValue(), CoreMatchers.equalTo("test"));

    }

}
