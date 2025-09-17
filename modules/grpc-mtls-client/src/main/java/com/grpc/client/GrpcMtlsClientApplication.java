package com.grpc.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcMtlsClientApplication implements CommandLineRunner {

    private final GreetingClientService greetingClientService;

    // Spring injects our client service
    public GrpcMtlsClientApplication(GreetingClientService greetingClientService) {
        this.greetingClientService = greetingClientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GrpcMtlsClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // This method runs after the application starts
        String response = greetingClientService.receiveGreeting("Gemini");

        System.out.println("\n--- RESPONSE FROM SERVER ---");
        System.out.println(response);
        System.out.println("----------------------------");
    }
}