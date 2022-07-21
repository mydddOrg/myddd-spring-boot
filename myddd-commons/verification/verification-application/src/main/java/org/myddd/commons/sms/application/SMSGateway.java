package org.myddd.commons.sms.application;

public interface SMSGateway {

    void sendSmsToMobile(String mobile,String code);

    default boolean isMock(){
        return false;
    }

}
