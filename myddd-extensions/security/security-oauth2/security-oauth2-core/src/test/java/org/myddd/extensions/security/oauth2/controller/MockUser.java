package org.myddd.extensions.security.oauth2.controller;

public class MockUser {

    private Long userId;

    public MockUser(Long userId) {
        this.userId = userId;
    }

    public MockUser(){

    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
