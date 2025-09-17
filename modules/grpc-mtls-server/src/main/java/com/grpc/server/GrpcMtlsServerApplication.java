package com.grpc.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcMtlsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GrpcMtlsServerApplication.class, args);
        System.out.println("✅ gRPC mTLS Server Started on port 9090...");
    }
}