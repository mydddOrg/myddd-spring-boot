package org.myddd.lang;

public class ErrorResponse {

    private int errorStatus;

    private String errorCode;

    private String errorMsg;

    public static ErrorResponse buildFromException(BusinessException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorCode = exception.getErrorCode().errorCode();
        errorResponse.errorStatus = exception.getErrorCode().errorStatus();
        return errorResponse;
    }

    public static ErrorResponse badRequest(Exception e){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorStatus = -1;
        errorResponse.errorCode = "BAD REQUEST";
        errorResponse.errorMsg = e.getMessage();
        return errorResponse;
    }

    public int getErrorStatus() {
        return errorStatus;
    }

    public void setErrorStatus(int errorStatus) {
        this.errorStatus = errorStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
