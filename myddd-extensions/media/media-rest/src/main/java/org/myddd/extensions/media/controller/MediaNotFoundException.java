package org.myddd.extensions.media.controller;

import org.myddd.lang.BusinessException;

public class MediaNotFoundException extends BusinessException {

    public MediaNotFoundException() {
        super(MediaRestError.MEDIA_NOT_FOUND);
    }
}
