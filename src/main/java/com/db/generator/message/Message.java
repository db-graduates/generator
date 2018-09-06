package com.db.generator.message;

import com.cf.data.model.poloniex.PoloniexChartData;
import com.db.generator.factory.IdCreator;

import java.math.BigDecimal;
import java.util.List;

public class Message {
    private long id;

    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal volume;
    private BigDecimal quoteVolume;
    private BigDecimal open;
    private BigDecimal close;


    public Message(List<PoloniexChartData> list, long id) {
        PoloniexChartData chartData = list.get(0);
        high = chartData.high;
        low = chartData.low;
        volume = chartData.volume;
        quoteVolume = chartData.quoteVolume;
        open = chartData.open;
        close = chartData.close;
        this.id = id;
    }

    public Message(PoloniexChartData chartData, long id) {
        high = chartData.high;
        low = chartData.low;
        volume = chartData.volume;
        quoteVolume = chartData.quoteVolume;
        open = chartData.open;
        close = chartData.close;
        this.id = id;
    }

    public static Message of(PoloniexChartData poloniexChartData) {
        return new Message(poloniexChartData, IdCreator.getId(poloniexChartData));
    }

    public static Message of(List<PoloniexChartData> poloniexChartData) {
        return new Message(poloniexChartData,
                IdCreator.getId(poloniexChartData.get(0)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getQuoteVolume() {
        return quoteVolume;
    }

    public void setQuoteVolume(BigDecimal quoteVolume) {
        this.quoteVolume = quoteVolume;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public void setOpen(BigDecimal open) {
        this.open = open;
    }

    public BigDecimal getClose() {
        return close;
    }

    public void setClose(BigDecimal close) {
        this.close = close;
    }
}
