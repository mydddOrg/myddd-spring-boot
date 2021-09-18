package org.myddd.rest.advice;

import org.apache.dubbo.common.serialize.protobuf.support.ProtobufWrappedException;
import org.myddd.lang.BusinessException;
import org.myddd.lang.ErrorResponse;
import org.myddd.lang.BadParameterException;
import org.myddd.lang.RPCWrapperException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice()
public class ExceptionAdvice {

    private static final String MYDDD_BUSINESS_EXCEPTION = "org.myddd.lang.BusinessException";

    @ExceptionHandler(value = BadParameterException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BadParameterException exception){
        exception.printStackTrace();
        return ResponseEntity.status(401).body(ErrorResponse.buildFromException(exception));
    }


    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> businessExceptionHandler(BusinessException exception){
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(ErrorResponse.buildFromException(exception));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception){
        exception.printStackTrace();
        return ResponseEntity.status(500).body(ErrorResponse.badRequest(exception));
    }

    @ExceptionHandler(value = ProtobufWrappedException.class)
    public ResponseEntity<ErrorResponse> protobufWrappedExceptionHandler(ProtobufWrappedException exception){
        String exceptionOriginalClassName = exception.getOriginalClassName();
        if(MYDDD_BUSINESS_EXCEPTION.equals(exceptionOriginalClassName)){
            String message = exception.getOriginalMessage();
            return ResponseEntity.badRequest().body(ErrorResponse.buildFromRpcMessage(message));
        }
        return ResponseEntity.status(500).body(ErrorResponse.badRequest(exception));
    }
}
