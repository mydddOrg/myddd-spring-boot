package org.myddd.commons.sms.application;

public interface EmailGateway {

    void sendSmsToEmail(String email, String code);

    default boolean isMock(){
        return false;
    }
}
