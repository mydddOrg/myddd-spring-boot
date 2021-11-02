package org.myddd.lang;

/**
 * 异常处理码
 */
public interface ErrorCode {

    default int errorStatus() {
        return -1;
    }

    default String errorCode(){
        return this.toString();
    }

}
