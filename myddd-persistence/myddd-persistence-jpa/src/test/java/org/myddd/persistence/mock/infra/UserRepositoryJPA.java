package org.myddd.persistence.mock.infra;

import org.myddd.persistence.jpa.AbstractRepositoryJPA;
import org.myddd.persistence.mock.User;
import org.myddd.persistence.mock.UserRepository;

import javax.inject.Named;

@Named
public class UserRepositoryJPA extends AbstractRepositoryJPA implements UserRepository {

    @Override
    public User queryUserByUserId(String userId) {
        return getEntityManager().createQuery("from User where userId = :userId", User.class)
                .setParameter("userId",userId)
                .getResultList().stream().findFirst().orElse(null);
    }

}
