package com.tools.wechat.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tools.wechat.bean.Core;
import com.tools.wechat.enums.MessageEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public class MessageUtil {

    private static Logger LOG = LoggerFactory.getLogger(MessageUtil.class);

    private static Core core = Core.getInstance();

    private static Pattern pattern = Pattern.compile("(.+?\\(.+?\\))");

    /**
     * 接收消息，放入队列
     *
     * @param msgList
     * @return
     */
    public static JSONArray produceMsg(JSONArray msgList) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < msgList.size(); i++) {
            JSONObject msg = new JSONObject();
            JSONObject m = msgList.getJSONObject(i);
            m.put("groupMsg", false);// 是否是群消息
            if (m.getString("FromUserName").contains("@@") || m.getString("ToUserName").contains("@@")) { // 群聊消息
                if (m.getString("FromUserName").contains("@@")
                        && !core.getGroupIdList().contains(m.getString("FromUserName"))) {
                    core.getGroupIdList().add((m.getString("FromUserName")));
                } else if (m.getString("ToUserName").contains("@@")
                        && !core.getGroupIdList().contains(m.getString("ToUserName"))) {
                    core.getGroupIdList().add((m.getString("ToUserName")));
                }
                // 群消息与普通消息不同的是在其消息体（Content）中会包含发送者id及":<br/>"消息，这里需要处理一下，去掉多余信息，只保留消息内容
                if (m.getString("Content").contains("<br/>")) {
                    String content = m.getString("Content").substring(m.getString("Content").indexOf("<br/>") + 5);
                    m.put("Content", content);
                    m.put("groupMsg", true);
                }
            } else {
                ToolsUtil.msgFormatter(m, "Content");
            }
            if (m.getInteger("MsgType") == MessageEnum.TEXT.getCode()) { // words
                // 文本消息
                if (m.getString("Url").length() != 0) {

                    Matcher matcher = ToolsUtil.getMatcher(pattern, m.getString("Content"));
                    String data = "Map";
                    if (matcher.find()) {
                        data = matcher.group(1);
                    }
                    msg.put("Type", "Map");
                    msg.put("Text", data);
                } else {
                    msg.put("Text", m.getString("Content"));
                }
                m.put("Type", msg.getString("Type"));
                m.put("Text", msg.getString("Text"));
            } else {
                LOG.info("Useless msg");
            }
            LOG.info("收到消息一条，来自: {} ", m.getString("FromUserName"));
            result.add(m);
        }
        return result;
    }

}
