package org.myddd.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BusinessExceptionTest {

    @Test
    void testExceptionWithData(){
        BusinessException exception = new BusinessException(MockErrorCode.MOCK_ERROR_CODE,"1","2");
        Assertions.assertEquals("MOCK_ERROR_CODE,1,2",exception.getMessage());
    }

    @Test
    void testCreateBusinessException(){
        BusinessException businessException = new BusinessException(MockErrorCode.MOCK_ERROR_CODE);
        Assertions.assertEquals(0,businessException.getData().length);
        Assertions.assertEquals(MockErrorCode.MOCK_ERROR_CODE,businessException.getErrorCode());
    }

    @Test
    void testCreateBusinessExceptionWithParam(){
        BusinessException businessException = new BusinessException(MockErrorCode.MOCK_ERROR_CODE,"A","B");
        Assertions.assertEquals(2,businessException.getData().length);
        Assertions.assertEquals(MockErrorCode.MOCK_ERROR_CODE,businessException.getErrorCode());
    }
}
