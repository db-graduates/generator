package com.db.generator.producer;

import com.cf.data.model.poloniex.PoloniexChartData;
import com.db.generator.http_aggregator.ChartAggregator;
import com.db.generator.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaSender implements Sender {
    private static final Logger LOG = LogManager.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<Long, Message> kafkaTemplate;

    @Autowired
    private ChartAggregator source;

    @Scheduled(fixedRate = 2_000)
    @Override
    public void sendMessage() {
        List<PoloniexChartData> btcDailyChartDataStartingFromYesterday = source.getBtcDailyChartDataStartingFromYesterday();
        if (btcDailyChartDataStartingFromYesterday.iterator().hasNext()) {
            btcDailyChartDataStartingFromYesterday.forEach(b -> System.out.println("Get: " + b.date));
            kafkaTemplate.send("test", new Message(btcDailyChartDataStartingFromYesterday));
        }
    }
}
