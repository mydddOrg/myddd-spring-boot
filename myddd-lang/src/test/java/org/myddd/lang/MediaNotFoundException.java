package org.myddd.lang;

public class MediaNotFoundException extends BusinessException{

    public MediaNotFoundException() {
        super(MockErrorCode.MOCK_ERROR_CODE);
    }
}
