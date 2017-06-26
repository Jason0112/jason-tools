package com.tools.activemq.business.impl;

import com.alibaba.fastjson.JSONObject;
import com.tools.activemq.exception.MessageException;
import com.tools.activemq.business.IUserProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :用户业务信息处理实现类
 */
@Service
public class UserProcessImpl implements IUserProcess {

    private static Logger logger = LoggerFactory.getLogger(UserProcessImpl.class);

    /**
     * 案例
     *
     * @param message 消息
     * @throws MessageException
     */
    @Override
    public void process(JSONObject message) throws MessageException {
        logger.info("用户处理业务逻辑:{}", message);
    }
}
