package com.tools.rabbitmq.producer.impl;

import com.tools.rabbitmq.producer.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * date: 2017/5/21
 * description :
 *
 * @author : zhencai.cheng
 */
@Service
public class MQProducerImpl implements MQProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;


    private final static Logger logger = LoggerFactory.getLogger(MQProducerImpl.class);

    @Override
    public void sendQueue(String queueKey, Object object) {
        try {
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
