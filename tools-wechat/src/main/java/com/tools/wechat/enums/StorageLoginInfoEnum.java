package com.tools.wechat.enums;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public enum StorageLoginInfoEnum {

    //URL
    url("url", ""),
    fileUrl("fileUrl", ""),
    syncUrl("syncUrl", ""),

    deviceid("deviceid", ""), //生成15位随机数

    //baseRequest
    skey("skey", ""),
    wxsid("wxsid", ""),
    wxuin("wxuin", ""),
    pass_ticket("pass_ticket", ""),


    InviteStartCount("InviteStartCount", 0),
    User("User", new JSONObject()),
    SyncKey("SyncKey", new JSONObject()),
    synckey("synckey", ""),


    MemberCount("MemberCount", ""),
    MemberList("MemberList", new JSONArray());

    private String key;
    private Object type;

    StorageLoginInfoEnum(String key, Object type) {
        this.key = key;
        this.type = type;
    }

    public String getKey() {
        return key;
    }


    public Object getType() {
        return type;
    }
}
