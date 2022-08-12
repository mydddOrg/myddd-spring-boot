package org.myddd.rest.advice.mock;

import org.myddd.lang.BusinessException;

public class EchoException extends BusinessException {
    public EchoException() {
        super(EchoError.ERROR_ONE,"hello","world");
    }
}
