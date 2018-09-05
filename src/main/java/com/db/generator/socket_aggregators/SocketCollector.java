package com.db.generator.socket_aggregators;

import com.cf.client.WSSClient;
import com.cf.client.poloniex.wss.model.PoloniexWSSSubscription;
import com.cf.client.wss.handler.IMessageHandler;
import com.cf.example.PoloniexWSSClientExample;
import com.db.generator.profile.Socket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// only for learning purposes (don't use this class)
@Socket
@Component
public class SocketCollector {

    private static final Logger LOG = LogManager.getLogger(PoloniexWSSClientExample.class);
    private static final String ENDPOINT_URL = "wss://api2.poloniex.com";
    @Value("${currency.code}")
    private String currencyCode;
    @Value("${work.time.mill}")
    private long timePeriod;

    public void start() {
        try {
            this.subscribe();
        } catch (InterruptedException ex) {
            LOG.info(ex.getMessage());
            System.exit(0);
        } catch (Exception ex) {
            LOG.fatal("An exception occurred when running PoloniexWSSClientExample - {}", ex.getMessage());
            System.exit(-1);
        }
    }

    public void subscribe() throws Exception {
        IMessageHandler iMessageHandler = new SimpleHandler();
        try (WSSClient wssClient = new WSSClient(ENDPOINT_URL)) {
            wssClient.addSubscription(new PoloniexWSSSubscription(currencyCode),
                    iMessageHandler);
            wssClient.run(timePeriod);
        }

    }
}