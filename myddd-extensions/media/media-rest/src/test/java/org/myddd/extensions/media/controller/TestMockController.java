package org.myddd.extensions.media.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.media.AbstractControllerTest;
import org.myddd.extensions.media.MockErrorCode;
import org.myddd.lang.ErrorResponse;
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
    void testBadParameterExceptionTwo(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(baseUrl()+"/v1/error/badParameterExceptionTwo",ErrorResponse.class);
        Assertions.assertEquals(401,response.getStatusCodeValue());
        Assertions.assertEquals(MockErrorCode.BAD_PARAMETER.toString(), Objects.requireNonNull(response.getBody()).getErrorCode());
    }
}
