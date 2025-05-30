package com.example.tickers;

public enum Coin {
    BTC("btc"),
    ETH("eth"),
    XRP("xrp"),
    DOGE("doge");

    private final String tag;

    Coin(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}