package com.tools.kafka.listener;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tools.kafka.enums.TopicTypeEnum;
import com.tools.kafka.exception.MessageProcessorException;
import com.tools.kafka.consumer.business.IUserMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :消息消费
 */
public class KafkaConsumerListener implements KafkaConsumerService, InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerListener.class);

    @Autowired
    private KafkaMessageListenerContainer kafkaMessageListenerContainer;

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Autowired
    IUserMessageService userMessageService;

    public void onMessage(ConsumerRecord<Integer, String> record) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String msg = record.value();
                TopicTypeEnum typeEnum = TopicTypeEnum.getTopicTypeEnumByTopic(record.topic());
                if (msg == null) {
                    logger.info("主题中有空值存在：topic = {},offset = {}, key = {}, value = {}", record.topic(), record.offset(), record.key(), record.value());
                }
                JSONObject message = JSON.parseObject(msg);
                try {
                    switch (typeEnum) {
                        case USER:
                            userMessageService.onMessage(message);
                            break;
                        default:
                            logger.info("默认:{}",message);
                    }
                } catch (MessageProcessorException e) {
                    logger.error(e.getMessage());
                }
            }
        });
    }

    /**
     * 设置监听
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ContainerProperties containerProperties = kafkaMessageListenerContainer.getContainerProperties();
        if (null != containerProperties) {
            containerProperties.setMessageListener(this);
        }
    }

}
