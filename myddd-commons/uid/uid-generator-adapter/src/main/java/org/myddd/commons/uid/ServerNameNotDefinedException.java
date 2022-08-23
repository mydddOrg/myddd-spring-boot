package org.myddd.commons.uid;

import org.myddd.lang.BusinessException;

public class ServerNameNotDefinedException extends BusinessException {
    public ServerNameNotDefinedException() {
        super(WorkIDErrorCode.SERVER_NAME_NOT_DEFINED);
    }
}
