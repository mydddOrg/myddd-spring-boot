package org.myddd.extensions.organization.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Objects;

public class AbstractOrganizationGrpcBridge {

    private static final String LOAD_BALANCING_RR = "round_robin";

    private static final String grpcAddress = "dns:///organisation:8081";

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
