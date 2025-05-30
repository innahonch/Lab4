package com.example.binance;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BinanceTickerMessageHandler {
    private final List<BinanceTickerUpdateListener> listeners;

    public BinanceTickerMessageHandler(List<BinanceTickerUpdateListener> listeners) {
        this.listeners = listeners;
    }

    public void handleMessage(BinanceWebSocketMessage message) {
        // System.out.println("Received message from stream: " + message.getStream());
        // System.out.println("Ticker data: " + message.getData());
        listeners.forEach(listener -> listener.onUpdate(message));
    }
}
