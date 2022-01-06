package org.myddd.lang;

import java.util.Arrays;

public class BadParameterException extends RuntimeException{

    private final ErrorCode errorCode;

    private final String[] data;

    public BadParameterException(){
        this.errorCode = BadParameterError.BAD_PARAMETER_ERROR;
        this.data = new String[]{};
    }

    public BadParameterException(String... data){
        this.errorCode = BadParameterError.BAD_PARAMETER_ERROR;
        this.data = Arrays.stream(data).toArray(String[]::new);
    }

    public BadParameterException(ErrorCode errorCode){
        super(errorCode.errorCode());
        this.errorCode = errorCode;
        this.data = new String[]{};
    }

    public BadParameterException(ErrorCode errorCode, String... data){
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
