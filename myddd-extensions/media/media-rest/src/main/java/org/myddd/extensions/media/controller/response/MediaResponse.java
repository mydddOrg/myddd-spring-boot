package org.myddd.extensions.media.controller.response;


import org.myddd.extensions.media.api.MediaDTO;

public class MediaResponse {

    private String mediaId;

    private String name;

    private long size;

    public static MediaResponse of(MediaDTO mediaDTO){
        MediaResponse mediaResponse = new MediaResponse();
        mediaResponse.mediaId = mediaDTO.getMediaId();
        return mediaResponse;
    }

    public static MediaResponse of(MediaDTO mediaDTO,String name,long size){
        MediaResponse mediaResponse = new MediaResponse();
        mediaResponse.mediaId = mediaDTO.getMediaId();
        mediaResponse.name = name;
        mediaResponse.size = size;
        return mediaResponse;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
