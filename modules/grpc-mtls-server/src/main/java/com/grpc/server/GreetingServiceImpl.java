package com.grpc.server;

import com.example.grpc.api.GreetingServiceGrpc;
import com.example.grpc.api.HelloRequest;
import com.example.grpc.api.HelloResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService // This annotation marks this class as a gRPC service
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void greet(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        // 1. Get the name from the incoming request
        String name = request.getName();
        System.out.println("Received request for name: " + name);

        // 2. Create the greeting message
        String greetingMessage = "Hello, " + name + "! Your request was secured with mTLS. 🛡️";

        // 3. Build the response object
        HelloResponse response = HelloResponse.newBuilder()
                .setGreeting(greetingMessage)
                .build();

        // 4. Send the response back to the client
        responseObserver.onNext(response);

        // 5. Complete the RPC call
        responseObserver.onCompleted();
    }
}