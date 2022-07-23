package org.myddd.extensions.organisation;


import org.apache.commons.lang3.RandomStringUtils;
import org.myddd.extensions.organisation.domain.EmployeeIdGenerator;

import javax.inject.Named;
import java.security.SecureRandom;

@Named
public class EmployeeIdGeneratorImpl implements EmployeeIdGenerator {

    private static final int DEFAULT_LENGTH = 12;

    @Override
    public String randomEmployeeId(int length) {
        return RandomStringUtils.random(length, 0, 0, true, true, null, new SecureRandom());
    }

    @Override
    public String randomEmployeeId() {
        return randomEmployeeId(DEFAULT_LENGTH);
    }

    @Override
    public String randomEmployeeId(String prefix) {
        var randomString = randomEmployeeId(DEFAULT_LENGTH);
        return String.format("%s-%s",prefix,randomString);
    }
}
