package org.myddd.grpc;

import com.google.protobuf.Empty;
import io.grpc.*;
import io.grpc.netty.NettyServerBuilder;
import org.junit.jupiter.api.*;
import org.myddd.domain.InstanceFactory;
import org.myddd.grpc.api.EchoDto;
import org.myddd.grpc.api.EchoServiceGrpc;
import org.myddd.grpc.api.EchoType;
import org.myddd.grpc.mock.EchoError;
import org.myddd.grpc.mock.EchoServiceGrpcImpl;
import org.myddd.ioc.spring.SpringInstanceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootTest(classes = Application.class)
public class EchoServiceGrpcTest {


    private final static int PORT = 8081;

    private static Server server;

    private static ManagedChannel managedChannel;


    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void beforeClass(){
        InstanceFactory.setInstanceProvider(SpringInstanceProvider.createInstance(applicationContext));
    }

    @BeforeAll
    public static void beforeAll() throws IOException {
        server = NettyServerBuilder.forPort(PORT).addService(new EchoServiceGrpcImpl()).build();
        server.start();

        managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", PORT).usePlaintext().build();
    }

    @AfterAll
    public static void afterAll(){
        server.shutdownNow();
    }

    @Test
    void hello(){
        var echoServiceStub = EchoServiceGrpc.newBlockingStub(managedChannel);
        Assertions.assertNotNull(echoServiceStub.hello(Empty.getDefaultInstance()));
    }

    @Test
    void echo(){
        var echoServiceStub = EchoServiceGrpc.newBlockingStub(managedChannel);
        Assertions.assertDoesNotThrow(()->echoServiceStub.echo(EchoDto.newBuilder().build()));

        var errorEchoDto = EchoDto.newBuilder()
                .setType(EchoType.Exception)
                .build();

        try{
            echoServiceStub.echo(errorEchoDto);
        }catch (StatusRuntimeException e){
            var metadata = e.getTrailers();
            Assertions.assertNotNull(metadata);

            var isBusinessException = metadata.get(GrpcRunner.KEY_IS_BUSINESS_EXCEPTION);
            Assertions.assertTrue(Boolean.parseBoolean(isBusinessException));

            var errorCode= metadata.get(GrpcRunner.KEY_ERROR_CODE);
            Assertions.assertEquals(EchoError.ERROR_ONE.toString(),errorCode);

            var errorDataString = metadata.get(GrpcRunner.KEY_ERROR_DATA);
            assert errorDataString != null;
            Assertions.assertEquals("hello,world",new String(errorDataString));
        }
    }

}
