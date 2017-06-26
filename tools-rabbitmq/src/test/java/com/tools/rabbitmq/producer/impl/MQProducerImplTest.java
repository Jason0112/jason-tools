package com.tools.rabbitmq.producer.impl;

import com.tools.rabbitmq.common.CommonTest;
import com.tools.rabbitmq.producer.MQProducer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * date: 2017/5/21
 * description :
 *
 * @author : zhencai.cheng
 */
public class MQProducerImplTest extends CommonTest {

    @Before
    public void setUp() throws Exception {

    }

    @Autowired
    MQProducer mqProducer;

    final String queue_key = "test_queue_key";

    @Test
    public void send() {
        Map<String, Object> msg = new HashMap<>();
        msg.put("data", "hello,rabbmit mq!");
        mqProducer.sendQueue(queue_key, msg);
    }
}