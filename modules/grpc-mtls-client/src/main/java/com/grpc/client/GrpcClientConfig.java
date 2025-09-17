package com.grpc.client;

import com.example.grpc.api.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLException;
import java.io.IOException;

@Configuration
public class GrpcClientConfig {

    private final GrpcClientProperties properties;

    public GrpcClientConfig(GrpcClientProperties properties) {
        this.properties = properties;
    }

    @Bean
    public GreetingServiceGrpc.GreetingServiceBlockingStub greetingServiceBlockingStub() throws IOException, SSLException {
        ClassPathResource trustCertCollection = new ClassPathResource(properties.getTrustCertPath());
        ClassPathResource clientCertChain = new ClassPathResource(properties.getClientCertPath());
        ClassPathResource clientPrivateKey = new ClassPathResource(properties.getClientKeyPath());

        SslContext sslContext = GrpcSslContexts.forClient()
                .trustManager(trustCertCollection.getInputStream())
                .keyManager(clientCertChain.getInputStream(), clientPrivateKey.getInputStream())
                .build();

        ManagedChannel channel = NettyChannelBuilder.forAddress(properties.getAddress(), properties.getPort())
                .sslContext(sslContext)
                .build();

        return GreetingServiceGrpc.newBlockingStub(channel);
    }
}