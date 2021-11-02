package org.myddd.lang;

import java.util.Arrays;

public class BusinessException extends RuntimeException implements BusinessError {

    private ErrorCode errorCode;

    private String[] data = new String[]{};

    public BusinessException(ErrorCode errorCode){
        super(errorCode.errorCode());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode,String... data){
        super(errorCode.errorCode() + "," + String.join(",", data));
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
