package org.myddd.lang;

public enum BadParameterError implements ErrorCode{

    BAD_PARAMETER_ERROR {
        @Override
        public String errorCode() {
            return "BAD PARAMETER";
        }
    }
}
