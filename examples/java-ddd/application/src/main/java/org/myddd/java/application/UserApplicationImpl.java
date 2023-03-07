package org.myddd.java.application;

import org.myddd.java.api.UserApplication;
import org.myddd.java.api.dto.UserDTO;
import org.myddd.java.application.assembler.UserAssembler;
import org.myddd.java.domain.User;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.utils.Page;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;

@Named
public class UserApplicationImpl implements UserApplication {

    @Inject
    private QueryChannelService queryChannelService;

    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        var user = UserAssembler.toUser(userDTO);
        var createdUser = user.createUser();
        return UserAssembler.toUserDTO(createdUser);
    }

    @Override
    public Page<UserDTO> searchUser(int page, int pageSize, String searchKey) {
        var pageSearch = queryChannelService
                .createJpqlQuery("from User where name like :name", User.class)
                .addParameter("name","%" + searchKey + "%")
                .setPage(page,pageSize)
                .pagedList();
        return Page.builder(UserDTO.class)
                .start(pageSearch.getStart())
                .totalSize(pageSearch.getResultCount())
                .data(pageSearch.getData().stream().map(UserAssembler::toUserDTO).collect(Collectors.toList()));
    }
}
