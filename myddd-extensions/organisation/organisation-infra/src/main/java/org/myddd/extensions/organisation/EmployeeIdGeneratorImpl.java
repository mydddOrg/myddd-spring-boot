package org.myddd.extensions.organisation;


import org.apache.commons.lang3.RandomStringUtils;
import org.myddd.extensions.organisation.domain.EmployeeIdGenerator;

import javax.inject.Named;

@Named
public class EmployeeIdGeneratorImpl implements EmployeeIdGenerator {

    private static final int DEFAULT_LENGTH = 12;

    @Override
    public String randomEmployeeId(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    @Override
    public String randomEmployeeId() {
        return randomEmployeeId(DEFAULT_LENGTH);
    }

    @Override
    public String randomEmployeeId(String prefix) {
        return String.format("%s-%s",prefix,RandomStringUtils.randomAlphanumeric(DEFAULT_LENGTH));
    }
}
