package org.myddd.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestBadParameterException {

    @Test
    void testNewBadParameterException(){
        BadParameterException badParameterException = new BadParameterException();
        Assertions.assertEquals(0,badParameterException.getData().length);
        Assertions.assertEquals("BAD PARAMETER",badParameterException.getErrorCode().errorCode());
    }

    @Test
    void testNewBadParameterExceptionWithErrorCode(){
        BadParameterException badParameterException = new BadParameterException(MockErrorCode.MOCK_ERROR_CODE);
        Assertions.assertEquals(0,badParameterException.getData().length);
        Assertions.assertEquals(MockErrorCode.MOCK_ERROR_CODE.errorCode(),badParameterException.getErrorCode().errorCode());
    }

    @Test
    void testNewBadParameterExceptionWithErrorCodeAndData(){
        BadParameterException badParameterException = new BadParameterException(MockErrorCode.MOCK_ERROR_CODE,"A");
        Assertions.assertEquals(1,badParameterException.getData().length);
        Assertions.assertEquals(MockErrorCode.MOCK_ERROR_CODE.errorCode(),badParameterException.getErrorCode().errorCode());

    }
}
