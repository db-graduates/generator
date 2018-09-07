package com.db.generator.service;

import com.cf.data.model.poloniex.PoloniexChartData;
import db.com.model.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PoloniexSenderToKafka {

    @Value("${kafka.producer.topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @Autowired
    private APIChartDataMapper dataMapper;

    public void send(List<PoloniexChartData> chartData){
        chartData.forEach(data -> {
            log.info("Get data from API" + data);
            Message message = dataMapper.mapToDefaultMessage(data);
            kafkaTemplate.send(topic, message);
        });
    }
}
