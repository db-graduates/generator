package com.db.generator;

import com.cf.client.poloniex.PoloniexExchangeService;
import com.db.generator.http_aggregator.ChartAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GeneratorApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(GeneratorApplication.class, args);
//		applicationContext.getBean(SocketCollector.class).start();
        applicationContext.getBean(ChartAggregator.class)
                .getBtcDailyChartDataStartingFromYesterday();
        System.out.println();
    }
}
