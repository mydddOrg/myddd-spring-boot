package org.myddd.extensions.organization;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.myddd.extensions.organization.application.grpc.EmployeeApplicationGrpcImpl;
import org.myddd.extensions.organization.application.grpc.OrgRoleApplicationGrpcImpl;
import org.myddd.extensions.organization.application.grpc.OrganizationApplicationGrpcImpl;
import org.myddd.extensions.organization.application.grpc.PermissionGroupApplicationGrpcImpl;

import java.io.IOException;
import java.security.SecureRandom;

public class AbstractGrpcTest extends AbstractTest{

    private final static int PORT = 8080 + new SecureRandom().nextInt(500);

    private static Server server;

    protected static ManagedChannel managedChannel;

    @BeforeAll
    public static void beforeAll() throws IOException {
        server = NettyServerBuilder.forPort(PORT)
                .addService(new EmployeeApplicationGrpcImpl())
                .addService(new OrganizationApplicationGrpcImpl())
                .addService(new PermissionGroupApplicationGrpcImpl())
                .addService(new OrgRoleApplicationGrpcImpl())
                .build();
        server.start();

        managedChannel = ManagedChannelBuilder.forTarget("127.0.0.1:" + PORT).usePlaintext().build();
    }

    @AfterAll
    public static void afterAll() {
        server.shutdownNow();
    }
}
