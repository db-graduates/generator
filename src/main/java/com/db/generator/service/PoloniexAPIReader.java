package com.db.generator.service;

import com.cf.data.model.poloniex.PoloniexChartData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoloniexAPIReader implements APIReader {
    private static final Logger LOG = LogManager.getLogger(PoloniexAPIReader.class);

    @Autowired
    private ChartAggregator source;

    @Autowired
    private PoloniexSenderToKafka poloniexSenderToKafka;

    @EventListener(ContextRefreshedEvent.class)
    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000))
    public void readFromAPIFor24Hours() {
        LOG.info("Try populate!");
        long fiveMinutesPeriodInSec = 300;
        long fiveMinutesPeriodsPerDay = 12 * 24;
        List<PoloniexChartData> chartDataFromAPI = source.getBtcDailyChartData((fiveMinutesPeriodsPerDay) * fiveMinutesPeriodInSec, fiveMinutesPeriodInSec);
        poloniexSenderToKafka.send(chartDataFromAPI);
    }

    @Scheduled(fixedRate = 300_000, initialDelay = 300_000)
    @Override
    public void readFromAPI() {
        int attempts = 0;
        while (true) {
            try {
                if (attempts > 0) {
                    Thread.sleep(10_000);
                }
                LOG.info("Send message to kafka (online data) is started!");
                List<PoloniexChartData> chartDataFromAPI = source.getBtcDailyChartData();
                LOG.info("Gotten size from API: " + chartDataFromAPI.size());
                if (chartDataFromAPI.size() > 0) {
                    poloniexSenderToKafka.send(chartDataFromAPI);
                    break;
                }
            } catch (Exception e) {
                attempts++;
                if (attempts > 4) {
                    break;
                }
            }
        }
    }
}
