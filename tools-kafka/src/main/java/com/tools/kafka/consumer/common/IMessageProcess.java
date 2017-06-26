package com.tools.kafka.consumer.common;

import com.alibaba.fastjson.JSONObject;
import com.tools.kafka.exception.MessageProcessorException;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :消息处理公共接口
 */
public interface IMessageProcess {
    /**
     * 业务消息处理公共接口
     *
     * @param message 消息
     * @throws MessageProcessorException
     */
    void onMessage(JSONObject message) throws MessageProcessorException;
}
