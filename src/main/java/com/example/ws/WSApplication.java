package com.example.ws;

import com.example.openidconnect.OpenIdConnectProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(OpenIdConnectProperties.class)
@ComponentScan(basePackages = "com.example")
public class WSApplication {
    public static void main(String[] args) {
        SpringApplication.run(WSApplication.class, args);
    }
}