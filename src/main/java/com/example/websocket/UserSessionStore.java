package com.example.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserSessionStore {
    private final Map<String, UserSession> sessions = new ConcurrentHashMap<>();

    public void addSession(WebSocketSession session, String userId, ObjectMapper objectMapper) {
        String query = session.getUri().getQuery();
        boolean useProtobuf = false;
        if (query != null) {
            if (query.contains("format=protobuf")) {
                useProtobuf = true;
            }
        }
        sessions.put(session.getId(), new UserSession(session, userId, objectMapper, useProtobuf));
    }

    public void removeSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public Iterable<UserSession> getAllSessions() {
        return sessions.values();
    }
}