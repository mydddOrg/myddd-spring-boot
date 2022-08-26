package org.myddd.extensions.security.controller;

import com.google.protobuf.Int64Value;
import org.myddd.commons.verification.EmailVerificationCodeApplication;
import org.myddd.commons.verification.MobileVerificationCodeApplication;
import org.myddd.extensions.security.IAuthentication;
import org.myddd.extensions.security.UserNotExistsException;
import org.myddd.extensions.security.UserVO;
import org.myddd.extensions.security.api.PageQueryDto;
import org.myddd.extensions.security.api.PageUserDto;
import org.myddd.extensions.security.api.UserApplication;
import org.myddd.extensions.security.api.UserDto;
import org.myddd.utils.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/v1")
public class UserController {

    @Inject
    private IAuthentication authentication;

    @Inject
    private UserApplication userApplication;

    @Inject
    private EmailVerificationCodeApplication emailVerificationCodeApplication;

    @Inject
    private MobileVerificationCodeApplication mobileVerificationCodeApplication;

    @PostMapping("/users")
    public ResponseEntity<UserVO> createLocalUser(@RequestBody UserVO userVO) {
        if (!authentication.isAuthentication()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto created = userApplication.createLocalUser(userVO.toDto());
        return ResponseEntity.ok(UserVO.of(created));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserVO> queryUserById(@PathVariable Long id) {
        if (!authentication.isAuthentication()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDto queryUser = userApplication.queryUserById(Int64Value.of(id));
        if (Objects.isNull(queryUser)) {
            throw new UserNotExistsException();
        }
        return ResponseEntity.ok(UserVO.of(queryUser));
    }

    @GetMapping("/current-login-user/user")
    public ResponseEntity<UserVO> queryCurrentUser() {
        if (!authentication.isAuthentication()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDto queryUser = userApplication.queryUserById(Int64Value.of(authentication.loginUserId()));
        if (Objects.isNull(queryUser)) {
            throw new UserNotExistsException();
        }
        return ResponseEntity.ok(UserVO.of(queryUser));
    }


    @GetMapping("/users")
    public ResponseEntity<Page<UserVO>> pageQueryUser(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int pageSize) {
        if (!authentication.isAuthentication()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        PageUserDto pageUserDto = userApplication.pageQueryUser(
                PageQueryDto.newBuilder()
                        .setPageSize(pageSize)
                        .setPage(page)
                        .build()
        );
        return ResponseEntity.ok(Page.builder(UserVO.class)
                .pageSize(pageSize)
                .start((long) page * pageSize)
                .totalSize(pageUserDto.getTotal())
                .data(pageUserDto.getUsersList().stream().map(UserVO::of).collect(Collectors.toList()))
        );
    }

    @PutMapping("/users/{id}/enable")
    public ResponseEntity<Void> enableUser(@PathVariable Long id) {
        userApplication.enableUser(UserDto.newBuilder()
                .setId(id)
                .build());
        return ResponseEntity.ok().build();
    }


    @PutMapping("/users/{id}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {
        userApplication.disableUser(UserDto.newBuilder()
                .setId(id)
                .build());
        return ResponseEntity.ok().build();
    }

}
