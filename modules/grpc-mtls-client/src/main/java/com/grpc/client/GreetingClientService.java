package com.grpc.client;

import com.example.grpc.api.GreetingServiceGrpc;
import com.example.grpc.api.HelloRequest;
import com.example.grpc.api.HelloResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GreetingClientService {

    // Injects the gRPC client stub.
    // 'greeting-service' is the name we defined in application.yml
    @GrpcClient("greeting-service")
    private GreetingServiceGrpc.GreetingServiceBlockingStub greetingStub;

    public String receiveGreeting(String name) {
        System.out.println("Sending request to server with name: " + name);

        // Create the request object
        HelloRequest request = HelloRequest.newBuilder()
                .setName(name)
                .build();

        // Call the server and get the response
        HelloResponse response = greetingStub.greet(request);

        // Return the greeting message from the response
        return response.getGreeting();
    }
}