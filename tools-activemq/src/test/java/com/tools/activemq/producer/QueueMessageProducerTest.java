package com.tools.activemq.producer;

import com.tools.activemq.common.CommonTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/1
 * @description :
 */
public class QueueMessageProducerTest extends CommonTest {
    private Logger logger = LoggerFactory.getLogger(QueueMessageProducerTest.class);
    @Autowired
    QueueMessageProducer messageProducer;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSendTopic() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "user");
        map.put("process", "处理");
        messageProducer.sendTopic(map);
        logger.info("发送完毕`");
    }

    @Test
    public void testSendTopic1() throws Exception {

    }
}