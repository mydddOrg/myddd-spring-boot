package org.myddd.querychannel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.querychannel.basequery.NamedParameters;

import java.util.Map;

class TestNamedParameters {

    @Test
    void testCreateNamedParameters(){
        NamedParameters namedParameters = NamedParameters.create();
        Assertions.assertEquals(0,namedParameters.getParams().size());

        NamedParameters namedParameters1 = NamedParameters.create(Map.of("A",1,"B",2));
        Assertions.assertTrue(namedParameters1.getParams().size() > 0);
    }

    @Test
    void testAddParameters(){
        NamedParameters namedParameters = NamedParameters.create();
        namedParameters.add("A",1)
                .add("B",2);
        Assertions.assertTrue(namedParameters.getParams().size() > 0);
    }
}
