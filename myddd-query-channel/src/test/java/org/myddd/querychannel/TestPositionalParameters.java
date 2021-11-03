package org.myddd.querychannel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.querychannel.basequery.PositionalParameters;

import java.util.List;

class TestPositionalParameters {

    @Test
    void testCreatePositionalParameters(){
        PositionalParameters positionalParameters = PositionalParameters.create();
        Assertions.assertEquals(0,positionalParameters.getParams().length);

        PositionalParameters positionalParameters1 = PositionalParameters.create("A","B","C");
        Assertions.assertEquals(3,positionalParameters1.getParams().length);

        PositionalParameters positionalParameters2 = PositionalParameters.create(List.of(1,2,3,4));
        Assertions.assertEquals(4,positionalParameters2.getParams().length);
    }
}
