package com.grpc.server;

import com.example.grpc.api.GreetingServiceGrpc;
import com.example.grpc.api.HelloRequest;
import com.example.grpc.api.HelloResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void greet(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        String name = request.getName();
        System.out.println("Received request for name: " + name);

        String greetingMessage = "Hello, " + name + "! Your request was secured with mTLS. 🛡️";

        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greetingMessage)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}