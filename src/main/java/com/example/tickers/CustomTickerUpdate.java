package com.example.tickers;

public record CustomTickerUpdate(Coin coin, long timestamp, double price) {
}