package com.example.websocket;

import com.example.tickers.Coin;
import com.example.tickers.CustomTickerUpdate;
import com.example.tickers.TickerUpdateListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.tickers.protobuf.TickerUpdate;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class UserSession implements TickerUpdateListener {
    private final WebSocketSession session;
    private final String userId;
    private final ObjectMapper objectMapper;
    private final boolean useProtobuf;

    public UserSession(WebSocketSession session, String userId, ObjectMapper objectMapper, boolean useProtobuf) {
        this.session = session;
        this.userId = userId;
        this.objectMapper = objectMapper;
        this.useProtobuf = useProtobuf;
    }

    public String getSessionId() {
        return session.getId();
    }

    @Override
    public void onTickerUpdate(CustomTickerUpdate tickerUpdate) throws IOException {
        try {
            if (useProtobuf) {
                TickerUpdate protoUpdate = TickerUpdate.newBuilder()
                        .setCoin(tickerUpdate.coin().name())
                        .setTimestamp(tickerUpdate.timestamp())
                        .setPrice(tickerUpdate.price())
                        .build();
                session.sendMessage(new BinaryMessage(protoUpdate.toByteArray()));
            } else {
                String json = objectMapper.writeValueAsString(tickerUpdate);
                session.sendMessage(new TextMessage(json));
            }
        }
        catch (IOException e) {
            System.err.println("Failed to send update to session");
        }
    }
}
