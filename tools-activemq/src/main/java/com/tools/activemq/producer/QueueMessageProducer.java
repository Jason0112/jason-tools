package com.tools.activemq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author ：cheng.zhencai  
 * @date ：2017/04/30
 * @description : 队列生产者，根据spring配置文件定义
 */
@Component
public class QueueMessageProducer {
    /**
     * jmsTemplate模板，pubSubDomain?广播消息:队列消息
     */
    @Autowired
    private JmsTemplate jmsTemplate;

    private String DEFAULT_TOPIC_NAME = "com.tools.default";

    /**
     * 自定义队列名
     *
     * @param queueName 队列名
     * @param obj       消息体
     */
    private void sendQueue(String queueName, final Serializable obj) {
        jmsTemplate.send(queueName, session -> {
            return session.createObjectMessage(obj);
        });
    }

    /**
     * 自定义队列名
     *
     * @param topicName 队列名
     * @param obj       消息体
     */
    public void sendTopic(String topicName, Serializable obj) {
        this.sendQueue(topicName, obj);
    }

    /**
     * 发送topic消息
     *
     * @param obj 实现序列化对象
     */
    public void sendTopic(Serializable obj) {
        this.sendTopic(DEFAULT_TOPIC_NAME, obj);
    }
}
