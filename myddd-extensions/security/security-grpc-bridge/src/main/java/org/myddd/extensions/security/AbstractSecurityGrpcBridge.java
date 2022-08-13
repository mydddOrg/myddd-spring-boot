package org.myddd.extensions.security;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Objects;

public abstract class AbstractSecurityGrpcBridge {

    private static final String LOAD_BALANCING_RR = "round_robin";

    private static final String grpcAddress = "dns:///security:8081";

    private ManagedChannel managedChannel;

    protected ManagedChannel getManagedChannel(){
        if(Objects.isNull(managedChannel)){
            managedChannel = ManagedChannelBuilder.
                    forTarget(grpcAddress)
                    .defaultLoadBalancingPolicy(LOAD_BALANCING_RR)
                    .usePlaintext()
                    .build();
        }
        return managedChannel;
    }
}
