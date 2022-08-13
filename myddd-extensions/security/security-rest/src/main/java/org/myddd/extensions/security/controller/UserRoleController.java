package org.myddd.extensions.security.controller;

import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.RoleVO;
import org.myddd.extensions.security.api.RoleDto;
import org.myddd.extensions.security.api.UserRoleApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class UserRoleController {

    @Inject
    private IAuthentication authentication;

    @Inject
    private UserRoleApplication roleApplication;

    @PostMapping("/roles")
    public ResponseEntity<RoleVO> createRole(@RequestBody RoleVO roleVO){
        if(!authentication.isAuthentication()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        RoleDto roleDto = roleApplication.createRole(roleVO.toDto());
        return ResponseEntity.ok(RoleVO.of(roleDto));
    }
}
