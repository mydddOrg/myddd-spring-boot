package org.myddd.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BusinessExceptionTest {

    @Test
    void testExceptionWithData(){
        BusinessException exception = new BusinessException(MockErrorCode.MOCK_ERROR_CODE,"1","2");
        Assertions.assertEquals("MOCK_ERROR_CODE,1,2",exception.getMessage());
    }
}
