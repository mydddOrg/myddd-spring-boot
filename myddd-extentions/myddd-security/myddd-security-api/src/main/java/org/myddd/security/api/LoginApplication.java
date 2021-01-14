package org.myddd.security.api;

import org.myddd.utils.Page;

public interface LoginApplication {

    LoginDTO createSupper(LoginDTO loginDTO);

    LoginDTO createUser(LoginDTO loginDTO);

    LoginDTO updateUser(LoginDTO loginDTO);

    LoginDTO queryLogin(String username);

    boolean isEmpty();

    boolean updatePassword(LoginDTO loginDTO);

    boolean updatePasswordByAdmin(LoginDTO loginDTO);

    boolean deleteUser(String username);

    boolean enableUser(String username);

    boolean disableUser(String username);

    Page<LoginDTO> pageListLogin(int page,int pageSize,String search);

}
