package com.example.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
public class UserWebSocketHandler extends TextWebSocketHandler {
    private final UserSessionStore sessionStore;
    private final ObjectMapper objectMapper;

    public UserWebSocketHandler(UserSessionStore sessionStore, ObjectMapper objectMapper) {
        this.sessionStore = sessionStore;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        if (userId == null) {
            try {
                session.close(CloseStatus.POLICY_VIOLATION.withReason("Unauthorized: Invalid or missing token"));
            } catch (IOException e) {
                System.err.println("Error closing session: " + e.getMessage());
            }
            return;
        }
        sessionStore.addSession(session, userId, objectMapper);
        System.out.println("Connection established for session: " + session.getId() + ", userId: " + userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionStore.removeSession(session.getId());
        System.out.println("Connection closed for session: " + session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        sessionStore.removeSession(session.getId());
        System.out.println("Transport error for session: " + session.getId() + ", error: " + exception.getMessage());
    }
}
