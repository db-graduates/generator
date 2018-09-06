package com.db.generator.aggregator;


import com.cf.client.poloniex.PoloniexExchangeService;
import com.cf.data.model.poloniex.PoloniexChartData;
import com.db.generator.producer.KafkaSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ChartAggregator {
    private static final Logger LOG = LogManager.getLogger(ChartAggregator.class);

    @Autowired
    PoloniexExchangeService service;

    @Value("${pl.currency}")
    private String currency;

    @Value("${pl.time.step.back}")
    private Long timeStepBack;

    @Value("${pl.time.period}")
    private Long timePeriod;

    public List<PoloniexChartData> getBtcDailyChartData() {
        ZonedDateTime time = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(timeStepBack);
        LOG.info("ask: " + time);
        return service.returnChartData(currency,
                timePeriod,
                time.toEpochSecond());
    }

    public List<PoloniexChartData> getBtcDailyChartData(long pastJump, long periodOfTime) {
        ZonedDateTime time = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(pastJump);
        LOG.info("ask: " + time);
        return service.returnChartData(currency,
                periodOfTime,
                time.toEpochSecond());
    }

}
