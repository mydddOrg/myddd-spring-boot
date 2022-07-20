package org.myddd.extesions.media.gridfs;

import org.myddd.extesions.media.domain.MediaExtra;
import org.myddd.extesions.media.domain.MediaType;

public class GridFSMediaExtra extends MediaExtra {

    private String fileId;

    public GridFSMediaExtra(String fileId,String md5Hex){
        this.fileId = fileId;
        this.md5Hex = md5Hex;
    }

    @Override
    public MediaType mediaType() {
        return MediaType.GRID_FS;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }


}
