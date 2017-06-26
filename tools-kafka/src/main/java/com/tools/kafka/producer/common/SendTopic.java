package com.tools.kafka.producer.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tools.kafka.enums.TopicTypeEnum;
import com.tools.kafka.exception.TopicSendException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : zhencai.chengcai
 * @date : 2017/4/18
 * @description :发送topic
 */
@Component
public class SendTopic {

    private static final Logger logger = LoggerFactory.getLogger(SendTopic.class);
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private ThreadPoolTaskExecutor executorService;

    /**
     * topic 处理分发
     *
     * @param data   请求参数
     * @param e      发送类型(哪种topic主题)
     * @param action 操作类型
     * @throws TopicSendException
     */
    public void sendTopic(Map<String, String> data, TopicTypeEnum e, String action) throws TopicSendException {
        if (!e.isFlag()) {
            return;
        }
        data.put("action", action);
        executorService.execute(() -> {
            String message = JSON.toJSONString(data, SerializerFeature.WriteDateUseDateFormat);
            if (e.getParameter() == null) {
                logger.info("主题名称:{},信息:{}", e.getTopicName(), message);
                kafkaTemplate.send(e.getTopicName(), message);
            } else {
                logger.info("主题名称:{},参数:{},信息:{}", e.getTopicName(), data.get(e.getParameter()), message);
                kafkaTemplate.send(e.getTopicName(), data.get(e.getParameter()), message);
            }
            if (logger.isInfoEnabled()) {
                logger.info("{}--向kafka发布主题[{}]成功，内容为：[{}]", e.getName(), e.getTopicName(), message);
            }
        });
    }
}
