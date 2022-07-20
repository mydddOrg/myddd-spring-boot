package org.myddd.extensions.security.domain;

import org.myddd.domain.AbstractRepository;

import java.util.List;

public interface UserRepository extends AbstractRepository {

    User queryLocalUserByUserId(String userId);

    User queryUserByEmail(String email,UserType userType);

    User queryUserByPhone(String phone,UserType userType);

    List<String> queryExistsUserIdByType(UserType userType);
}
