package com.ay.testlab.kafka.batchlistener;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BatchMessageConsumingExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchMessageConsumingExample.class);

    @Autowired
    private MessageProducerForBatchListener sender;

    @Value("${kafka.topic.batchConsumerTopic}")
    private String topicName;

    public void execute(ArrayList ar) {
        LOGGER.info("BatchMessageConsumingExample is executing...");
        for (int j = 0; j < 10 ; j++) {
            try {
                Thread.sleep(10);     //add some lag to producer
                for (int i = 0; i < ar.size(); ++i) {
                    sender.send(topicName, "BatchMessageConsuming - ConsumingExample = " + ar.get(i).toString());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public MessageProducerForBatchListener messageProducer(){
        return new MessageProducerForBatchListener();
    }

    @Bean
    public BatchMessageConsumer batchMessageConsumer(){
        return new BatchMessageConsumer();
    }
}
