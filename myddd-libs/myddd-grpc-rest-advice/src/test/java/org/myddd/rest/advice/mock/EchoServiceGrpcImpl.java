package org.myddd.rest.advice.mock;

import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.myddd.grpc.GrpcRunner;
import org.myddd.grpc.api.EchoDto;
import org.myddd.grpc.api.EchoServiceGrpc;
import org.myddd.grpc.api.EchoType;

public class EchoServiceGrpcImpl extends EchoServiceGrpc.EchoServiceImplBase {

    @Override
    public void hello(Empty request, StreamObserver<StringValue> responseObserver) {
        GrpcRunner.run(responseObserver,()-> StringValue.of(String.valueOf(System.currentTimeMillis())));
    }

    @Override
    public void echo(EchoDto request, StreamObserver<Empty> responseObserver) {

        GrpcRunner.run(responseObserver,()->{
            if(request.getType() == EchoType.Exception){
                throw new EchoException();
            }
            return Empty.getDefaultInstance();
        });
    }
}
