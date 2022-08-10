package org.myddd.extensions.media.grpc.bridge;

import com.google.protobuf.StringValue;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.myddd.extensions.media.api.MediaApplication;
import org.myddd.extensions.media.api.MediaApplicationGrpc;
import org.myddd.extensions.media.api.MediaByte;
import org.myddd.extensions.media.api.MediaDTO;

import javax.inject.Named;
import java.util.Objects;

@Named
public class MediaApplicationGrpcBridge implements MediaApplication {

    private static final String mediaGrpcAddress = "dns:///media:8081";

    private static final String LOAD_BALANCING_RR = "round_robin";

    private ManagedChannel managedChannel;

    protected ManagedChannel getManagedChannel(){
        if(Objects.isNull(managedChannel)){
            managedChannel = ManagedChannelBuilder.
                    forTarget(mediaGrpcAddress)
                    .defaultLoadBalancingPolicy(LOAD_BALANCING_RR)
                    .usePlaintext()
                    .build();
        }
        return managedChannel;
    }

    @Override
    public MediaByte queryMedia(StringValue request) {
        var mediaApplicationStub = MediaApplicationGrpc.newBlockingStub(getManagedChannel());
        return mediaApplicationStub.queryMedia(request);
    }

    @Override
    public MediaDTO createMedia(MediaByte request) {
        var mediaApplicationStub = MediaApplicationGrpc.newBlockingStub(getManagedChannel());
        return mediaApplicationStub.createMedia(request);
    }

    @Override
    public MediaDTO queryMediaIdByDigest(StringValue request) {
        var mediaApplicationStub = MediaApplicationGrpc.newBlockingStub(getManagedChannel());
        return mediaApplicationStub.queryMediaIdByDigest(request);
    }


}
