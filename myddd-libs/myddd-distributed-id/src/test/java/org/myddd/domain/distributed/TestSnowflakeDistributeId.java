package org.myddd.domain.distributed;

import org.junit.jupiter.api.Test;
import org.myddd.domain.IDGenerate;

class TestSnowflakeDistributeId {

    private IDGenerate idGenerate = new SnowflakeDistributeId();

    @Test
    void testGenerateId(){
        for (int i = 0; i < 1000; i++) {
            long id = idGenerate.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}
