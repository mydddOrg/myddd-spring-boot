package org.myddd.extesions.media;

import org.myddd.lang.BusinessException;

public class MediaUploadFailedException extends BusinessException {

    public MediaUploadFailedException(String... data){
        super(MediaDomainError.MEDIA_UPLOAD_FAILED,data);
    }
}
