package org.myddd.lang;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class ErrorResponse {

    private static final String BAD_REQUEST = "BAD REQUEST";

    private Integer errorStatus;

    private String errorCode;

    private String errorMsg;

    private static Properties configProperties;

    private static final String ERROR_MSG_PROPERTIES = "error_msg.properties";


    public static Builder newBuilder(){
        return new Builder();
    }

    public static Builder badRequest(String errorMsg){
        return new Builder()
                .setErrorMsg(errorMsg)
                .setErrorCode(BAD_REQUEST);
    }

    public static class Builder {

        private Integer errorStatus;

        private String errorCode;

        private String errorMsg;

        private String[] params;

        private Builder setErrorMsg(String errorMsg){
            this.errorMsg = errorMsg;
            return this;
        }

        public Builder setParams(String[] params){
            this.params = params;
            return this;
        }

        public Builder setErrorStatus(Integer status){
            if(Objects.nonNull(status)){
                this.errorStatus = status;
            }
            return this;
        }

        public Builder setErrorCode(String errorCode){
            this.errorCode = errorCode;
            return this;
        }

        public ErrorResponse build(){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.errorCode = errorCode;
            errorResponse.errorStatus = errorStatus;
            errorResponse.errorMsg = errorMsg;
            if(getProperties().containsKey(errorResponse.errorCode)){
                String msgString = getProperties().getProperty(errorResponse.errorCode);
                errorResponse.errorMsg = String.format(msgString, params);
            }
            return errorResponse;
        }

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
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorStatus() {
        return errorStatus;
    }
}
