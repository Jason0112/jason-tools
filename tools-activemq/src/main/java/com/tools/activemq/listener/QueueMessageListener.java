package com.tools.activemq.listener;


import com.tools.activemq.consumer.converter.MQMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author ：cheng.zhencai  
 * @date ：2017/04/30
 * @description : 消息队列监听
 */
@Component
public class QueueMessageListener implements  MessageListener, InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);

    @Autowired
    DefaultMessageListenerContainer defaultMessageListenerContainer;
    /**
     * 消息转换器接口
     */
    @Autowired
    private MQMessageConverter messageConverter;

    /**
     * 接收消息
     *
     * @param message 消息
     */
    public void onMessage(Message message) {
        try {
            logger.info("接收到消息");
            messageConverter.fromMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("消息处理失败..................");
        }
    }

    /**
     * 设置监听
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        defaultMessageListenerContainer.setMessageListener(this);
    }
}