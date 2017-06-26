package com.tools.message.service.impl;

import com.tools.message.common.CommonTest;
import com.tools.message.exception.SendException;
import com.tools.message.model.SMS;
import com.tools.message.service.IMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description :
 */
public class EmailServiceImplTest extends CommonTest {

    @Autowired
    IMessageService messageService;

    @Test
    public void testSend() throws SendException {
        SMS sms = new SMS();
        sms.setAppKey("appKey");
        sms.setContent("测试");
        sms.setMobile("18201299161");
        messageService.sendMessages(sms);
    }
}