package org.myddd.querychannel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TestPositionalParameters {

    @Test
    void testCreatePositionalParameters(){
        BaseQuery.PositionalParameters positionalParameters = BaseQuery.PositionalParameters.create();
        Assertions.assertEquals(0,positionalParameters.getParams().length);

        BaseQuery.PositionalParameters positionalParameters1 = BaseQuery.PositionalParameters.create("A","B","C");
        Assertions.assertEquals(3,positionalParameters1.getParams().length);

        BaseQuery.PositionalParameters positionalParameters2 = BaseQuery.PositionalParameters.create(List.of(1,2,3,4));
        Assertions.assertEquals(4,positionalParameters2.getParams().length);
    }
}
