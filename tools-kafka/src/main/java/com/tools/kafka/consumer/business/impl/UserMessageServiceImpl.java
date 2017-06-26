package com.tools.kafka.consumer.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.tools.kafka.exception.MessageProcessorException;
import com.tools.kafka.consumer.business.IUserMessageService;
import org.springframework.stereotype.Service;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :
 */
@Service
public class UserMessageServiceImpl implements IUserMessageService {

    @Override
    public void onMessage(JSONObject message) throws MessageProcessorException {
        System.out.println(message);
    }
}
