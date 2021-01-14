package org.myddd.security.domain;

import org.myddd.domain.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oauth_refresh_token")
public class OAuthRefreshToken extends BaseEntity {
    @Id
    @Column(name = "token_id")
    private String tokenId;

    @Lob
    @Column(name="token")
    private byte[] token;

    @Lob
    private byte[] authentication;

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

    public byte[] getAuthentication() {
        return authentication;
    }

    public void setAuthentication(byte[] authentication) {
        this.authentication = authentication;
    }

    @Override
    public Serializable getId() {
        return tokenId;
    }
}
