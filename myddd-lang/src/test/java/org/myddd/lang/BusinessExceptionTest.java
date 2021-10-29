package org.myddd.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BusinessExceptionTest {

    @Test
    void testExceptionWithData(){
        BusinessException exception = new BusinessException(MockErrorCode.MOCK_ERROR_CODE,"1","2");
        Assertions.assertEquals(exception.getMessage(),"MOCK_ERROR_CODE,1,2");
    }
}
