package com.tools.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tools.wechat.enums.URLEnum;
import com.tools.wechat.bean.Core;
import com.tools.wechat.enums.MessageEnum;
import com.tools.wechat.service.SendService;
import com.tools.wechat.util.Config;
import com.tools.wechat.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * date: 2017/6/7
 * description :
 *
 * @author : zhencai.cheng
 */
@Service
public class SendServiceImpl implements SendService {
    private static final Logger LOG = LoggerFactory.getLogger(SendServiceImpl.class);
    private Core core = Core.getInstance();


    /**
     * 根据ID发送文本消息
     *
     * @param text
     * @param id
     */
    @Override
    public void sendMsgById(String text, String id) {
        sendMsg(text, id);
    }

    /**
     * 根据NickName发送文本消息
     *
     * @param nickName
     * @param text
     * @return
     */
    @Override
    public boolean sendMsgByNickName(String nickName, String text) {
        if (nickName != null) {
            String toUserName = getUserNameByNickName(nickName);
            if (toUserName != null) {
                webWxSendMsg(MessageEnum.TEXT.getCode(), text, toUserName);
                return true;
            }
        }
        return false;
    }

    /**
     * 消息发送
     *
     * @param msgType
     * @param content
     * @param toUserName
     */
    private void webWxSendMsg(int msgType, String content, String toUserName) {
        String url = String.format(URLEnum.WEB_WX_SEND_MSG.getUrl(), core.getLoginInfo().get("url"));
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("Type", msgType);
        msgMap.put("Content", content);
        msgMap.put("FromUserName", core.getUserName());
        msgMap.put("ToUserName", toUserName == null ? core.getUserName() : toUserName);
        msgMap.put("LocalID", new Date().getTime() * 10);
        msgMap.put("ClientMsgId", new Date().getTime() * 10);
        Map<String, Object> paramMap = core.getParamMap();
        paramMap.put("Msg", msgMap);
        paramMap.put("Scene", 0);
        try {
            HttpUtil.doPost(url, paramMap);
        } catch (Exception e) {
            LOG.error("webWxSendMsg", e);
        }
    }

    /**
     * 根据NickName发送图片消息
     *
     * @param nickName
     * @param filePath
     * @return
     */
    @Override
    public boolean sendPicMsgByNickName(String nickName, String filePath) {
        String toUserName = getUserNameByNickName(nickName);
        return toUserName != null && sendPicMsgByUserId(toUserName, filePath);
    }

    /**
     * 根据用户id发送图片消息
     *
     * @param userId
     * @param filePath
     * @return
     */
    private boolean sendPicMsgByUserId(String userId, String filePath) {
        JSONObject responseObj = webWxUploadMedia(filePath);
        if (responseObj != null) {
            String mediaId = responseObj.getString("MediaId");
            if (mediaId != null) {
                return webWxSendMsgImg(userId, mediaId);
            }
        }
        return false;
    }

    /**
     * 发送图片消息，内部调用
     *
     * @param userId
     * @param mediaId
     * @return
     */
    private boolean webWxSendMsgImg(String userId, String mediaId) {
        String url = String.format("%s/webwxsendmsgimg?fun=async&f=json&pass_ticket=%s", core.getLoginInfo().get("url"),
                core.getLoginInfo().get("pass_ticket"));
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("Type", 3);
        msgMap.put("MediaId", mediaId);
        msgMap.put("FromUserName", core.getUserSelf().getString("UserName"));
        msgMap.put("ToUserName", userId);
        String clientMsgId = String.valueOf(new Date().getTime())
                + String.valueOf(new Random().nextLong()).substring(1, 5);
        msgMap.put("LocalID", clientMsgId);
        msgMap.put("ClientMsgId", clientMsgId);
        Map<String, Object> paramMap = core.getParamMap();
        paramMap.put("BaseRequest", core.getParamMap().get("BaseRequest"));
        paramMap.put("Msg", msgMap);
        try {
            String result = HttpUtil.doPost(url, paramMap);
            return JSON.parseObject(result).getJSONObject("BaseResponse").getInteger("Ret") == 0;
        } catch (Exception e) {
            LOG.error("webWxSendMsgImg 错误： ", e);
        }
        return false;

    }

    /**
     * 根据用户id发送文件
     *
     * @param userId
     * @param filePath
     * @return
     */
    private boolean sendFileMsgByUserId(String userId, String filePath) {
        String title = new File(filePath).getName();
        Map<String, String> data = new HashMap<>();
        data.put("appid", Config.API_WXAPPID);
        data.put("title", title);
        data.put("totallen", "");
        data.put("attachid", "");
        data.put("type", "6"); // APPMSGTYPE_ATTACH
        data.put("fileext", title.split("\\.")[1]); // 文件后缀
        JSONObject responseObj = webWxUploadMedia(filePath);
        if (responseObj != null) {
            data.put("totallen", responseObj.getString("StartPos"));
            data.put("attachid", responseObj.getString("MediaId"));
        } else {
            LOG.error("sednFileMsgByUserId 错误: ", data);
        }
        return webWxSendAppMsg(userId, data);
    }

    /**
     * 根据用户昵称发送文件消息
     *
     * @param nickName
     * @param filePath
     * @return
     */
    @Override
    public boolean sendFileMsgByNickName(String nickName, String filePath) {
        String toUserName = getUserNameByNickName(nickName);
        if (toUserName != null) {
            return sendFileMsgByUserId(toUserName, filePath);
        }
        return false;
    }

    /**
     * 根据UserName发送文本消息
     *
     * @param text
     * @param toUserName
     */
    private void sendMsg(String text, String toUserName) {
        LOG.info("发送消息 {}: {}", toUserName, text);
        webWxSendMsg(MessageEnum.TEXT.getCode(), text, toUserName);
    }

    /**
     * 通过RealName获取本次UserName
     * 如NickName为"yaphone"，则获取UserName=
     * "@1212d3356aea8285e5bbe7b91229936bc183780a8ffa469f2d638bf0d2e4fc63"，
     * 可通过UserName发送消息
     *
     * @param nickName
     * @return
     */
    private String getUserNameByNickName(String nickName) {
        for (JSONObject o : core.getContactList()) {
            if (o.getString("NickName").equals(nickName)) {
                return o.getString("UserName");
            }
        }
        return null;
    }

    /**
     * 上传多媒体文件到 微信服务器，目前应该支持3种类型:
     * <p>
     * 1. pic 直接显示，包含图片，表情
     * 2.video
     * 3.doc 显示为文件，包含PDF等
     * </p>
     *
     * @param filePath
     * @return
     */
    private JSONObject webWxUploadMedia(String filePath) {

        String url = String.format(URLEnum.WEB_WX_UPLOAD_MEDIA.getUrl(), core.getLoginInfo().get("fileUrl"));
        try {
            String result = HttpUtil.doPostFile(url, filePath);
            return JSON.parseObject(result);
        } catch (Exception e) {
            LOG.error("webWxUploadMedia 错误： ", e);
        }
        return null;
    }

    /**
     * 内部调用
     *
     * @param userId
     * @param data
     * @return
     */
    private boolean webWxSendAppMsg(String userId, Map<String, String> data) {
        String url = String.format("%s/webwxsendappmsg?fun=async&f=json&pass_ticket=%s", core.getLoginInfo().get("url"),
                core.getLoginInfo().get("pass_ticket"));
        String clientMsgId = String.valueOf(new Date().getTime())
                + String.valueOf(new Random().nextLong()).substring(1, 5);
        String content = "<appmsg appid='wxeb7ec651dd0aefa9' sdkver=''><title>" + data.get("title")
                + "</title><des></des><action></action><type>6</type><content></content><url></url><lowurl></lowurl>"
                + "<appattach><totallen>" + data.get("totallen") + "</totallen><attachid>" + data.get("attachid")
                + "</attachid><fileext>" + data.get("fileext") + "</fileext></appattach><extinfo></extinfo></appmsg>";
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("Type", data.get("type"));
        msgMap.put("Content", content);
        msgMap.put("FromUserName", core.getUserSelf().getString("UserName"));
        msgMap.put("ToUserName", userId);
        msgMap.put("LocalID", clientMsgId);
        msgMap.put("ClientMsgId", clientMsgId);
        /*
         * Map<String, Object> paramMap = new HashMap<String, Object>();
		 *
		 * @SuppressWarnings("unchecked") Map<String, Map<String, String>>
		 * baseRequestMap = (Map<String, Map<String, String>>)
		 * core.getLoginInfo() .get("baseRequest"); paramMap.put("BaseRequest",
		 * baseRequestMap.get("BaseRequest"));
		 */

        Map<String, Object> paramMap = core.getParamMap();
        paramMap.put("Msg", msgMap);
        paramMap.put("Scene", 0);
        try {
            String result = HttpUtil.doPost(url, paramMap);
            return JSON.parseObject(result).getJSONObject("BaseResponse").getInteger("Ret") == 0;
        } catch (Exception e) {
            LOG.error("错误: ", e);
        }
        return false;
    }
}
