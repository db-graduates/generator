package com.db.generator.http_aggregator;


import com.cf.client.poloniex.PoloniexExchangeService;
import com.cf.data.model.poloniex.PoloniexChartData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ChartAggregator {

    @Autowired
    PoloniexExchangeService service;

    @Value("${pl.currency}")
    private String currency;

    @Value("${pl.time.step.back}")
    private Long timeStepBack;

    @Value("${pl.time.period}")
    private Long timePeriod;

    public List<PoloniexChartData> getBtcDailyChartDataStartingFromYesterday() {
        ZonedDateTime time = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(timeStepBack);
        return service.returnChartData(currency,
                timePeriod,
                time.toEpochSecond());
    }


}
