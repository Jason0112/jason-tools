package com.tools.wechat.service;

/**
 * date: 2017/6/7
 * description :
 *
 * @author : zhencai.cheng
 */
public interface SendService {

    void sendMsgById(String text, String id);

    boolean sendMsgByNickName(String nickName, String message);
    
    boolean sendPicMsgByNickName(String nickName, String filePath);

    boolean sendFileMsgByNickName(String nickName, String filePath);
}
