package org.myddd.java.api;

import org.myddd.java.api.dto.UserDTO;
import org.myddd.utils.Page;

public interface UserApplication {

    UserDTO createUser(UserDTO userDTO);

    Page<UserDTO> searchUser(int page,int pageSize,String searchKey);
}
