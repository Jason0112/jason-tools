package com.tools.activemq.consumer.dispatcher;

import com.alibaba.fastjson.JSONObject;
import com.tools.activemq.exception.MessageException;
import com.tools.activemq.business.MessageProcessFactory;
import com.tools.activemq.business.IProcess;
import com.tools.activemq.enums.MessageEventEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author ：cheng.zhencai  
 * @date ：2015/10/23
 * @description : 分发处理消息
 */

public class MessageDispatcher {

    private static Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

    /**
     * 多线程处理队列消息，暂不启动关闭线程池功能
     */
    private static ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * 分发器
     *
     * @param message 消息
     * @throws MessageException
     */
    public static void dispatcher(final JSONObject message) throws MessageException {
        logger.info("进入分发器..........");

        /**根据事件类型，获取事件对应自定的枚举*/
        MessageEventEnum eventEnum = MessageEventEnum.getEventEnumByType(message.getString("type"));

        if (eventEnum == null) {
            logger.error("消息：{} 没找到对应事件.", message.toString());
            return;
        }

        logger.info("........查找{}事件处理程序.........", eventEnum.getType());

        final IProcess finalProcess;
        try {
            finalProcess = MessageProcessFactory.getMessageProcess(eventEnum);
            logger.info("........匹配完处处理程序.........");
        } catch (Exception e) {
            logger.info("获取到消息处理程序导常.........", e.toString());
            e.printStackTrace();
            return;
        }

        if (finalProcess == null) {
            logger.info("没找到消息处理程序.........");
            return;
        }
        logger.info("消息进入多线程处理.........");
        executorService.submit(() -> {
            try {
                finalProcess.process(message);
            } catch (MessageException e) {
                logger.error(e.getMessage());
            }
        });

    }
}
