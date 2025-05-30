package com.example.websocket;

import com.example.jwt.JwtDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtDecoder jwtDecoder;

    public WebSocketHandshakeInterceptor(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String query = request.getURI().getQuery();
        System.out.println("WebSocket handshake query: " + query);
        String token = null;

        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                if (param.startsWith("token=")) {
                    token = param.substring("token=".length());
                    int ampIndex = token.indexOf('&');
                    if (ampIndex != -1) {
                        token = token.substring(0, ampIndex);
                    }
                    break;
                }
            }
        }

        if (token != null) {
            // System.out.println("Extracted token: " + token);
            try {
                var claims = jwtDecoder.decodeToken(token);
                // System.out.println("Decoded claims: " + claims);
                attributes.put("userId", claims.getSubject());
                return true;
            } catch (Exception e) {
                System.err.println("Token decoding failed: " + e.getMessage());
                e.printStackTrace();
                response.setStatusCode(HttpStatus.FORBIDDEN);
                return false;
            }
        }

        System.out.println("No valid token found");
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
