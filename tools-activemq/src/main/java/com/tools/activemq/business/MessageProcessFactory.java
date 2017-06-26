package com.tools.activemq.business;

import com.tools.activemq.util.SpringUtil;
import com.tools.activemq.enums.MessageEventEnum;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :
 */
public class MessageProcessFactory {


    public static IProcess getMessageProcess(MessageEventEnum eventEnum) {

        switch (eventEnum) {
            case USER:
                return (IProcess) SpringUtil.getBeanObj("userProcessImpl");
            default:

        }
        return null;
    }
}
