package com.example.binance;

import com.example.tickers.Coin;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.stream.Stream;

@Component
public class BinanceWebSocketTickerStreamClient implements WebSocketHandler {

    private final String baseUrl;
    private final BinanceTickerMessageHandler handler;
    private final ObjectMapper objectMapper;

    public BinanceWebSocketTickerStreamClient(
            @Value("${binance.websocket.base-url}") String baseUrl,
            BinanceTickerMessageHandler handler,
            ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;
        this.handler = handler;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void connect() {
        try {
            StandardWebSocketClient client = new StandardWebSocketClient();
            client.doHandshake(this, createStreamUrl());
        } catch (Exception e) {
            System.err.println("Failed to connect to Binance WebSocket: " + e.getMessage());
        }
    }

    private String createStreamUrl() {
        String tickers = Stream.of(Coin.values())
                .map(BinanceWebSocketTickerStreamClient::createTicker)
                .reduce((first, second) -> first + "/" + second)
                .orElse("");
        return baseUrl + "/stream?streams=" + tickers;
    }

    private static String createTicker(Coin coin) {
        return coin.getTag().toLowerCase() + "usdt@ticker";
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connected to Binance WebSocket");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        try {
            BinanceWebSocketMessage wsMessage = objectMapper.readValue(
                    message.getPayload().toString(), BinanceWebSocketMessage.class);
            handler.handleMessage(wsMessage);
        } catch (Exception e) {
            throw new BinanceMessageSerializationException("Failed to parse Binance message", e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        System.err.println("WebSocket transport error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("Binance WebSocket connection closed: " + closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
