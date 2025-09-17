package com.grpc.client;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "grpc.client.config")
public class GrpcClientProperties {

    private String address;
    private int port;
    private String trustCertPath;
    private String clientCertPath;
    private String clientKeyPath;

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }
    public String getTrustCertPath() { return trustCertPath; }
    public void setTrustCertPath(String trustCertPath) { this.trustCertPath = trustCertPath; }
    public String getClientCertPath() { return clientCertPath; }
    public void setClientCertPath(String clientCertPath) { this.clientCertPath = clientCertPath; }
    public String getClientKeyPath() { return clientKeyPath; }
    public void setClientKeyPath(String clientKeyPath) { this.clientKeyPath = clientKeyPath; }
}