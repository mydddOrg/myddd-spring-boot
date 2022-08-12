package org.myddd.rest.advice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.lang.ErrorResponse;
import org.myddd.rest.advice.AbstractControllerTest;
import org.myddd.rest.advice.mock.EchoError;
import org.springframework.http.ResponseEntity;

import java.util.Objects;


class MockControllerTest extends AbstractControllerTest {

    @Test
    void testException(){
        ResponseEntity<Void> response = restTemplate.getForEntity(baseUrl()+"/v1/error/exception",Void.class);
        Assertions.assertEquals(500,response.getStatusCodeValue());
    }

    @Test
    void testBusinessOneError(){
        ResponseEntity<ErrorResponse> response = restTemplate.getForEntity(baseUrl()+"/v1/error/businessErrorOne",ErrorResponse.class);

        Assertions.assertEquals(400,response.getStatusCodeValue());
        Assertions.assertEquals(EchoError.ERROR_ONE.toString(), Objects.requireNonNull(response.getBody()).getErrorCode());
        Assertions.assertEquals("media not found",Objects.requireNonNull(response.getBody()).getErrorMsg());
    }
}
