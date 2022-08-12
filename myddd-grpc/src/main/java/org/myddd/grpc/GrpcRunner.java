package org.myddd.grpc;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.myddd.lang.BusinessException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.function.Supplier;

public class GrpcRunner {

    protected static final Metadata.Key<String> KEY_ERROR_STATUS = Metadata.Key.of("error-status",Metadata.ASCII_STRING_MARSHALLER);
    protected static final Metadata.Key<String> KEY_ERROR_CODE = Metadata.Key.of("error-code",Metadata.ASCII_STRING_MARSHALLER);
    protected static final Metadata.Key<byte[]> KEY_ERROR_DATA = Metadata.Key.of("error-data-bin",Metadata.BINARY_BYTE_MARSHALLER);
    protected static final Metadata.Key<String> KEY_IS_BUSINESS_EXCEPTION = Metadata.Key.of("is-business-exception",Metadata.ASCII_STRING_MARSHALLER);

    public static <T> void run(StreamObserver<T> responseObserver, Supplier<T> supplier){
        try{
            var result = supplier.get();
            if(Objects.isNull(result)){
                responseObserver.onError(new StatusRuntimeException(Status.DATA_LOSS));
                return;
            }
            responseObserver.onNext(result);
            responseObserver.onCompleted();
        }
        catch (BusinessException e){
            var error= Status.INTERNAL.withDescription(e.getLocalizedMessage());
            responseObserver.onError(new StatusRuntimeException(error,buildMetadata(e)));
        }
        catch (Exception e){
            var error= Status.INTERNAL.withDescription(e.getLocalizedMessage());
            responseObserver.onError(new StatusRuntimeException(error));
        }
    }

    private static Metadata buildMetadata(BusinessException e){
        var trailers = new Metadata();
        trailers.put(KEY_ERROR_CODE,e.getErrorCode().errorCode());
        trailers.put(KEY_ERROR_STATUS,String.valueOf(e.getErrorCode().errorStatus()));
        trailers.put(KEY_ERROR_DATA,String.join(",",e.getData()).getBytes(StandardCharsets.UTF_8));
        trailers.put(KEY_IS_BUSINESS_EXCEPTION,String.valueOf(true));

        return trailers;
    }
}
