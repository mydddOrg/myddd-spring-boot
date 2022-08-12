package org.myddd.commons.healthy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthyController {

    @Value("${server.name:no-name}")
    private String name;

    @GetMapping("/healthy")
    public ResponseEntity<HealthyResponse> healthy(){
        return ResponseEntity.ok(new HealthyResponse(name));
    }

}
