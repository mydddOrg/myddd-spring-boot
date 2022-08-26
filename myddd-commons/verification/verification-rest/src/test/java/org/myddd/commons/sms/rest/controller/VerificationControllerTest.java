package org.myddd.commons.sms.rest.controller;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.commons.sms.rest.AbstractControllerTest;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class VerificationControllerTest extends AbstractControllerTest {

    @Test
    void testSendSms(){
        var response = restTemplate.exchange(baseUrl() + "/v1/verification-code", HttpMethod.POST,createHttpEntityFromString(sendSMSJson()),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testSendEmailSms(){
        var response = restTemplate.exchange(baseUrl() + "/v1/verification-code", HttpMethod.POST,createHttpEntityFromString(sendEmailSmsJson()),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testValidCode(){
        var response = restTemplate.exchange(baseUrl() + "/v1/verification-code", HttpMethod.POST,createHttpEntityFromString(sendSMSJson()),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        DateTimeFormatter CURRENT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
        var mockCode =  LocalDateTime.now().format(CURRENT_DAY_FORMAT);

        var validResponse = restTemplate.exchange(baseUrl() + "/v1/verification-code/validation", HttpMethod.PUT,createHttpEntityFromString(validSmsJson(mockCode)),Void.class);
        Assertions.assertTrue(validResponse.getStatusCode().is2xxSuccessful());

        var notValidResponse = restTemplate.exchange(baseUrl() + "/v1/verification-code/validation", HttpMethod.PUT,createHttpEntityFromString(validSmsJson(randomId())),Void.class);
        Assertions.assertFalse(notValidResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void validEmailCode(){
        var response = restTemplate.exchange(baseUrl() + "/v1/verification-code", HttpMethod.POST,createHttpEntityFromString(sendEmailSmsJson()),Void.class);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());

        DateTimeFormatter CURRENT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");
        var mockCode =  LocalDateTime.now().format(CURRENT_DAY_FORMAT);

        var validResponse = restTemplate.exchange(baseUrl() + "/v1/verification-code/validation", HttpMethod.PUT,createHttpEntityFromString(validEmailJson(mockCode)),Void.class);
        Assertions.assertTrue(validResponse.getStatusCode().is2xxSuccessful());

        var notValidResponse = restTemplate.exchange(baseUrl() + "/v1/verification-code/validation", HttpMethod.PUT,createHttpEntityFromString(validEmailJson(randomId())),Void.class);
        Assertions.assertFalse(notValidResponse.getStatusCode().is2xxSuccessful());
    }


    private String sendEmailSmsJson(){
        var json = new JsonObject();
        json.addProperty("email","lingen.liu@gmail.com");
        return json.toString();
    }

    private String sendSMSJson(){
        var json = new JsonObject();
        json.addProperty("mobile","18620501006");
        return json.toString();
    }

    private String validSmsJson(String code){
        var json = new JsonObject();
        json.addProperty("mobile","18620501006");
        json.addProperty("code",code);
        return json.toString();
    }

    private String validEmailJson(String code){
        var json = new JsonObject();
        json.addProperty("email","lingen.liu@gmail.com");
        json.addProperty("code",code);
        return json.toString();
    }
}
