package com.tools.message.business;

import com.tools.message.common.CommonTest;
import com.tools.message.model.Email;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/7
 * @description :
 */
public class EmailSenderTest extends CommonTest {

    private Email email;

    @Before
    public void setUp() {
        email = new Email();
        email.setContent("在 2016年12月1日上午 3:49 你的 Facebook 密码已通过邮箱 jason0112x@163.com 重置。");
        email.setSendAddress("jason0112x@163.com");
        email.setSubject("测试邮件");
    }

    @Test
    public void testSendTextMail() throws Exception {
        boolean result =EmailSender.sendTextMail(email);
        System.out.println(result);
    }

    @Test
    public void testSendHtmlMail() throws Exception {
        email.setContent("<h1 style=\"color: red;font-size:40px ;margin-left: 90px\">ontentaaaaa</h1></br><h2>jfdkajf</h2>");
        boolean result = EmailSender.sendHtmlMail(email);
        System.out.println(result);
    }

}