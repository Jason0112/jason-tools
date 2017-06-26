package com.tools.message.service;

import com.tools.message.exception.SendException;
import com.tools.message.model.SMS;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description : 发送接口
 */
public interface IMessageService {
    /**
     * 发送短信
     *
     * @param message 短信
     */
    void sendMessages(SMS message) throws SendException;


}
