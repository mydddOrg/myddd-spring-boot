package org.myddd.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.UUID;

class TestErrorResponse {

    @Test
    void testCreateBadErrorResponse(){
        String errorMsg = randomId();
        ErrorResponse errorResponse = ErrorResponse.badRequest(errorMsg).build();
        Assertions.assertEquals("BAD REQUEST",errorResponse.getErrorCode());
        Assertions.assertEquals(errorMsg,errorResponse.getErrorMsg());
        Assertions.assertNull(errorResponse.getErrorStatus());
    }

    @Test
    void testCreateErrorResponse(){
        String errorCode = randomId();
        Integer errorStatus = new Random().nextInt();
        ErrorResponse errorResponse = ErrorResponse
                .newBuilder()
                .setErrorCode(errorCode)
                .setErrorStatus(errorStatus)
                .build();

        Assertions.assertEquals(errorCode,errorResponse.getErrorCode());
        Assertions.assertEquals(errorStatus,errorResponse.getErrorStatus());
        Assertions.assertNull(errorResponse.getErrorMsg());
    }

    @Test
    void testCreateErrorResponseWithErrorMsg(){
        String errorCode = "ERROR_ONE";

        Integer errorStatus = new Random().nextInt();
        ErrorResponse errorResponse = ErrorResponse
                .newBuilder()
                .setErrorCode(errorCode)
                .setErrorStatus(errorStatus)
                .build();

        Assertions.assertEquals(errorCode,errorResponse.getErrorCode());
        Assertions.assertEquals(errorStatus,errorResponse.getErrorStatus());
        Assertions.assertEquals("this is error msg",errorResponse.getErrorMsg());
    }

    @Test
    void testI18nErrorMsg(){
        String errorCode = "ERROR_ONE";

        Integer errorStatus = new Random().nextInt();
        ErrorResponse errorResponse = ErrorResponse
                .newBuilder()
                .setErrorCode(errorCode)
                .setErrorStatus(errorStatus)
                .setLanguage("ZH-CN")
                .build();

        Assertions.assertEquals(errorCode,errorResponse.getErrorCode());
        Assertions.assertEquals(errorStatus,errorResponse.getErrorStatus());
        Assertions.assertEquals("这是一个错误",errorResponse.getErrorMsg());

        ErrorResponse anotherError = ErrorResponse
                .newBuilder()
                .setErrorCode("ERROR_TWO")
                .setErrorStatus(errorStatus)
                .setLanguage("ZH-CN")
                .build();

        Assertions.assertEquals("THIS IS ANOTHER ERROR",anotherError.getErrorMsg());
    }

    @Test
    void testCreateErrorResponseWithErrorMsgAndParams(){
        String errorCode = "ERROR_WITH_PARAM";

        Integer errorStatus = new Random().nextInt();
        ErrorResponse errorResponse = ErrorResponse
                .newBuilder()
                .setErrorCode(errorCode)
                .setErrorStatus(errorStatus)
                .setParams(new String[]{"A","B"})
                .build();

        Assertions.assertEquals(errorCode,errorResponse.getErrorCode());
        Assertions.assertEquals(errorStatus,errorResponse.getErrorStatus());
        Assertions.assertEquals("A_B",errorResponse.getErrorMsg());
    }

    private String randomId(){
        return UUID.randomUUID().toString();
    }
}
