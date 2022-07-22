package org.myddd.commons.sms.application;

import org.myddd.commons.cache.api.Cache;
import org.myddd.commons.cache.api.CacheBuilder;
import org.myddd.commons.sms.InvalidVerificationCodeException;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class AbstractVerificationCodeApplication {

    private static final Random random  = new Random();

    protected static final String VERIFICATION_PREFIX = "VERIFICATION_%s";

    private static final DateTimeFormatter CURRENT_DAY_FORMAT = DateTimeFormatter.ofPattern("yyyyMM");

    private static final Cache<String> cache = CacheBuilder.newBuilder().build(String.class);

    protected void cacheValidCode(String key,String code){
        cache.put(String.format(VERIFICATION_PREFIX,key),code);
    }

    protected void validCode(String key,String code,boolean isMock){
        if(isMock){
            validCodeForMock(code);
            return;
        }
        var codeValue = getValidCode(key);
        if(!code.equals(codeValue)) throw new InvalidVerificationCodeException();
    }

    private void validCodeForMock(String code){
        var mockCode =  LocalDateTime.now().format(CURRENT_DAY_FORMAT);
        if(!mockCode.equals(code)) throw new InvalidVerificationCodeException();
    }

    private String  getValidCode(String key){
        var cacheKey = String.format(VERIFICATION_PREFIX,key);
        return cache.get(cacheKey);
    }

    protected String randomCode(){
        var min = 100000;
        var max = 999999;
        return String.valueOf(random.nextInt(max - min) + min);
    }

}
