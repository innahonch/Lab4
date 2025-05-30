package com.example.binance;

public interface BinanceTickerUpdateListener {
    void onUpdate(BinanceWebSocketMessage message);
}
