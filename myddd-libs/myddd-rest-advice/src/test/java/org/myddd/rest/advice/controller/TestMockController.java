package org.myddd.rest.advice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.lang.ErrorResponse;
import org.myddd.rest.advice.AbstractControllerTest;
import org.myddd.rest.advice.MockErrorCode;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

class TestMockController extends AbstractControllerTest {

    @Test
    void testBusinessOneError(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(baseUrl()+"/v1/error/businessErrorOne",ErrorResponse.class);

        Assertions.assertEquals(400,response.getStatusCodeValue());
        Assertions.assertEquals(MockErrorCode.MEDIA_NOT_FOUND.toString(), Objects.requireNonNull(response.getBody()).getErrorCode());
        Assertions.assertEquals("media not found",Objects.requireNonNull(response.getBody()).getErrorMsg());
    }


    @Test
    void testBusinessTwoError(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(baseUrl()+"/v1/error/businessErrorTwo",ErrorResponse.class);

        Assertions.assertEquals(400,response.getStatusCodeValue());
        Assertions.assertEquals(MockErrorCode.FILE_NOT_FOUND.toString(), Objects.requireNonNull(response.getBody()).getErrorCode());
        Assertions.assertEquals("file my_avatar.png not found",Objects.requireNonNull(response.getBody()).getErrorMsg());
    }

    @Test
    void testBadParameterException(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(baseUrl()+"/v1/error/badParameterException",ErrorResponse.class);
        Assertions.assertEquals(401,response.getStatusCodeValue());
    }

    @Test
    void testBadParameterExceptionWithData(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(baseUrl()+"/v1/error/badParameterExceptionWithData",ErrorResponse.class);
        Assertions.assertEquals(401,response.getStatusCodeValue());
        Assertions.assertEquals("ERROR",response.getBody().getErrorMsg());
    }

    @Test
    void testBadParameterExceptionTwo(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(baseUrl()+"/v1/error/badParameterExceptionTwo",ErrorResponse.class);
        Assertions.assertEquals(401,response.getStatusCodeValue());
        Assertions.assertEquals(MockErrorCode.BAD_PARAMETER.toString(), Objects.requireNonNull(response.getBody()).getErrorCode());
    }

    @Test
    void testException(){
        ResponseEntity<Void> response = restTemplate.getForEntity(baseUrl()+"/v1/error/exception",Void.class);
        Assertions.assertEquals(500,response.getStatusCodeValue());
    }
}
