package org.myddd.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;

public class ErrorResponse {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResponse.class);

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
            var errorResponse = new ErrorResponse();
            errorResponse.errorCode = errorCode;
            errorResponse.errorStatus = errorStatus;
            errorResponse.errorMsg = errorMsg;
            if(getProperties().containsKey(errorResponse.errorCode)){
                var msgString = getProperties().getProperty(errorResponse.errorCode);
                errorResponse.errorMsg = String.format(msgString, (Object[])params);
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
            try(var inputStream = ErrorResponse.class.getClassLoader().getResourceAsStream(ERROR_MSG_PROPERTIES)) {
                configProperties.load(inputStream);
            }catch (Exception e){
                logger.warn(e.getMessage(),e);
            }
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Integer getErrorStatus() {
        return errorStatus;
    }
}
