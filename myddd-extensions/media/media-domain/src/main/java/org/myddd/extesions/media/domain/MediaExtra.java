package org.myddd.extesions.media.domain;

import java.io.Serializable;

public abstract class MediaExtra implements Serializable {

    protected String md5Hex;

    public abstract MediaType mediaType();

    public void setMd5Hex(String md5Hex) {
        this.md5Hex = md5Hex;
    }
}
