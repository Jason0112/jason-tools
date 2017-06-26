package com.tools.wechat.service;

import com.tools.wechat.bean.Message;

/**
 * date: 2017/6/5
 * description :消息处理接口
 *
 * @author : zhencai.cheng
 */
@FunctionalInterface
public interface MessageService {

    /**
     * 处理消息
     *
     * @param message 消息
     * @return
     */
    String send(Message message);
}
