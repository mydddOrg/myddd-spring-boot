package org.myddd.commons.healthy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

public class HealthyControllerTest extends AbstractControllerTest{

    @Test
    void healthy(){
        var response = restTemplate.exchange(baseUrl() + "/healthy", HttpMethod.GET,createEmptyHttpEntity(),HealthyResponse.class);
        Assertions.assertEquals(200,response.getStatusCodeValue());

        var healthyResponse = response.getBody();
        Assertions.assertEquals("no-name",healthyResponse.getName());
        Assertions.assertNotNull(healthyResponse.getHost());
        Assertions.assertNotNull(healthyResponse.getDate());
    }
}
