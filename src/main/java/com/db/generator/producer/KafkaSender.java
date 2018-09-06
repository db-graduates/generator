package com.db.generator.producer;

import com.cf.data.model.poloniex.PoloniexChartData;
import com.db.generator.aggregator.ChartAggregator;
import com.db.generator.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaSender implements Sender {
    private static final Logger LOG = LogManager.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private ChartAggregator source;

    @EventListener(ContextRefreshedEvent.class)
    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 5,
            backoff = @Backoff(delay = 5000))
    public void populateKafka() {
        LOG.info("Try populate!");
        long fiveMinutesPeriodInSec = 300;
        long fiveMinutesPeriodsPerDay = 12 * 24;
        List<PoloniexChartData> btcDailyChartDataStartingFromYesterday;
        btcDailyChartDataStartingFromYesterday =
                source.getBtcDailyChartData(
                        (fiveMinutesPeriodsPerDay) * fiveMinutesPeriodInSec,
                        fiveMinutesPeriodInSec);
        for (PoloniexChartData poloniexChartData : btcDailyChartDataStartingFromYesterday) {
            LOG.info("Get data from API" + poloniexChartData);
            kafkaTemplate.send("test",
                    Message.of(poloniexChartData));
        }
    }

    @Scheduled(fixedRate = 300_000, initialDelay = 300_000)
    @Override
    public void sendMessage() {
        int attempts = 0;
        while (true) {
            try {
                if (attempts > 0) {
                    Thread.sleep(10_000);
                }
                LOG.info("Send message to kafka (online data) is started!");
                List<PoloniexChartData> btcDailyChartDataStartingFromYesterday = source.getBtcDailyChartData();
                LOG.info("Gotten size from API: " + btcDailyChartDataStartingFromYesterday.size());
                if (btcDailyChartDataStartingFromYesterday.size() > 0) {
                    btcDailyChartDataStartingFromYesterday.forEach(b -> LOG.info("Get: " + b.date));
                    kafkaTemplate.send("test",
                            Message.of(btcDailyChartDataStartingFromYesterday));
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
