package org.myddd.rest.advice;

import com.google.common.base.Strings;
import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import org.myddd.grpc.GrpcRunner;
import org.myddd.lang.BadParameterException;
import org.myddd.lang.BusinessException;
import org.myddd.lang.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@ControllerAdvice()
public class GrpcExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(GrpcExceptionAdvice.class);
    private static final String X_LANGUAGE = "X-LANGUAGE";


    @ExceptionHandler(value = StatusRuntimeException.class)
    public ResponseEntity<ErrorResponse> grpcExceptionHandler(StatusRuntimeException exception,WebRequest request){
        logger.error(exception.getMessage(),exception);

        var metadata = exception.getTrailers();
        if(Objects.isNull(metadata) || !Boolean.parseBoolean(metadata.get(GrpcRunner.KEY_IS_BUSINESS_EXCEPTION))){
           return exceptionHandler(exception);
        }
        return ResponseEntity.badRequest().body(buildErrorResponse(metadata,request));
    }

    private ErrorResponse buildErrorResponse(Metadata metadata,WebRequest request){
        var errorStatus = metadata.get(GrpcRunner.KEY_ERROR_STATUS);
        var errorCode= metadata.get(GrpcRunner.KEY_ERROR_CODE);
        var errorDataString = metadata.get(GrpcRunner.KEY_ERROR_DATA);

        var builder = ErrorResponse.newBuilder();

        builder.setLanguage(request.getHeader(X_LANGUAGE));
        if(!Strings.isNullOrEmpty(errorStatus))builder.setErrorStatus(Integer.valueOf(errorStatus));
        if(!Strings.isNullOrEmpty(errorCode))builder.setErrorCode(errorCode);
        if(Objects.nonNull(errorDataString)){
            var params = new String(errorDataString, StandardCharsets.UTF_8).split(",");
            builder.setParams(params);
        }
        return builder.build();
    }

    private ResponseEntity<ErrorResponse> exceptionHandler(Exception exception){
        logger.error(exception.getMessage(),exception);
        return ResponseEntity.status(500).body(ErrorResponse.badRequest(exception.getMessage()).build());
    }


}
