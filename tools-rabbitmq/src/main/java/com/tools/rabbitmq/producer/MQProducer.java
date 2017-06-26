package com.tools.rabbitmq.producer;

/**
 * date: 2017/5/21
 * description :
 *
 * @author : zhencai.cheng
 */
public interface MQProducer {

    /**
     * 发送消息到指定队列
     *
     * @param queueKey 队列key
     * @param object   发送对象
     */
    void sendQueue(String queueKey, Object object);
}
