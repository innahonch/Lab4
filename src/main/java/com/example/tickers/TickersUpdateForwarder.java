package com.example.tickers;

import com.example.binance.BinanceTickerUpdateListener;
import com.example.websocket.UserSessionStore;
import com.example.binance.BinanceWebSocketMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TickersUpdateForwarder implements BinanceTickerUpdateListener {
    private final UserSessionStore userSessionStore;

    public TickersUpdateForwarder(UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
    }

    @Override
    public void onUpdate(BinanceWebSocketMessage message) {
        CustomTickerUpdate tickerUpdate = toTickerUpdate(message);
        userSessionStore.getAllSessions().forEach(session -> {
            try {
                session.onTickerUpdate(tickerUpdate);
            } catch (IOException e) {
                System.err.println("Failed to send ticker update to session: " + session.getSessionId());
            }
        });
    }

    private CustomTickerUpdate toTickerUpdate(BinanceWebSocketMessage message) {
        var data = message.getData();
        var symbol = data.getSymbol().toLowerCase();
        var coin = Coin.valueOf(symbol.substring(0, symbol.length() - 4).toUpperCase());
        return new CustomTickerUpdate(coin, data.getEventTime(), Double.parseDouble(data.getLastPrice()));
    }
}