package com.tools.activemq.consumer.converter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.activemq.consumer.dispatcher.MessageDispatcher;
import com.tools.activemq.exception.MessageException;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.IOException;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :
 */
public class MQMessageConverter implements MessageConverter {


    protected static final Logger logger = LoggerFactory.getLogger(MQMessageConverter.class);
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * 继承实现消息格式转换
     *
     * @param message 消息
     * @return 返回消息体
     * @throws JMSException
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        if (logger.isDebugEnabled()) {
            logger.debug("接收 JMS 消息:" + message);
        }
        if (message instanceof ObjectMessage) {
            ObjectMessage oMsg = (ObjectMessage) message;
            if (oMsg instanceof ActiveMQObjectMessage) {
                ActiveMQObjectMessage aMsg = (ActiveMQObjectMessage) oMsg;
                try {
                    String model =mapper.writeValueAsString(aMsg.getObject());
                    logger.info("message:" + model.toString());
                    this.handler(model);
                    return model;
                } catch (Exception e) {
                    logger.error("消息:{}不是模型的一个实例.", message.toString());
                    throw new JMSException("消息:{}不是model的一个实例.", message.toString());
                }
            } else {
                logger.error("消息: {} 不是 ActiveMQObjectMessage 的一个实例.", message.toString());
                throw new JMSException("消息: {} 不是 ActiveMQObjectMessage 的一个实例.", message.toString());
            }
        } else {
            logger.error("消息: {} 不是 ObjectMessage 的一个实例..", message.toString());
            throw new JMSException("消息: {} 不是 ObjectMessage 的一个实例..", message.toString());
        }
    }

    /***
     * 转换对象到消息
     *
     * @param obj     消息对象
     * @param session active session
     * @return 消息
     * @throws JMSException
     * @throws MessageConversionException
     */
    @Override
    public Message toMessage(Object obj, Session session) throws JMSException, MessageConversionException {
        if (logger.isDebugEnabled()) {
            logger.debug("将通知对象转换为 JMS 消息: {}", obj.toString());
        }
        ActiveMQObjectMessage msg = (ActiveMQObjectMessage) session.createObjectMessage();
        msg.setObject(String.valueOf(obj));
        return msg;
    }

    /**
     * 消息处理分发
     *
     * @param message 消息
     * @throws MessageException
     */
    private void handler(String message) throws MessageException {
        try {
            JSONObject json = mapper.readValue(message, JSONObject.class);
            MessageDispatcher.dispatcher(json);
        } catch (IOException e) {
            logger.error("********************************转换异常**********************************");
            throw new MessageException("转换异常", e);
        } catch (Exception e) {
            logger.error("********************************消息分发失败**********************************");
            throw new MessageException("消息分发失败", e);
        }
        logger.info("消息分发成功...........");
    }
}
