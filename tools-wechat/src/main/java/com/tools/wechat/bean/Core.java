package com.tools.wechat.bean;

import com.alibaba.fastjson.JSONObject;
import com.tools.wechat.enums.RequestParaEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public class Core {

    private static final ThreadLocal<Core> threadLocal = new ThreadLocal<>();

    private Core() {
    }

    public static Core getInstance() {
        if (threadLocal.get() == null) {
            synchronized (Core.class) {
                Core core = new Core();
                threadLocal.set(core);
                return core;
            }
        }
        return threadLocal.get();
    }

    boolean alive = false;
    private int memberCount = 0;

    private String userName;
    private String nickName;
    private List<JSONObject> msgList = new ArrayList<>();

    private JSONObject userSelf; // 登陆账号自身信息
    private List<JSONObject> memberList = new ArrayList<>(); // 好友+群聊+公众号+特殊账号
    private List<JSONObject> contactList = new ArrayList<>();
    // 好友
    private List<JSONObject> groupList = new ArrayList<>();
    // 群
    private List<JSONObject> groupMemeberList = new ArrayList<>();
    ; // 群聊成员字典
    private List<JSONObject> publicUsersList = new ArrayList<>();
    // 公众号／服务号
    private List<JSONObject> specialUsersList = new ArrayList<>();
    // 特殊账号
    private List<String> groupIdList = new ArrayList<>();
    private Map<String, JSONObject> userInfoMap = new HashMap<>();

    Map<String, Object> loginInfo = new HashMap<>();

    String uuid = null;

    boolean useHotReload = false;
    String hotReloadDir = "itchat.pkl";
    int receivingRetryCount = 5;

    /**
     * 请求参数
     */
    public Map<String, Object> getParamMap() {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> loginMap = getLoginInfo();
        for (RequestParaEnum baseRequest : RequestParaEnum.values()) {
            map.put(baseRequest.getParam(), loginMap.get(baseRequest.getValue()));
        }
        Map<String, Object> result = new HashMap<>();
        result.put("BaseRequest", map);
        return result;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public List<JSONObject> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<JSONObject> memberList) {
        this.memberList = memberList;
    }

    public Map<String, Object> getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(Map<String, Object> loginInfo) {
        this.loginInfo = loginInfo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isUseHotReload() {
        return useHotReload;
    }

    public void setUseHotReload(boolean useHotReload) {
        this.useHotReload = useHotReload;
    }

    public String getHotReloadDir() {
        return hotReloadDir;
    }

    public void setHotReloadDir(String hotReloadDir) {
        this.hotReloadDir = hotReloadDir;
    }

    public int getReceivingRetryCount() {
        return receivingRetryCount;
    }

    public void setReceivingRetryCount(int receivingRetryCount) {
        this.receivingRetryCount = receivingRetryCount;
    }


    public List<JSONObject> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<JSONObject> msgList) {
        this.msgList = msgList;
    }

    public List<String> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<String> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public List<JSONObject> getContactList() {
        return contactList;
    }

    public void setContactList(List<JSONObject> contactList) {
        this.contactList = contactList;
    }

    public List<JSONObject> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<JSONObject> groupList) {
        this.groupList = groupList;
    }

    public List<JSONObject> getGroupMemeberList() {
        return groupMemeberList;
    }

    public void setGroupMemeberList(List<JSONObject> groupMemeberList) {
        this.groupMemeberList = groupMemeberList;
    }

    public List<JSONObject> getPublicUsersList() {
        return publicUsersList;
    }

    public void setPublicUsersList(List<JSONObject> publicUsersList) {
        this.publicUsersList = publicUsersList;
    }

    public List<JSONObject> getSpecialUsersList() {
        return specialUsersList;
    }

    public void setSpecialUsersList(List<JSONObject> specialUsersList) {
        this.specialUsersList = specialUsersList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public JSONObject getUserSelf() {
        return userSelf;
    }

    public void setUserSelf(JSONObject userSelf) {
        this.userSelf = userSelf;
    }

    public Map<String, JSONObject> getUserInfoMap() {
        return userInfoMap;
    }

    public void setUserInfoMap(Map<String, JSONObject> userInfoMap) {
        this.userInfoMap = userInfoMap;
    }

}
