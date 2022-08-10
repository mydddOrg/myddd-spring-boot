package org.myddd.extensions.media.application.grpc;

import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.myddd.domain.InstanceFactory;
import org.myddd.extensions.media.api.MediaApplication;
import org.myddd.extensions.media.api.MediaApplicationGrpc;
import org.myddd.extensions.media.api.MediaByte;
import org.myddd.extensions.media.api.MediaDTO;
import org.myddd.grpc.GrpcRunner;

import java.util.Objects;

public class MediaApplicationGrpcProxy extends MediaApplicationGrpc.MediaApplicationImplBase {

    private static MediaApplication mediaApplication;

    private static MediaApplication getMediaApplication(){
        if(Objects.isNull(mediaApplication)){
            mediaApplication = InstanceFactory.getInstance(MediaApplication.class);
        }
        return mediaApplication;
    }

    @Override
    public void createMedia(MediaByte request, StreamObserver<MediaDTO> responseObserver) {
        GrpcRunner.run(responseObserver,()->getMediaApplication().createMedia(request));
    }

    @Override
    public void queryMedia(StringValue request, StreamObserver<MediaByte> responseObserver) {
        GrpcRunner.run(responseObserver,()->getMediaApplication().queryMedia(request));
    }

    @Override
    public void queryMediaIdByDigest(StringValue request, StreamObserver<MediaDTO> responseObserver) {
        GrpcRunner.run(responseObserver,()->getMediaApplication().queryMediaIdByDigest(request));
    }
}
