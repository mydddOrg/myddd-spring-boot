package org.myddd.security.account.infra;

import org.myddd.domain.AbstractRepositoryJPA;
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
        return getEntityRepository().save(loginEntity);
    }

    @Override
    public LoginEntity updateUser(LoginEntity loginEntity) {
        Assert.notNull(loginEntity.getId());
        loginEntity.setUpdateDate(System.currentTimeMillis());
        return getEntityRepository().save(loginEntity);
    }

    @Override
    public LoginEntity findByUsername(String username) {
        return getEntityRepository().createCriteriaQuery(LoginEntity.class)
                .eq("username",username)
                .singleResult();
    }

    @Override
    public boolean isEmpty() {
        long count = getEntityRepository().createJpqlQuery("select count(*) as count from LoginEntity").singleResult();
        return count == 0;
    }

    @Override
    public boolean userExists(String username) {
        long count = getEntityRepository().createJpqlQuery("select count(*) as count from LoginEntity where username = ?1")
                .setParameters(username).singleResult();
        return count > 0;
    }

    @Override
    public boolean deleteUser(String username) {
        LoginEntity loginEntity = findByUsername(username);
        if(Objects.nonNull(loginEntity)){
            Assert.isTrue(!loginEntity.isSuperUser(),"不允许删除超级用户");
            getEntityRepository().remove(loginEntity);
        }
        return true;
    }

    @Override
    public void enable(LoginEntity loginEntity) {
        loginEntity.setDisabled(false);
        getEntityRepository().save(loginEntity);
    }

    @Override
    public void disable(LoginEntity loginEntity) {
        loginEntity.setDisabled(true);
        getEntityRepository().save(loginEntity);
    }


}
