package org.myddd.commons.uid;

import org.myddd.lang.BusinessException;

public class ServerPortNotDefinedException extends BusinessException {

    public ServerPortNotDefinedException() {
        super(WorkIDErrorCode.SERVER_PORT_NOT_DEFINED);
    }
}
