package com.tools.message.service.impl;

import com.tools.message.enums.ThirdChannelEnum;
import com.tools.message.exception.SendException;
import com.tools.message.model.ChannelInfo;
import com.tools.message.model.Monitor;
import com.tools.message.model.SMS;
import com.tools.message.model.SignRecord;
import com.tools.message.service.IChannelService;
import com.tools.message.service.IMessageService;
import com.tools.message.service.IMonitorService;
import com.tools.message.service.ISignRecordService;
import com.tools.message.util.HttpUtil;
import com.tools.message.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description :
 */
@Service
public class MessageSerivceImpl implements IMessageService {

    private static Logger logger = LoggerFactory.getLogger(MessageSerivceImpl.class);
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private final static String LEFT_BRACKETS = "【";
    private final static String RIGHT_BRACKETS = "】";

    @Autowired
    IChannelService channelService;
    @Autowired
    ISignRecordService signRecordService;
    @Autowired
    IMonitorService monitorService;

    @Override
    public void sendMessages(SMS message) throws SendException {
        logger.info("send message .....");
        try {

            ThirdChannelEnum channelEnum = ThirdChannelEnum.getEnumByAppKey(message.getAppKey());
            Map<String, String> params = this.handlerRequestParameter(channelEnum, message);
            String result = HttpUtil.doPost(channelEnum.getHttp(), params);
            this.handlerResult(channelEnum, result, message, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SendException("测试异常");
        }
    }

    /**
     * 根据不同渠道处理请求参数
     *
     * @return 请求参数
     */
    private Map<String, String> handlerRequestParameter(ThirdChannelEnum channelEnum, SMS message) throws UnsupportedEncodingException, SendException {
        Map<String, String> reqParams = new HashMap<>();
        ChannelInfo info = channelService.findChannelInfoByAppKey(channelEnum.getAppKey());
        switch (channelEnum) {
            case HLQX:
                reqParams.put("username", info.getAccount());
                reqParams.put("password", MD5Util.MD5(info.getPassword()).toUpperCase());
                reqParams.put("phone", message.getMobile());
                reqParams.put("message", URLEncoder.encode(message.getContent(), "gb2312"));
                reqParams.put("epid", "122339");
                reqParams.put("linkid", UUID.randomUUID().toString());
                reqParams.put("subcode", getSignSubId(message.getContent()));
            case OTHER:
                break;
            default:
        }
        return reqParams;
    }

    /**
     * 获取签名 对应的扩展码
     *
     * @param content 短信内容
     * @return 扩展码
     * @throws SendException
     */
    private String getSignSubId(String content) throws SendException {
        try {
            if (content.contains(LEFT_BRACKETS) && content.contains(RIGHT_BRACKETS)) {
                String sign = content.substring(content.indexOf(LEFT_BRACKETS), content.indexOf(RIGHT_BRACKETS) + 1);
                SignRecord signRecord = signRecordService.getSignRecordByName(sign);
                if (signRecord == null) {
                    throw new SendException("短信签名未报备,需要在短信页面添加签名");
                }
                return signRecord.getId() + "";
            }
        } catch (SendException e) {
            throw new SendException(e.getMessage());
        }
        return "";
    }


    /**
     * 解析结果
     *
     * @param channelEnum 渠道
     * @param result      结果
     */
    private void handlerResult(ThirdChannelEnum channelEnum, String result,
                               SMS message, Map<String, String> reqParams) {
        Monitor monitor = new Monitor();
        monitor.setContent(message.getContent());
        monitor.setResult(result);
        switch (channelEnum) {
            case HLQX:
                monitor.setAccount(reqParams.get("username"));
                monitor.setMessageId(reqParams.get("linkid"));
                break;
            case OTHER:
                break;
            default:
        }

        Date now = new Date();
        monitor.setCreateTime(now);
        monitor.setUpdateTime(now);
        this.saveSendResult(monitor);
    }

    /**
     * 保存结果
     *
     * @param monitor 发送结果
     */
    private void saveSendResult(Monitor monitor) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                monitorService.insert(monitor);
            }
        });
    }
}
