package org.myddd.media;

import java.io.Serializable;

public class MediaFile implements Serializable{

    /**
     * 图片文件的byte[]内容
     */
    private byte[] content;

    /**
     * 图片文件名
     */
    private String fileName;


    public MediaFile() {

    }

    public MediaFile(byte[] content, String fileName){
        this.content = content;
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
