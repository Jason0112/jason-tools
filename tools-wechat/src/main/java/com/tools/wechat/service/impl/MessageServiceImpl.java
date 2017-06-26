package com.tools.wechat.service.impl;

import com.tools.wechat.bean.Message;
import com.tools.wechat.enums.MessageEnum;
import com.tools.wechat.service.MessageService;
import com.tools.wechat.service.SendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    SendService sendService;

    @Override
    public String send(Message message) {
        MessageEnum messageEnum = MessageEnum.getEnumByCode(message.getCode());
        logger.info("消息:{},类型:{}", message, messageEnum);
        switch (messageEnum) {
            case TEXT:
                sendService.sendMsgByNickName(message.getNickName(), message.getContent());
                break;
            case IMAGE:
            case EMOTICON:
                sendService.sendPicMsgByNickName(message.getNickName(), message.getFilePath());
                break;
            case VOICE:
            case VIDEO:
                sendService.sendFileMsgByNickName(message.getNickName(), message.getFilePath());
                break;
            default:
        }
        return null;
    }


}
