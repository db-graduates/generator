package com.db.generator;

import com.cf.client.poloniex.PoloniexExchangeService;
import com.cf.data.model.poloniex.PoloniexChartData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class GeneratorApplication {
    @Value("${pl.apikey}")
    private String apiKey;

    @Value("${pl.apisecret}")
    private String apiSecret;

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                SpringApplication.run(GeneratorApplication.class, args);
//		applicationContext.getBean(SocketCollector.class).start();
        applicationContext.getBean(ChartAggregator.class)
                .getBtcDailyChartDataStartingFromYesterday();
        System.out.println();
    }

    @Bean
    public PoloniexExchangeService poloniexExchangeService() {
        PoloniexExchangeService service =
                new PoloniexExchangeService(apiKey, apiSecret);

        return service;
    }
}
