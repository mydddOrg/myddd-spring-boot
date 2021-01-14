package org.myddd.security.account.application;

import org.apache.logging.log4j.util.Strings;
import org.myddd.domain.InstanceFactory;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.security.account.domain.LoginEntity;
import org.myddd.security.account.MD5PasswordEncrypt;
import org.myddd.security.account.application.assembler.LoginAssembler;
import org.myddd.security.api.LoginApplication;
import org.myddd.security.api.LoginDTO;
import org.myddd.security.api.PasswordEncrypt;
import org.myddd.utils.Assert;
import org.myddd.utils.Page;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class LoginApplicationImpl implements LoginApplication {

    private PasswordEncrypt passwordEncrypt;

    @Inject
    private LoginAssembler loginAssembler;

    @Inject
    private QueryChannelService queryChannelService;

    private PasswordEncrypt getPasswordEncrypt() {
        if (Objects.isNull(passwordEncrypt)) {
            passwordEncrypt = InstanceFactory.getInstanceWithDefault(PasswordEncrypt.class, new MD5PasswordEncrypt());
        }
        return passwordEncrypt;
    }

    @Transactional
    public LoginDTO createUser(LoginDTO loginDTO) {
        Assert.notNull(loginDTO.getUsername(),"用户名不能为空");
        Assert.notNull(loginDTO.getPassword(),"密码不能为空");
        Assert.isFalse(LoginEntity.exists(loginDTO.getUsername()),"用户已存在");

        loginDTO.setPassword(getPasswordEncrypt().encrypt(loginDTO.getPassword()));

        LoginEntity user = loginAssembler.toEntity(loginDTO);
        user.createUser();

        return loginAssembler.toDTOWithoutPassword(user);
    }

    @Override
    @Transactional
    public LoginDTO updateUser(LoginDTO loginDTO) {
        LoginEntity user = loginAssembler.toEntity(loginDTO);
        LoginEntity updatedUser = user.updateUser();

        return loginAssembler.toDTOWithoutPassword(updatedUser);

    }


    @Override
    @Transactional
    public LoginDTO createSupper(LoginDTO loginDTO) {
        Assert.notNull(loginDTO.getUsername(),"用户名不能为空");
        Assert.notNull(loginDTO.getPassword(),"密码不能为空");
        Assert.isFalse(LoginEntity.exists(loginDTO.getUsername()),"用户已存在");

        loginDTO.setPassword(getPasswordEncrypt().encrypt(loginDTO.getPassword()));

        LoginEntity superUser = loginAssembler.toEntity(loginDTO);
        superUser.createSuperUser();

        return loginAssembler.toDTOWithoutPassword(superUser);

    }

    @Override
    public LoginDTO queryLogin(String username) {
        LoginEntity loginEntity = LoginEntity.findByUsername(username);
        return loginAssembler.toDTO(loginEntity);
    }

    @Override
    public boolean isEmpty() {
        return LoginEntity.isEmpty();
    }

    @Override
    @Transactional
    public boolean updatePassword(LoginDTO loginDTO) {
        loginDTO.setPassword(getPasswordEncrypt().encrypt(loginDTO.getPassword()));
        loginDTO.setNewPassword(getPasswordEncrypt().encrypt(loginDTO.getNewPassword()));
        return LoginEntity.updatePassword(loginDTO.getUsername(), loginDTO.getPassword(), loginDTO.getNewPassword());
    }

    @Override
    @Transactional
    public boolean updatePasswordByAdmin(LoginDTO loginDTO) {
        loginDTO.setNewPassword(getPasswordEncrypt().encrypt(loginDTO.getNewPassword()));
        return LoginEntity.updatePasswordByAdmin(loginDTO.getUsername(),loginDTO.getNewPassword());
    }

    @Override
    @Transactional
    public boolean deleteUser(String username) {
        LoginEntity.delete(username);
        return true;
    }

    @Override
    @Transactional
    public boolean enableUser(String username) {
        Assert.notNull(username,"用户名不能为空");
        LoginEntity loginEntity = LoginEntity.findByUsername(username);
        Assert.notNull(loginEntity,"找不到指定用户:"+username);

        loginEntity.enabled();
        return true;
    }

    @Override
    @Transactional
    public boolean disableUser(String username) {
        Assert.notNull(username,"用户名不能为空");
        LoginEntity loginEntity = LoginEntity.findByUsername(username);
        Assert.notNull(loginEntity,"找不到指定用户:"+username);

        loginEntity.disabled();
        return true;
    }

    @Override
    public Page<LoginDTO> pageListLogin(int page, int pageSize, String search) {
        String querySQL;
        List<Object> params = new ArrayList<>();
        if (Strings.isEmpty(search)) {
            querySQL = "from LoginEntity le order by le.createDate DESC";
        } else {
            querySQL = "from LoginEntity le where le.username like ?1 or le.displayName like ?1 order by le.createDate DESC";
            params.add("%" + search + "%");
        }
        Page<LoginEntity> inventoryItemDTOPage = this.queryChannelService
                .createJpqlQuery(querySQL)
                .setParameters(params)
                .setPage(page, pageSize)
                .pagedList();

        return Page.builder()
                .stat(inventoryItemDTOPage.getStart())
                .totalSize(inventoryItemDTOPage.getResultCount())
                .pageSize(inventoryItemDTOPage.getPageSize())
                .data(inventoryItemDTOPage.getData().stream().map(it -> loginAssembler.toDTO(it)).collect(Collectors.toList()));
    }
}
