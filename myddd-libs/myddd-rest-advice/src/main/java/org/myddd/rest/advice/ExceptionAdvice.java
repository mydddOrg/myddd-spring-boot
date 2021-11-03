package org.myddd.rest.advice;

import org.apache.dubbo.common.serialize.protobuf.support.ProtobufWrappedException;
import org.myddd.lang.BusinessException;
import org.myddd.lang.ErrorResponse;
import org.myddd.lang.BadParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;


@ControllerAdvice()
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    private static final String MYDDD_BUSINESS_EXCEPTION = "org.myddd.lang.BusinessException";

    private static final String SPLIT = ",";

    @ExceptionHandler(value = BadParameterException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BadParameterException exception){
        logger.error(exception.getMessage(),exception);
        return ResponseEntity.status(401).body(
                ErrorResponse
                        .newBuilder()
                        .setErrorCode(exception.getErrorCode().errorCode())
                        .setErrorStatus(exception.getErrorCode().errorStatus())
                        .setParams(exception.getData())
                        .build()
        );
    }


    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException exception){
        logger.error(exception.getMessage(),exception);
        return ResponseEntity.badRequest().body(
                ErrorResponse
                        .newBuilder()
                        .setErrorCode(exception.getErrorCode().errorCode())
                        .setErrorStatus(exception.getErrorCode().errorStatus())
                        .setParams(exception.getData())
                        .build()
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception){
        logger.error(exception.getMessage(),exception);
        return ResponseEntity.status(500).body(ErrorResponse.badRequest(exception.getMessage()).build());
    }

    @ExceptionHandler(value = ProtobufWrappedException.class)
    public ResponseEntity<ErrorResponse> protobufWrappedExceptionHandler(ProtobufWrappedException exception){
        logger.error(exception.getMessage(),exception);
        String exceptionOriginalClassName = exception.getOriginalClassName();
        if(MYDDD_BUSINESS_EXCEPTION.equals(exceptionOriginalClassName)){
            String message = exception.getOriginalMessage();
            String[] params = message.split(SPLIT);
            return ResponseEntity.badRequest().body(
                    ErrorResponse
                            .newBuilder()
                            .setErrorCode(params[0])
                            .setParams(Arrays.copyOfRange(params,1,params.length - 1))
                            .build()
            );
        }
        return ResponseEntity.status(500).body(ErrorResponse.badRequest(exception.getMessage()).build());
    }
}
