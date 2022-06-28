package org.myddd.extensions.security.domain;

public interface UserPasswordEncoder {

    String encodePassword(String password);

}
