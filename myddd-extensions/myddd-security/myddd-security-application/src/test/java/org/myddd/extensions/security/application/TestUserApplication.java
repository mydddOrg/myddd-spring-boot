package org.myddd.extensions.security.application;


import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.myddd.extensions.security.AbstractTest;
import org.myddd.extensions.security.UserNameEmptyException;
import org.myddd.extensions.security.api.PageQueryDto;
import org.myddd.extensions.security.api.PageUserDto;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;

import javax.inject.Inject;

class TestUserApplication extends AbstractTest {

    @Inject
    private UserApplication userApplication;

    @Test
    void testUserApplicationExist(){
        Assertions.assertNotNull(userApplication);
    }

    @Test
    void testCreateUser(){
        UserDto created = userApplication.createLocalUser(randomUserDto());
        Assertions.assertNotNull(created);

        UserDto nameEmptyUserDto = UserDto.newBuilder().build();
        Assertions.assertThrows(UserNameEmptyException.class,()->{
            userApplication.createLocalUser(nameEmptyUserDto);
        });
    }


    @Test
    void testQueryUser(){
        UserDto notExists = userApplication.queryUserById(Int64Value.of(-1L));
        Assertions.assertNull(notExists);

        UserDto created = userApplication.createLocalUser(randomUserDto());
        Assertions.assertNotNull(created);
        UserDto query = userApplication.queryUserById(Int64Value.of(created.getId()));
        Assertions.assertNotNull(query);
    }

    @Test
    void testQueryLocalUserByUserId(){
        UserDto created = userApplication.createLocalUser(randomUserDto());
        Assertions.assertNotNull(created);

        Assertions.assertNotNull(userApplication.queryLocalUserByUserId(StringValue.of(created.getEmail())));
    }

    @Test
    void testPageQueryUser(){
        UserDto created = userApplication.createLocalUser(randomUserDto());
        Assertions.assertNotNull(created);

        PageUserDto pageUserDto = userApplication.pageQueryUser(PageQueryDto
                .newBuilder()
                .setPageSize(10)
                .build());

        Assertions.assertFalse(pageUserDto.getUsersList().isEmpty());
    }

    @Test
    void testEnableAndDisableUser(){
        var emptyUserDto = UserDto.newBuilder().build();
        Assertions.assertThrows(UserNameEmptyException.class,()-> userApplication.createLocalUser(emptyUserDto));

        UserDto created = userApplication.createLocalUser(randomUserDto());
        Assertions.assertFalse(created.getDisabled());


        userApplication.disableUser(UserDto.newBuilder().setId(created.getId()).build());

        UserDto query = userApplication.queryUserById(Int64Value.of(created.getId()));
        Assertions.assertTrue(query.getDisabled());

        userApplication.enableUser(UserDto.newBuilder().setId(query.getId()).build());
        UserDto anotherQuery = userApplication.queryUserById(Int64Value.of(created.getId()));
        Assertions.assertFalse(anotherQuery.getDisabled());
    }

}
