package org.myddd.extensions.security.oauth2.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenController {

    @Resource(name="tokenServices")
    ConsumerTokenServices tokenServices;

    @DeleteMapping(value = "/oauth/token/{tokenId}")
    public ResponseEntity<Void> revokeToken(@PathVariable String tokenId) {
        tokenServices.revokeToken(tokenId);
        return ResponseEntity.ok().build();
    }

}
