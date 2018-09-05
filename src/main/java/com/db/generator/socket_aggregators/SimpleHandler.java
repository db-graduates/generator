package com.db.generator.socket_aggregators;

import com.cf.client.poloniex.wss.model.PoloniexWSSTicker;
import com.cf.client.wss.handler.IMessageHandler;
import com.db.generator.profile.Socket;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Socket
@Component
public class SimpleHandler implements IMessageHandler {
    private static final Logger LOG = LogManager.getLogger();
    private static final Gson GSON = new Gson();

    public SimpleHandler() {
    }

    public void handle(String message) {
        PoloniexWSSTicker ticker = this.mapMessageToPoloniexTicker(message);
        LOG.debug(ticker);
    }

    protected PoloniexWSSTicker mapMessageToPoloniexTicker(String message) {
        List results = (List) GSON.fromJson(message, List.class);
        if (results.size() < 3) {
            return null;
        } else {
            List olhc = (List) results.get(2);
            return (new PoloniexWSSTicker.PoloniexWSSTickerBuilder())
                    .setCurrencyPair((Double) olhc.get(0))
                    .setLastPrice(new BigDecimal((String) olhc.get(1)))
                    .setLowestAsk(new BigDecimal((String) olhc.get(2)))
                    .setHighestBid(new BigDecimal((String) olhc.get(3)))
                    .setPercentChange(new BigDecimal((String) olhc.get(4)))
                    .setBaseVolume(new BigDecimal((String) olhc.get(5)))
                    .setQuoteVolume(new BigDecimal((String) olhc.get(6)))
                    .setIsFrozen((Double) olhc.get(7) == 1.0D)
                    .setTwentyFourHourHigh(new BigDecimal((String) olhc.get(8)))
                    .setTwentyFourHourLow(new BigDecimal((String) olhc.get(9)))
                    .buildPoloniexTicker();
        }
    }
}