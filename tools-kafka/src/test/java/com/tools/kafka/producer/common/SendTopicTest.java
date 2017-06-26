package com.tools.kafka.producer.common;

import com.tools.kafka.common.CommonTest;
import com.tools.kafka.enums.TopicTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * date: 2017/5/26
 * description :
 *
 * @author : zhencai.cheng
 */
public class SendTopicTest extends CommonTest {

    @Autowired
    SendTopic sendTopic;

    private Map<String,String> params = new HashMap<>();

    @Before
    public void setUp(){
        params.put("companyName","测试企业");
        params.put("companyCity","8611");
        params.put("companyContactMobile","联系人");
        params.put("companySource","自测试");
    }

    @Test
    public void testSendTopic() throws Exception {
        sendTopic.sendTopic(params, TopicTypeEnum.COMPANY_FROM_DC,"create");
    }
}