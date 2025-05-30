package com.example.tickers;

import java.io.IOException;

public interface TickerUpdateListener {
    void onTickerUpdate(CustomTickerUpdate tickerUpdate) throws IOException;
}
