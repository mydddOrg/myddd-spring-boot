package org.myddd.extensions.security.oauth2.controller;

import org.myddd.extensions.security.IAuthentication;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class MockAuthController {

    @Inject
    private IAuthentication authentication;

    @GetMapping("/mock/auth")
    ResponseEntity<Object> authed(){
        if(authentication.isAuthentication()){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/mock/userId")
    ResponseEntity<Object> userId(){
        if(!authentication.isAuthentication()){
            return ResponseEntity.badRequest().build();
        }
        MockUser mockUser = new MockUser(authentication.loginUserId());
        return ResponseEntity.ok(mockUser);
    }
}
