package net.cryptochecker.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Market {

    @JsonProperty(value = "MarketName")
    private String marketName;
    @JsonProperty(value = "High")
    private double high;
    @JsonProperty(value = "Low")
    private double low;
    @JsonProperty(value = "Volume")
    private double volume;
    @JsonProperty(value = "Last")
    private double last;
    @JsonProperty(value = "BaseVolume")
    private double baseVolume;

    @JsonProperty(value = "TimeStamp")
    private String timestamp;
    @JsonProperty(value = "Bid")
    private double bid;
    @JsonProperty(value = "Ask")
    private double ask;
    @JsonProperty(value = "OpenBuyOrders")
    private int openBuyOrders;
    @JsonProperty(value = "OpenSellOrders")
    private int openSellOrders;
    @JsonProperty(value = "PrevDay")
    private double prevDay;
    @JsonProperty(value = "Created")
    private String created;
    @JsonProperty(value = "DisplayMarketName")
    private String displayMarketName;

    public Market() {
    }

    @Override
    public String toString() {
        return "Market{" +
                "marketName='" + marketName + '\'' +
                ", high=" + high +
                ", low=" + low +
                ", volume=" + volume +
                ", last=" + last +
                ", baseVolume=" + baseVolume +
                ", timestamp='" + timestamp + '\'' +
                ", bid=" + bid +
                ", ask=" + ask +
                ", openBuyOrders=" + openBuyOrders +
                ", openSellOrders=" + openSellOrders +
                ", prevDay=" + prevDay +
                ", created='" + created + '\'' +
                ", displayMarketName='" + displayMarketName + '\'' +
                '}';
    }

    public String getDisplayMarketName() {
        return displayMarketName;
    }

    public void setDisplayMarketName(String displayMarketName) {
        this.displayMarketName = displayMarketName;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public double getBaseVolume() {
        return baseVolume;
    }

    public void setBaseVolume(double baseVolume) {
        this.baseVolume = baseVolume;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public int getOpenBuyOrders() {
        return openBuyOrders;
    }

    public void setOpenBuyOrders(int openBuyOrders) {
        this.openBuyOrders = openBuyOrders;
    }

    public int getOpenSellOrders() {
        return openSellOrders;
    }

    public void setOpenSellOrders(int openSellOrders) {
        this.openSellOrders = openSellOrders;
    }

    public double getPrevDay() {
        return prevDay;
    }

    public void setPrevDay(double prevDay) {
        this.prevDay = prevDay;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    private String dateToDateTime(LocalDateTime date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.ms");
        return dateFormat.format(date);
    }


}
