package org.myddd.grpc;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.myddd.lang.BusinessException;

import java.util.Objects;
import java.util.function.Supplier;

public class GrpcRunner {

    public static final Metadata.Key<String> KEY_ERROR_CODE = Metadata.Key.of("error-code",Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<byte[]> KEY_ERROR_DATA = Metadata.Key.of("error-data-bin",Metadata.BINARY_BYTE_MARSHALLER);
    public static final Metadata.Key<String> KEY_IS_BUSINESS_EXCEPTION = Metadata.Key.of("is-business-exception",Metadata.ASCII_STRING_MARSHALLER);

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
            var trailers = new Metadata();
            trailers.put(KEY_ERROR_CODE,e.getErrorCode().toString());
            trailers.put(KEY_ERROR_DATA,String.join(",",e.getData()).getBytes());
            trailers.put(KEY_IS_BUSINESS_EXCEPTION,String.valueOf(true));

            var error= Status.INTERNAL.withDescription(e.getLocalizedMessage());
            responseObserver.onError(new StatusRuntimeException(error,trailers));
        }
        catch (Exception e){
            var error= Status.INTERNAL.withDescription(e.getLocalizedMessage());
            responseObserver.onError(new StatusRuntimeException(error));
        }
    }
}
