package com.tools.message.business;


import com.tools.message.model.Email;
import com.tools.message.util.ConfigProperties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/7
 * @description :
 */
public class EmailSender {
    /**
     * 发送text文本邮件
     *
     * @param email 邮件
     * @return 是否发送成功
     */
    public static boolean sendTextMail(Email email) {
        try {
            Message mailMessage = getMessage();
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(email.getSendAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(email.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = email.getContent();
            mailMessage.setText(mailContent);
            if (email.getAttachFileNames() != null && email.getAttachFileNames().length > 0) {
                //附件
                Multipart multipart = new MimeMultipart();
                for (int i = 0; i < email.getAttachFileNames().length; i++) {
                    DataSource source = new FileDataSource(email.getAttachFileNames()[i]);
                    BodyPart bodyPart = new MimeBodyPart();
                    bodyPart.setDataHandler(new DataHandler(source));
                    String[] ss = email.getAttachFileNames()[i].split("/");
                    bodyPart.setFileName(ss[ss.length - 1]);
                    multipart.addBodyPart(bodyPart);
                }
                BodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(email.getContent(), "text/html");
                multipart.addBodyPart(bodyPart);
                mailMessage.setContent(multipart);
            }

            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 以HTML格式发送邮件
     *
     * @param email 待发送的邮件信息
     */
    public static boolean sendHtmlMail(Email email) {
        try {
            Message mailMessage = getMessage();
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address to = new InternetAddress(email.getSendAddress());
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 设置邮件消息的主题
            mailMessage.setSubject(email.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(email.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            if (email.getAttachFileNames() != null && email.getAttachFileNames().length > 0) {
                //附件
                BodyPart bodyPart = new MimeBodyPart();
                Multipart multipart = new MimeMultipart();
//                for(int i=0;i<email.getAttachFileNames().length;i++) {
                DataSource source = new FileDataSource(email.getAttachFileNames()[0]);
                bodyPart.setDataHandler(new DataHandler(source));
                bodyPart.setFileName(email.getAttachFileNames()[0]);
                multipart.addBodyPart(bodyPart);
//                }
                mailMessage.setContent(multipart);
            }


            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 创建邮件基础信息
     *
     * @return 邮件消息
     */
    private static Message getMessage() {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties p = new Properties();
        p.put("mail.smtp.host", ConfigProperties.MAIL_SERVER_HOST);
        p.put("mail.smtp.port", ConfigProperties.MAIL_SERVER_PORT);
        p.put("mail.smtp.auth", ConfigProperties.MAIL_SERVER_AUTH);
        //如果需要身份认证，则创建一个密码验证器
        if (ConfigProperties.MAIL_SERVER_AUTH) {
            authenticator = new MyAuthenticator(ConfigProperties.MAIL_USER, ConfigProperties.MAIL_PASSWORD);
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(p, authenticator);
        // 根据session创建一个邮件消息
        Message mailMessage = new MimeMessage(sendMailSession);
        try {
            // 创建邮件发送者地址
            Address from = new InternetAddress(ConfigProperties.MAIL_FORM_ADDRESS);
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return mailMessage;
    }
}
