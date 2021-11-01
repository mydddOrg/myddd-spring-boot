package org.myddd.persistence.mock;

import org.myddd.domain.AbstractRepository;

public interface UserRepository extends AbstractRepository {

    User queryUserByUserId(String userId);

}
