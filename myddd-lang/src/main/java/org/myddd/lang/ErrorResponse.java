package org.myddd.lang;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class ErrorResponse {

    private int errorStatus;

    private String errorCode;

    private String errorMsg;

    private static Properties configProperties;

    private static final String ERROR_MSG_PROPERTIES = "error_msg.properties";

    private static Properties getProperties(){
        if(Objects.isNull(configProperties)){
            loadProperties();
        }
        return configProperties;
    }

    private static void loadProperties(){
        configProperties = new Properties();
        try {
            InputStream inputStream = ErrorResponse.class.getClassLoader().getResourceAsStream(ERROR_MSG_PROPERTIES);
            configProperties.load(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ErrorResponse buildFromRpcMessage(String message){
        ErrorResponse errorResponse = new ErrorResponse();
        String params[] = message.split(",");
        errorResponse.errorCode = params[0];
        if(getProperties().containsKey(errorResponse.errorCode)){
            String msgString = getProperties().getProperty(errorResponse.errorCode);
            errorResponse.errorMsg = String.format(msgString, (Object[]) Arrays.copyOfRange(params,1,params.length - 1));
        }
        return errorResponse;
    }

    public static ErrorResponse buildFromException(IBusinessError exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorCode = exception.getErrorCode().errorCode();
        errorResponse.errorStatus = exception.getErrorCode().errorStatus();
        if(getProperties().containsKey(errorResponse.errorCode)){
            String msgString = getProperties().getProperty(errorResponse.errorCode);
            errorResponse.errorMsg = String.format(msgString, (Object[]) exception.getData());
        }

        return errorResponse;
    }

    public static ErrorResponse buildFromException(BadParameterException exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.errorCode = exception.getErrorCode().errorCode();
        errorResponse.errorStatus = exception.getErrorCode().errorStatus();
        if(getProperties().containsKey(errorResponse.errorCode)){
            String msgString = getProperties().getProperty(errorResponse.errorCode);
            errorResponse.errorMsg = String.format(msgString, (Object[]) exception.getData());
        }
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
