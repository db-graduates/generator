package com.db.generator.factory;

import com.cf.data.model.poloniex.PoloniexChartData;

import java.time.ZonedDateTime;

public class IdCreator {

    public static long getId(PoloniexChartData poloniexChartData) {
        ZonedDateTime date = poloniexChartData.date;
        return date.toEpochSecond() / 300;
    }
}
