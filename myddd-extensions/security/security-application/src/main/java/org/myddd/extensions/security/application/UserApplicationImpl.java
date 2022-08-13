package org.myddd.extensions.security.application;


import com.google.protobuf.BoolValue;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.security.api.*;
import org.myddd.extensions.security.application.assembler.UserAssembler;
import org.myddd.extensions.security.domain.User;
import org.myddd.querychannel.QueryChannelService;
import org.myddd.utils.Page;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class UserApplicationImpl implements UserApplication {

    private QueryChannelService queryChannelService;

    private QueryChannelService getQueryChannelService(){
        if(Objects.isNull(queryChannelService)){
            queryChannelService = InstanceFactory.getInstance(QueryChannelService.class);
        }
        return queryChannelService;
    }

    @Override
    @Transactional
    public UserDto createLocalUser(UserDto request) {
        User created = Objects.requireNonNull(UserAssembler.toUser(request)).createLocalUser();
        return UserAssembler.toUserDto(created);
    }

    @Override
    public OptionalUserDto queryLocalUserByUserId(StringValue request) {
        var query = User.queryLocalByEmailOrPhone(request.getValue());
        return UserAssembler.toOptionalUserDto(query);
    }

    @Override
    public UserDto queryUserById(Int64Value request) {
        User user = User.queryUserById(request.getValue());
        return UserAssembler.toUserDto(user);
    }

    @Override
    public PageUserDto pageQueryUser(PageQueryDto request) {
        Page<User> userPage = getQueryChannelService()
                .createJpqlQuery("from User",User.class)
                .setPage(request.getPage(),request.getPageSize())
                .pagedList();

        return PageUserDto.newBuilder()
                .setPage(request.getPage())
                .setPageSize(request.getPageSize())
                .setTotal(userPage.getResultCount())
                .addAllUsers(userPage.getData().stream().map(UserAssembler::toUserDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    @Transactional
    public BoolValue enableUser(UserDto request) {
        User user = UserAssembler.toUser(request);
        Objects.requireNonNull(user).enable();
        return BoolValue.of(true);
    }

    @Override
    @Transactional
    public BoolValue disableUser(UserDto request) {
        User user = UserAssembler.toUser(request);
        Objects.requireNonNull(user).disable();
        return BoolValue.of(true);
    }

}
