package com.db.generator.config;


import com.cf.client.poloniex.PoloniexExchangeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpApiConfig {
    @Value("${pl.apikey}")
    private String apiKey;

    @Value("${pl.apisecret}")
    private String apiSecret;

    @Bean
    public PoloniexExchangeService poloniexExchangeService() {
        PoloniexExchangeService service =
                new PoloniexExchangeService(apiKey, apiSecret);

        return service;
    }
}
