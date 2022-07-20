package org.myddd.extensions.security;

import org.myddd.extensions.security.domain.User;
import org.myddd.extensions.security.domain.UserRepository;
import org.myddd.extensions.security.domain.UserType;
import org.myddd.persistence.jpa.AbstractRepositoryJPA;

import javax.inject.Named;
import java.util.List;

@Named
public class UserRepositoryJPA extends AbstractRepositoryJPA implements UserRepository {

    public static final String USER_TYPE = "userType";

    @Override
    public User queryLocalUserByUserId(String userId) {
        return getEntityManager().createQuery("from User where userId = :userId and userType = :userType",User.class)
                .setParameter("userId",userId)
                .setParameter(USER_TYPE, UserType.LOCAL)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public User queryUserByEmail(String email,UserType userType) {
        return getEntityManager()
                .createQuery("from User where email = :email and userType = :userType",User.class)
                .setParameter("email",email)
                .setParameter(USER_TYPE,userType)
                .getResultList().stream().findFirst().orElse(null);
    }

    public User queryUserByPhone(String phone,UserType userType){
        return getEntityManager()
                .createQuery("from User where phone = :phone and userType = :userType",User.class)
                .setParameter("phone",phone)
                .setParameter(USER_TYPE,userType)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<String> queryExistsUserIdByType(UserType userType) {
        return getEntityManager().createQuery("select userId from User where userType = :userType",String.class)
                .setParameter(USER_TYPE,userType)
                .getResultList();
    }

}
