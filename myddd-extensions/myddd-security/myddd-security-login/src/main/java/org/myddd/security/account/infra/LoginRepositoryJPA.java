package org.myddd.security.account.infra;

import org.myddd.security.account.domain.LoginEntity;
import org.myddd.security.account.domain.LoginRepository;
import org.myddd.utils.Assert;

import javax.inject.Named;
import java.util.Objects;

@Named
public class LoginRepositoryJPA extends AbstractRepositoryJPA implements LoginRepository {

    @Override
    public LoginEntity createLoginUser(LoginEntity loginEntity) {
        Assert.isNull(loginEntity.getId());
        return save(loginEntity);
    }

    @Override
    public LoginEntity updateUser(LoginEntity loginEntity) {
        Assert.notNull(loginEntity.getId());
        loginEntity.setUpdateDate(System.currentTimeMillis());
        return save(loginEntity);
    }

    @Override
    public LoginEntity findByUsername(String username) {
        return getEntityManager().createQuery("from LoginEntity where username = :username",LoginEntity.class)
                .setParameter("username",username)
                .getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public boolean isEmpty() {
        long count = getEntityManager().createQuery("select count(*) as count from LoginEntity",Long.class).getSingleResult();
        return count == 0;
    }

    @Override
    public boolean userExists(String username) {
        long count = getEntityManager().createQuery("select count(*) as count from LoginEntity where username = :username",Long.class)
                .setParameter("username",username)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean deleteUser(String username) {
        LoginEntity loginEntity = findByUsername(username);
        if(Objects.nonNull(loginEntity)){
            Assert.isTrue(!loginEntity.isSuperUser(),"不允许删除超级用户");
            getEntityManager().remove(loginEntity);
        }
        return true;
    }

    @Override
    public void enable(LoginEntity loginEntity) {
        loginEntity.setDisabled(false);
        save(loginEntity);
    }

    @Override
    public void disable(LoginEntity loginEntity) {
        loginEntity.setDisabled(true);
        save(loginEntity);
    }


}
