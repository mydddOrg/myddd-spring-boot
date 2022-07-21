package org.myddd.extensions.security.oauth2.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class MockController {

    /**
     * 抛出一个业务异常，带data,data可用于描述
     */
    @GetMapping("/mock/one")
    ResponseEntity<Object> businessTwoException(){
        return ResponseEntity.ok().build();
    }

}
