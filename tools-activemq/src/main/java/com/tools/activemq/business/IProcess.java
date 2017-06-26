package com.tools.activemq.business;


import com.alibaba.fastjson.JSONObject;
import com.tools.activemq.exception.MessageException;

/**
 * @author ：cheng.zhencai  
 * @date ：2017/04/30
 * @description : 消息处处理接口
 */

public interface IProcess {
    /**
     * 消息处理方法
     *
     * @param message 消息
     * @throws MessageException
     */
    void process(JSONObject message) throws MessageException;
}
