package org.myddd.lang;

/**
 * 异常处理码
 */
public interface ErrorCode {

    String UNKNOWN_ERROR = "Unknown Error";

    default int errorStatus() {
        return -1;
    }

    default String errorCode(){
        return this.toString();
    }
}
