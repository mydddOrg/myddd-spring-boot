package org.myddd.lang;

import java.util.Arrays;

public class RPCWrapperException extends Throwable implements IBusinessError{
    private ErrorCode errorCode;

    private String[] data = new String[]{};

    public RPCWrapperException(ErrorCode errorCode,String... data){
        super(errorCode.errorCode());
        this.errorCode = errorCode;
        this.data = Arrays.stream(data).toArray(String[]::new);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String[] getData() {
        return data;
    }
}
