package org.myddd.extesions.media.qcloud;

import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaType;

public class QCloudMediaExtra extends MediaExtra {

    private String filePath;

    public QCloudMediaExtra(String filePath){
        this.filePath = filePath;
    }

    @Override
    public MediaType mediaType() {
        return MediaType.Q_CLOUD;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
