package com.tools.kafka.producer.common;

import com.tools.kafka.exception.TopicSendException;

import java.util.Map;

/**
 * @author  : chengzhencai
 * @date : 2017/4/18
 * @description :topic公共接口
 */
public interface ITopicService {

    void publishTopic(Map<String, String> data, String action) throws TopicSendException;
}
