package org.myddd.extesions.media.local;

import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaType;

public class LocalMediaExtra extends MediaExtra {

    private final String path;

    public LocalMediaExtra(String md5Hex,String path){
        this.path = path;
        this.md5Hex = md5Hex;
    }

    public String destPath() {
        return path;
    }

    @Override
    public MediaType mediaType() {
        return MediaType.LOCAL_FILE;
    }
}
