package com.tools.kafka.listener;

import org.springframework.kafka.listener.MessageListener;

/**
 * date: 2017/5/26
 * description :
 *
 * @author : zhencai.cheng
 */
public interface KafkaConsumerService extends MessageListener<Integer,String> {
}
