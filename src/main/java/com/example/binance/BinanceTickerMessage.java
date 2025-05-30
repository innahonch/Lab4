package com.example.binance;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinanceTickerMessage {
    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("p")
    private String priceChange;

    @JsonProperty("P")
    private String priceChangePercent;

    @JsonProperty("w")
    private String weightedAvgPrice;

    @JsonProperty("x")
    private String previousPrice;

    @JsonProperty("c")
    private String lastPrice;

    @JsonProperty("Q")
    private String lastQty;

    @JsonProperty("b")
    private String bestBidPrice;

    @JsonProperty("B")
    private String bestBidQty;
    @JsonProperty("a") private String bestAskPrice;

    @JsonProperty("A")
    private String bestAskQty;

    @JsonProperty("o")
    private String openPrice;

    @JsonProperty("h")
    private String highPrice;

    @JsonProperty("l")
    private String lowPrice;

    @JsonProperty("v")
    private String totalTradedBaseAssetVolume;

    @JsonProperty("q")
    private String totalTradedQuoteAssetVolume;

    @JsonProperty("O")
    private Long statisticsOpenTime;

    @JsonProperty("C")
    private Long statisticsCloseTime;

    @JsonProperty("F")
    private Long firstTradeId;

    @JsonProperty("L")
    private Long lastTradeId;

    @JsonProperty("n")
    private Integer tradeCount;

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public Long getEventTime() { return eventTime; }
    public void setEventTime(Long eventTime) { this.eventTime = eventTime; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getPriceChange() { return priceChange; }
    public void setPriceChange(String priceChange) { this.priceChange = priceChange; }
    public String getPriceChangePercent() { return priceChangePercent; }
    public void setPriceChangePercent(String priceChangePercent) { this.priceChangePercent = priceChangePercent; }
    public String getWeightedAvgPrice() { return weightedAvgPrice; }
    public void setWeightedAvgPrice(String weightedAvgPrice) { this.weightedAvgPrice = weightedAvgPrice; }
    public String getPreviousPrice() { return previousPrice; }
    public void setPreviousPrice(String previousPrice) { this.previousPrice = previousPrice; }
    public String getLastPrice() { return lastPrice; }
    public void setLastPrice(String lastPrice) { this.lastPrice = lastPrice; }
    public String getLastQty() { return lastQty; }
    public void setLastQty(String lastQty) { this.lastQty = lastQty; }
    public String getBestBidPrice() { return bestBidPrice; }
    public void setBestBidPrice(String bestBidPrice) { this.bestBidPrice = bestBidPrice; }
    public String getBestBidQty() { return bestBidQty; }
    public void setBestBidQty(String bestBidQty) { this.bestBidQty = bestBidQty; }
    public String getBestAskPrice() { return bestAskPrice; }
    public void setBestAskPrice(String bestAskPrice) { this.bestAskPrice = bestAskPrice; }
    public String getBestAskQty() { return bestAskQty; }
    public void setBestAskQty(String bestAskQty) { this.bestAskQty = bestAskQty; }
    public String getOpenPrice() { return openPrice; }
    public void setOpenPrice(String openPrice) { this.openPrice = openPrice; }
    public String getHighPrice() { return highPrice; }
    public void setHighPrice(String highPrice) { this.highPrice = highPrice; }
    public String getLowPrice() { return lowPrice; }
    public void setLowPrice(String lowPrice) { this.lowPrice = lowPrice; }
    public String getTotalTradedBaseAssetVolume() { return totalTradedBaseAssetVolume; }
    public void setTotalTradedBaseAssetVolume(String totalTradedBaseAssetVolume) { this.totalTradedBaseAssetVolume = totalTradedBaseAssetVolume; }
    public String getTotalTradedQuoteAssetVolume() { return totalTradedQuoteAssetVolume; }
    public void setTotalTradedQuoteAssetVolume(String totalTradedQuoteAssetVolume) { this.totalTradedQuoteAssetVolume = totalTradedQuoteAssetVolume; }
    public Long getStatisticsOpenTime() { return statisticsOpenTime; }
    public void setStatisticsOpenTime(Long statisticsOpenTime) { this.statisticsOpenTime = statisticsOpenTime; }
    public Long getStatisticsCloseTime() { return statisticsCloseTime; }
    public void setStatisticsCloseTime(Long statisticsCloseTime) { this.statisticsCloseTime = statisticsCloseTime; }
    public Long getFirstTradeId() { return firstTradeId; }
    public void setFirstTradeId(Long firstTradeId) { this.firstTradeId = firstTradeId; }
    public Long getLastTradeId() { return lastTradeId; }
    public void setLastTradeId(Long lastTradeId) { this.lastTradeId = lastTradeId; }
    public Integer getTradeCount() { return tradeCount; }
    public void setTradeCount(Integer tradeCount) { this.tradeCount = tradeCount; }

    @Override
    public String toString() {
        return "BinanceTickerMessage{eventType='" + eventType + "', eventTime=" + eventTime + ", symbol='" + symbol + "', lastPrice='" + lastPrice + "'}";
    }
}