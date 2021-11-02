package org.myddd.lang;

import java.util.Arrays;

public class BadParameterException extends RuntimeException{

    private final ErrorCode errorCode;

    private final String[] data;

    private static final String ILLEGAL_ARGUMENT = "BAD PARAMETER";

    public BadParameterException(){
        this.errorCode = new ErrorCode() {
            @Override
            public String errorCode() {
                return ILLEGAL_ARGUMENT;
            }
        };
        this.data = new String[]{};
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
