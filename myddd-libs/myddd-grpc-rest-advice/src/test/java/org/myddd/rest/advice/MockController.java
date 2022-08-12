package org.myddd.rest.advice;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.myddd.grpc.api.EchoDto;
import org.myddd.grpc.api.EchoServiceGrpc;
import org.myddd.grpc.api.EchoType;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,value = "/v1")
public class MockController {


    private ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 8081).usePlaintext().build();


    @GetMapping("/error/exception")
    ResponseEntity<Void> exception() throws Exception {
        throw new Exception();
    }

    /**
     * 抛出一个业务异常，不带data
     */
    @GetMapping("/error/businessErrorOne")
    ResponseEntity<Object> businessException(){
        var echoServiceStub = EchoServiceGrpc.newBlockingStub(managedChannel);
        var result = echoServiceStub.echo(EchoDto.newBuilder()
                .setType(EchoType.Exception)
                .build());
        return ResponseEntity.ok().build();
    }


}
