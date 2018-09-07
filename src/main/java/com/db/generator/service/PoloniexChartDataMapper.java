package com.db.generator.service;

import com.cf.data.model.poloniex.PoloniexChartData;
import com.db.generator.util.IdCreator;
import db.com.model.Message;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PoloniexChartDataMapper implements APIChartDataMapper<PoloniexChartData> {

    @Override
    public Message mapToDefaultMessage(PoloniexChartData chartData) {

        ZonedDateTime date = chartData.date;
        return Message.builder()
            .date(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")))
            .high(chartData.high.doubleValue())
            .low(chartData.low.doubleValue())
            .volume(chartData.volume.doubleValue())
            .value(chartData.quoteVolume.doubleValue())
            .open(chartData.open.doubleValue())
            .close(chartData.close.doubleValue())
            .id(IdCreator.getId(chartData))
            .build();
    }


}
