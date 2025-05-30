package com.example.binance;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinanceWebSocketMessage {
    @JsonProperty("stream")
    private String stream;
    @JsonProperty("data")
    private BinanceTickerMessage data;

    public String getStream() { return stream; }
    public void setStream(String stream) { this.stream = stream; }
    public BinanceTickerMessage getData() { return data; }
    public void setData(BinanceTickerMessage data) { this.data = data; }

    @Override
    public String toString() {
        return "BinanceWebSocketMessage{stream='" + stream + "', data=" + data + "}";
    }
}