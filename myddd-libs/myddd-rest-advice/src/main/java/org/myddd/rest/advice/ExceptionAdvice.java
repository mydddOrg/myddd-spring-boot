package org.myddd.rest.advice;

import org.myddd.lang.BadParameterException;
import org.myddd.lang.BusinessException;
import org.myddd.lang.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice()
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);
    private static final String X_LANGUAGE = "X-LANGUAGE";

    @ExceptionHandler(value = BadParameterException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BadParameterException exception, WebRequest request){
        logger.error(exception.getMessage(),exception);
        return ResponseEntity.status(401).body(
                ErrorResponse
                        .newBuilder()
                        .setErrorCode(exception.getErrorCode().errorCode())
                        .setErrorStatus(exception.getErrorCode().errorStatus())
                        .setLanguage(request.getHeader(X_LANGUAGE))
                        .setParams(exception.getData())
                        .build()
        );
    }


    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException exception,WebRequest request){
        logger.error(exception.getMessage(),exception);
        return ResponseEntity.badRequest().body(
                ErrorResponse
                        .newBuilder()
                        .setErrorCode(exception.getErrorCode().errorCode())
                        .setErrorStatus(exception.getErrorCode().errorStatus())
                        .setLanguage(request.getHeader(X_LANGUAGE))
                        .setParams(exception.getData())
                        .build()
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception){
        logger.error(exception.getMessage(),exception);
        return ResponseEntity.status(500).body(ErrorResponse.badRequest(exception.getMessage()).build());
    }

}
