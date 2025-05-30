package com.example.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final UserWebSocketHandler webSocketHandler;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;
    private final String hostname;
    private final String port;

    public WebSocketConfig(
            UserWebSocketHandler webSocketHandler,
            WebSocketHandshakeInterceptor webSocketHandshakeInterceptor,
            @Value("${hostname}") String hostname,
            @Value("${server.port}") String port) {
        this.webSocketHandler = webSocketHandler;
        this.webSocketHandshakeInterceptor = webSocketHandshakeInterceptor;
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/coins")
                .addInterceptors(webSocketHandshakeInterceptor)
                .setAllowedOrigins(hostname + ":" + port);
    }
}
