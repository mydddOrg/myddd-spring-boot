package org.myddd.commons;

import org.mockito.Mockito;
import org.myddd.commons.sms.application.EmailGateway;
import org.myddd.commons.sms.application.SMSGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {

    @Bean
    EmailGateway emailGateway(){
        return Mockito.mock(EmailGateway.class);
    }

    @Bean
    SMSGateway smsGateway(){
        return Mockito.mock(SMSGateway.class);
    }
}
