package org.myddd.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Objects;
import java.util.function.Supplier;

public class GrpcRunner {

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
        catch (Exception e){
            var error= Status.INTERNAL.withDescription(e.getLocalizedMessage());
            responseObserver.onError(new StatusRuntimeException(error));
        }
    }
}
