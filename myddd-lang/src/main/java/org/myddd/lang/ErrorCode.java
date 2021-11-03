package org.myddd.lang;

import java.io.Serializable;

/**
 * 异常处理码
 */
public interface ErrorCode extends Serializable {

    default int errorStatus() {
        return -1;
    }

    default String errorCode(){
        return this.toString();
    }

}
