package com.tools.kafka.producer.business.impl;

import com.tools.kafka.enums.TopicTypeEnum;
import com.tools.kafka.exception.TopicSendException;
import com.tools.kafka.producer.business.IUserTopicService;
import com.tools.kafka.producer.common.SendTopic;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :此设计可以根据自己的业务需求
 */
@Service
public class UserTopicServiceImpl extends SendTopic implements IUserTopicService {

    @Override
    public void publishTopic(Map<String, String> data, String action) throws TopicSendException {
        Map<String, String> publishData = this.processData(data);
        this.sendTopic(publishData, TopicTypeEnum.USER, action);
    }

    /**
     * 根据自己的业务处理数据
     *
     * @param data 请求数据
     * @return 处理完成的数据
     */
    private Map<String, String> processData(Map<String, String> data) {
        return data;
    }
}
