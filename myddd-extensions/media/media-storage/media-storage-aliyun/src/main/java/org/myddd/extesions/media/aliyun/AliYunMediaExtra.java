package org.myddd.extesions.media.aliyun;

import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaType;

public class AliYunMediaExtra extends MediaExtra {

    private String filePath;

    public AliYunMediaExtra(String filePath){
        super();
        this.filePath = filePath;
    }

    @Override
    public MediaType mediaType() {
        return MediaType.ALI_YUN;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
