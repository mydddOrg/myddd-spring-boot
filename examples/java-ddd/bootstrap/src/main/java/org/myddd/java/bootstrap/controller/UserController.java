package org.myddd.java.bootstrap.controller;

import org.myddd.java.api.UserApplication;
import org.myddd.java.api.dto.UserDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.inject.Inject;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/v1")
public class UserController {

    @Inject
    private UserApplication userApplication;

    @PostMapping("/users")
    ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        var created = userApplication.createUser(userDTO);
        return ResponseEntity.ok(created);
    }
}
