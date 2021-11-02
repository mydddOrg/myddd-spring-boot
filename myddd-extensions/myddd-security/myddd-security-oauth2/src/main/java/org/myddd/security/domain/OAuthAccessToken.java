package org.myddd.security.domain;

import org.myddd.domain.BaseEntity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "oauth_access_token")
public class OAuthAccessToken extends BaseEntity {

    @Id
    @Column(name = "authentication_id",nullable = false)
    private String authenticationId;

    @Column(name = "token_id")
    private String tokenId;

    @Lob
    @Column(name="token")
    private byte[] token;;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    @Lob
    private byte[] authentication;

    @Column(name = "refresh_token")
    private String refreshToken;


    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }


    @Override
    public Serializable getId() {
        return authenticationId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
