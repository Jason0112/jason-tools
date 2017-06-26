package com.tools.wechat.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public class Config {

    public static final String API_WXAPPID = "API_WXAPPID";

    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";

    public static final ArrayList<String> API_SPECIAL_USER = new ArrayList<String>(Arrays.asList("filehelper", "weibo",
            "qqmail", "fmessage", "tmessage", "qmessage", "qqsync", "floatbottle", "lbsapp", "shakeapp", "medianote",
            "qqfriend", "readerapp", "blogapp", "facebookapp", "masssendapp", "meishiapp", "feedsapp", "voip",
            "blogappweixin", "brandsessionholder", "weixin", "weixinreminder", "officialaccounts", "wxitil",
            "notification_messages", "wxid_novlwrv3lqwv11", "gh_22b87fa7cb3c", "userexperience_alarm"));
}
