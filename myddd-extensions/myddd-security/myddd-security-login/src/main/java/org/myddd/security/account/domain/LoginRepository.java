package org.myddd.security.account.domain;

public interface LoginRepository {

    LoginEntity createLoginUser(LoginEntity loginEntity);

    LoginEntity updateUser(LoginEntity loginEntity);

    LoginEntity findByUsername(String username);

    boolean isEmpty();

    boolean userExists(String username);

    boolean deleteUser(String username);

    void enable(LoginEntity loginEntity);

    void disable(LoginEntity loginEntity);
}
