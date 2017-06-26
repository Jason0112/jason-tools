package com.tools.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * date: 2017/5/21
 * description :
 *
 * @author : zhencai.cheng
 */
@Component
public class QueueListenter implements MessageListener, InitializingBean {

    @Autowired
    SimpleMessageListenerContainer messageListenerContainer;

    @Override
    public void onMessage(Message message) {
        try {
            System.out.print(message.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
//        acknowledge
        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        messageListenerContainer.setMessageListener(this);
    }
}
