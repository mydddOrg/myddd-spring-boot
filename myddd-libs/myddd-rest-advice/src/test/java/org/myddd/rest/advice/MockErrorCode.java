package org.myddd.rest.advice;

import org.myddd.lang.ErrorCode;

public enum MockErrorCode implements ErrorCode {

    MEDIA_NOT_FOUND {
        @Override
        public int errorStatus() {
            return 101010;
        }
    },

    FILE_NOT_FOUND {
        @Override
        public int errorStatus() {
            return 10020;
        }
    },

    BAD_PARAMETER {
        @Override
        public int errorStatus() {
            return 10030;
        }
    },

    BAD_EMAIL {
        @Override
        public int errorStatus() {
            return 10040;
        }
    }
}
