package org.myddd.extesions.media;

import org.myddd.lang.BusinessException;

public class MediaNotFoundException extends BusinessException {
    public MediaNotFoundException(){
        super(MediaDomainError.MEDIA_NOT_FOUND);
    }
}
