package org.myddd.java.domain.repository;

import org.myddd.java.domain.User;
import org.myddd.java.domain.UserRepository;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.List;

@Named
public class UserRepositoryJPA extends AbstractRepositoryJPA implements UserRepository {
    @Override
    public User createUser(User user) {
        return save(user);
    }

    @Override
    public List<User> searchUserByName(String name) {
        return getEntityManager()
                .createQuery("from User where name like :name",User.class)
                .setParameter("name","%" + name + "%")
                .getResultList();
    }
}
