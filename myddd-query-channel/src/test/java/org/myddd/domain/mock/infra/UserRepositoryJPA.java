package org.myddd.domain.mock.infra;

import org.myddd.domain.mock.User;
import org.myddd.domain.mock.UserRepository;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import jakarta.inject.Named;

@Named
public class UserRepositoryJPA extends AbstractRepositoryJPA implements UserRepository {

    @Override
    public User queryUserByUserId(String userId) {
        return getEntityManager().createQuery("from User where userId = :userId",User.class)
                .setParameter("userId",userId)
                .getResultList().stream().findFirst().orElse(null);
    }

}
