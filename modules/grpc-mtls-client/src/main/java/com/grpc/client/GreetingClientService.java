package com.grpc.client;

import com.example.grpc.api.GreetingServiceGrpc;
import com.example.grpc.api.HelloRequest;
import com.example.grpc.api.HelloResponse;
import org.springframework.stereotype.Service;

@Service
public class GreetingClientService {

    private final GreetingServiceGrpc.GreetingServiceBlockingStub greetingStub;

    public GreetingClientService(GreetingServiceGrpc.GreetingServiceBlockingStub greetingStub) {
        this.greetingStub = greetingStub;
    }

    public String receiveGreeting(String name) {
        System.out.println("Sending request to server with name: " + name);
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloResponse response = greetingStub.greet(request);
        return response.getGreeting();
    }
}