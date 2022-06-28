package org.myddd.querychannel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class NamedParametersTest {

    @Test
    void testCreateNamedParameters(){
        BaseQuery.NamedParameters namedParameters = BaseQuery.NamedParameters.create();
        Assertions.assertEquals(0,namedParameters.getParams().size());

        BaseQuery.NamedParameters namedParameters1 = BaseQuery.NamedParameters.create(Map.of("A",1,"B",2));
        Assertions.assertTrue(namedParameters1.getParams().size() > 0);
    }

    @Test
    void testAddParameters(){
        BaseQuery.NamedParameters namedParameters = BaseQuery.NamedParameters.create();
        namedParameters.add("A",1)
                .add("B",2);
        Assertions.assertTrue(namedParameters.getParams().size() > 0);
    }
}
