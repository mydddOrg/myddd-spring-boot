package org.myddd.rest.advice;

import org.apache.dubbo.common.serialize.protobuf.support.ProtobufWrappedException;
import org.myddd.lang.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;


@ControllerAdvice()
public class DubboRPCExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(DubboRPCExceptionAdvice.class);

    private static final String MYDDD_BUSINESS_EXCEPTION = "org.myddd.lang.BusinessException";

    private static final String SPLIT = ",";

    private static final String X_LANGUAGE = "X-LANGUAGE";

    @ExceptionHandler(value = ProtobufWrappedException.class)
    public ResponseEntity<ErrorResponse> protobufWrappedExceptionHandler(ProtobufWrappedException exception, WebRequest request){
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
                            .setLanguage(request.getHeader(X_LANGUAGE))
                            .build()
            );
        }
        return ResponseEntity.status(500).body(ErrorResponse.badRequest(exception.getMessage()).build());
    }
}
