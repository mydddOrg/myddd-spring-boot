package org.myddd.java.domain;

import org.myddd.domain.AbstractRepository;

import java.util.List;

public interface UserRepository extends AbstractRepository {

    User createUser(User user);

    List<User> searchUserByName(String name);
}
