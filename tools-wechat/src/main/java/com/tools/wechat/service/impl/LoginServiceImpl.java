package com.tools.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tools.wechat.bean.Core;
import com.tools.wechat.enums.LoginEnum;
import com.tools.wechat.enums.RequestParaEnum;
import com.tools.wechat.enums.ResultEnum;
import com.tools.wechat.enums.RetCodeEnum;
import com.tools.wechat.enums.StorageLoginInfoEnum;
import com.tools.wechat.enums.URLEnum;
import com.tools.wechat.enums.UUIDParaEnum;
import com.tools.wechat.util.Config;
import com.tools.wechat.util.ToolsUtil;
import com.tools.wechat.enums.StatusNotifyParaEnum;
import com.tools.wechat.exception.HttpException;
import com.tools.wechat.service.LoginService;
import com.tools.wechat.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);
    private Core core = Core.getInstance();

    private static final Pattern PATTERN = Pattern.compile("window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";");
    private static final Pattern LOGIN_PATTERN = Pattern.compile("window.redirect_uri=\"(\\S+)\";");
    private static final Pattern CHECK_LOGIN_PATTERN = Pattern.compile("window.code=(\\d+)");
    private static final Pattern CHECK_MESSAGE_PATTERN = Pattern.compile("window.synccheck=\\{retcode:\"(\\d+)\",selector:\"" +
            "(\\d+)\"\\}");

    @Override
    public boolean login() {
        // 组装参数和URL
        Map<String, Object> params = new HashMap<>();
        params.put(LoginEnum.LOGIN_ICON.para(), LoginEnum.LOGIN_ICON.value());
        params.put(LoginEnum.UUID.para(), core.getUuid());
        params.put(LoginEnum.TIP.para(), LoginEnum.TIP.value());
        // long time = 4000;
        while (true) {
            // SleepUtils.sleep(time += 1000);
            long millis = System.currentTimeMillis();
            params.put(LoginEnum.R.para(), String.valueOf(millis / 1579L));
            params.put(LoginEnum.OTHER.para(), String.valueOf(millis));
            try {
                String result = HttpUtil.doGet(URLEnum.LOGIN_URL.getUrl(), params);
                String status = checkLogin(result);
                if (ResultEnum.SUCCESS.getCode().equals(status)) {
                    processLoginInfo(result); // 处理结果
                    core.setAlive(true);
                    return true;
                }
                if (ResultEnum.WAIT_CONFIRM.getCode().equals(status)) {
                    LOGGER.info("请点击微信确认按钮，进行登陆");
                }

            } catch (Exception e) {
                LOGGER.error("微信登陆异常！", e);
            }
        }
    }

    @Override
    public String getUuid() {
        // 组装参数和URL
        Map<String, Object> params = new HashMap<>();
        params.put(UUIDParaEnum.APP_ID.para(), UUIDParaEnum.APP_ID.value());
        params.put(UUIDParaEnum.FUN.para(), UUIDParaEnum.FUN.value());
        params.put(UUIDParaEnum.LANG.para(), UUIDParaEnum.LANG.value());
        params.put(UUIDParaEnum.TIMESTAMP.para(), String.valueOf(System.currentTimeMillis()));
        try {
            String result = HttpUtil.doGet(URLEnum.UUID_URL.getUrl(), params);
            Matcher matcher = ToolsUtil.getMatcher(PATTERN, result);
            if (matcher.find()) {
                if ((ResultEnum.SUCCESS.getCode().equals(matcher.group(1)))) {
                    core.setUuid(matcher.group(2));
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return core.getUuid();
    }

    @Override
    public boolean getQR() {
        String qrPath = LoginServiceImpl.class.getResource("/").getFile() + File.separator + "QR.jpg";
        String qrUrl = URLEnum.QRCODE_URL.getUrl() + core.getUuid();
        OutputStream out = null;
        try {
            HttpEntity result = HttpUtil.doGet(qrUrl);
            out = new FileOutputStream(qrPath);
            byte[] bytes = EntityUtils.toByteArray(result);
            out.write(bytes);
            out.flush();
            // 打开登陆二维码图片
            ToolsUtil.printQr(qrPath);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.error("关闭异常:{}", e);
                }
        }
        return true;
    }

    @Override
    public boolean webWxInit() {
        // 组装请求URL和参数
        String url = String.format(URLEnum.INIT_URL.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()),
                String.valueOf(System.currentTimeMillis() / 3158L),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));

        Map<String, Object> paramMap = core.getParamMap();

        // 请求初始化接口
        try {
            String result = HttpUtil.doPost(url, paramMap);
            JSONObject obj = JSON.parseObject(result);

            JSONObject user = obj.getJSONObject(StorageLoginInfoEnum.User.getKey());
            JSONObject syncKey = obj.getJSONObject(StorageLoginInfoEnum.SyncKey.getKey());

            core.getLoginInfo().put(StorageLoginInfoEnum.InviteStartCount.getKey(),
                    obj.getInteger(StorageLoginInfoEnum.InviteStartCount.getKey()));
            core.getLoginInfo().put(StorageLoginInfoEnum.SyncKey.getKey(), syncKey);

            JSONArray syncArray = syncKey.getJSONArray("List");
            StringBuilder sb = new StringBuilder();
            for (int i = 0, size = syncArray.size(); i < size; i++) {
                sb.append(syncArray.getJSONObject(i).getString("Key"))
                        .append('_')
                        .append(syncArray.getJSONObject(i).getString("Val"))
                        .append('|');
            }
            // 1_661706053|2_661706420|3_661706415|1000_1494151022|
            sb.deleteCharAt(sb.length() - 1);
            // 1_661706053|2_661706420|3_661706415|1000_1494151022
            core.getLoginInfo().put(StorageLoginInfoEnum.synckey.getKey(), sb.toString());
            // 1_656161336|2_656161626|3_656161313|11_656159955|13_656120033|201_1492273724|1000_1492265953|1001_1492250432|1004_1491805192
            core.setUserName(user.getString("UserName"));
            core.setNickName(user.getString("NickName"));
            core.setUserSelf(obj.getJSONObject("User"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void wxStatusNotify() {
        // 组装请求URL和参数
        String url = String.format(URLEnum.STATUS_NOTIFY_URL.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));

        Map<String, Object> paramMap = core.getParamMap();
        paramMap.put(StatusNotifyParaEnum.CODE.para(), StatusNotifyParaEnum.CODE.value());
        paramMap.put(StatusNotifyParaEnum.FROM_USERNAME.para(), core.getUserName());
        paramMap.put(StatusNotifyParaEnum.TO_USERNAME.para(), core.getUserName());
        paramMap.put(StatusNotifyParaEnum.CLIENT_MSG_ID.para(), System.currentTimeMillis());

        try {
            HttpUtil.doPost(url, paramMap);
        } catch (Exception e) {
            LOGGER.error("微信状态通知接口失败！", e);
        }

    }


    @Override
    public void webWxGetContact() {
        String url = String.format(URLEnum.WEB_WX_GET_CONTACT.getUrl(), core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()));
        Map<String, Object> paramMap = core.getParamMap();
        try {
            String result = HttpUtil.doPost(url, paramMap);
            JSONObject fullFriendsJsonList = JSON.parseObject(result);

            core.setMemberCount(fullFriendsJsonList.getInteger(StorageLoginInfoEnum.MemberCount.getKey()));
            JSONArray member = fullFriendsJsonList.getJSONArray(StorageLoginInfoEnum.MemberList.getKey());
            for (int i = member.size() - 1; i >= 0; i--) {
                JSONObject o = (JSONObject) member.get(i);
                if ((o.getInteger("VerifyFlag") & 8) != 0) { // 公众号/服务号
                    core.getPublicUsersList().add(o);
                } else if (Config.API_SPECIAL_USER.contains(o.getString("UserName"))) { // 特殊账号
                    core.getSpecialUsersList().add(o);
                } else if (o.getString("UserName").contains("@@")) { // 群聊
                    core.getGroupList().add(o);
                } else if (o.getString("UserName").equals(core.getUserSelf().getString("UserName"))) { // 自己
                    core.getContactList().remove(o);
                } else { // 普通联系人
                    core.getContactList().add(o);
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void logout() {
        String url = String.format(URLEnum.WEB_WX_LOGOUT.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()));
        Map<String, Object> params = new HashMap<>();
        params.put("redirect", "1");
        params.put("type", "1");
        params.put("skey", core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey()));
        try {
            HttpUtil.doGet(url, params); // 无消息
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        }
        core.setAlive(false);
    }

    /**
     * 检查登陆状态
     *
     * @param result 请求结果检测
     * @return 登陆状态
     */
    public String checkLogin(String result) {
        Matcher matcher = ToolsUtil.getMatcher(CHECK_LOGIN_PATTERN, result);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 处理登陆信息
     *
     * @param loginContent 登录内容
     */
    private void processLoginInfo(String loginContent) {
        Matcher matcher = ToolsUtil.getMatcher(LOGIN_PATTERN, loginContent);
        if (matcher.find()) {
            String originalUrl = matcher.group(1);
            String url = originalUrl.substring(0, originalUrl.lastIndexOf('/'));
            // https://wx2.qq.com/cgi-bin/mmwebwx-bin
            core.getLoginInfo().put("url", url);
            Map<String, List<String>> possibleUrlMap = this.getPossibleUrlMap();
            Iterator<Map.Entry<String, List<String>>> iterator = possibleUrlMap.entrySet().iterator();
            Map.Entry<String, List<String>> entry;
            String fileUrl;
            String syncUrl;
            while (iterator.hasNext()) {
                entry = iterator.next();
                String indexUrl = entry.getKey();
                fileUrl = "https://" + entry.getValue().get(0) + "/cgi-bin/mmwebwx-bin";
                syncUrl = "https://" + entry.getValue().get(1) + "/cgi-bin/mmwebwx-bin";
                if (core.getLoginInfo().get("url").toString().contains(indexUrl)) {
                    core.getLoginInfo().put("fileUrl", fileUrl);
                    core.getLoginInfo().put("syncUrl", syncUrl);
                    break;
                }
            }
            if (core.getLoginInfo().get("fileUrl") == null && core.getLoginInfo().get("syncUrl") == null) {
                core.getLoginInfo().put("fileUrl", url);
                core.getLoginInfo().put("syncUrl", url);
            }
            core.getLoginInfo().put("deviceid", "e" + String.valueOf(new Random().nextLong()).substring(1, 16)); // 生成15位随机数
            core.getLoginInfo().put("BaseRequest", new ArrayList<String>());
            String text;

            try {
                text = HttpUtil.doGet(originalUrl, false);
            } catch (HttpException e) {
                LOGGER.error("处理登陆信息异常:{}", e.getMessage());
                return;
            }
            Document doc = ToolsUtil.xmlParser(text);
            if (doc != null) {
                core.getLoginInfo().put(StorageLoginInfoEnum.skey.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.skey.getKey()).item(0).getFirstChild()
                                .getNodeValue());
                core.getLoginInfo().put(StorageLoginInfoEnum.wxsid.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.wxsid.getKey()).item(0).getFirstChild()
                                .getNodeValue());
                core.getLoginInfo().put(StorageLoginInfoEnum.wxuin.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.wxuin.getKey()).item(0).getFirstChild()
                                .getNodeValue());
                core.getLoginInfo().put(StorageLoginInfoEnum.pass_ticket.getKey(),
                        doc.getElementsByTagName(StorageLoginInfoEnum.pass_ticket.getKey()).item(0).getFirstChild()
                                .getNodeValue());
            }

        }
    }


    private Map<String, List<String>> getPossibleUrlMap() {
        Map<String, List<String>> possibleUrlMap = new HashMap<>();

        List<String> wxList = new ArrayList<>();
        wxList.add("file.wx.qq.com");
        wxList.add("webpush.wx.qq.com");
        possibleUrlMap.put("wx.qq.com", wxList);

        List<String> wx2List = new ArrayList<>();
        wx2List.add("file.wx2.qq.com");
        wx2List.add("webpush.wx2.qq.com");
        possibleUrlMap.put("wx2.qq.com", wx2List);

        List<String> wx8List = new ArrayList<>();
        wx8List.add("file.wx8.qq.com");
        wx8List.add("webpush.wx8.qq.com");
        possibleUrlMap.put("wx8.qq.com", wx8List);


        List<String> web2List = new ArrayList<>();
        web2List.add("file.web2.wechat.com");
        web2List.add("webpush.web2.wechat.com");
        possibleUrlMap.put("web2.wechat.com", web2List);

        List<String> wechatList = new ArrayList<>();
        wechatList.add("file.web.wechat.com");
        wechatList.add("webpush.web.wechat.com");
        possibleUrlMap.put("wechat.com", wechatList);
        return possibleUrlMap;
    }

    @Override
    public void startReceiving() {
        core.setAlive(true);
        new Thread(new Runnable() {
            int retryCount = 0;

            @Override
            public void run() {
                while (core.isAlive()) {
                    try {
                        Map<String, String> resultMap = syncCheck();
                        LOGGER.info(JSONObject.toJSONString(resultMap));
                        String retcode = resultMap.get("retcode");
                        String selector = resultMap.get("selector");
                        RetCodeEnum codeEnum = RetCodeEnum.getEnumByCode(retcode);
                        LOGGER.info(codeEnum.getType());
                        switch (codeEnum) {
                            case LOGIN_OUT:
                            case LOGIN_OTHER_WHERE:
                            case MOBILE_LOGIN_OUT:
                                core.setAlive(false);
                                break;
                            case NORMAL:
                                switch (selector) {
                                    case "2"://TODO 此处接收消息,暂不处理
                                        continue;
                                    case "3":
                                    case "4":
                                    case "6":
                                        continue;
                                    case "7":
                                        webWxSync();
                                }
                                break;
                            case UNKNOWN:
                                continue;
                            default:
                                webWxSync();
                        }
                    } catch (Exception e) {
                        LOGGER.info(e.getMessage());
                        retryCount += 1;
                        if (core.getReceivingRetryCount() < retryCount) {
                            core.setAlive(false);
                        } else {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                LOGGER.info(e.getMessage());
                            }
                        }
                    }

                }
            }
        }).start();

    }

    /**
     * @return 同步消息 sync the messages
     */
    private JSONObject webWxSync() {
        JSONObject result = null;
        String url = String.format(URLEnum.WEB_WX_SYNC_URL.getUrl(),
                core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()),
                core.getLoginInfo().get(StorageLoginInfoEnum.wxsid.getKey()),
                core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey()),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
        Map<String, Object> paramMap = core.getParamMap();
        paramMap.put(StorageLoginInfoEnum.SyncKey.getKey(),
                core.getLoginInfo().get(StorageLoginInfoEnum.SyncKey.getKey()));
        paramMap.put("rr", -new Date().getTime() / 1000);
        try {
            String text = HttpUtil.doPost(url, paramMap);
            JSONObject obj = JSON.parseObject(text);
            if (obj.getJSONObject("BaseResponse").getInteger("Ret") != 0) {
                result = null;
            } else {
                result = obj;
                core.getLoginInfo().put(StorageLoginInfoEnum.SyncKey.getKey(), obj.getJSONObject("SyncCheckKey"));
                JSONArray syncArray = obj.getJSONObject(StorageLoginInfoEnum.SyncKey.getKey()).getJSONArray("List");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < syncArray.size(); i++) {
                    sb.append(syncArray.getJSONObject(i).getString("Key"));
                    sb.append("_");
                    sb.append(syncArray.getJSONObject(i).getString("Val"));
                    sb.append("|");
                }
                String synckey = sb.toString();
                core.getLoginInfo().put(StorageLoginInfoEnum.synckey.getKey(),
                        synckey.substring(0, synckey.length() - 1));
                // 1_656161336|2_656161626|3_656161313|11_656159955|13_656120033|201_1492273724|1000_1492265953|1001_1492250432|1004_1491805192
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        return result;

    }

    /**
     * @return 检查是否有新消息 check whether there's a message
     */
    private Map<String, String> syncCheck() {
        Map<String, String> resultMap = new HashMap<>();
        // 组装请求URL和参数
        String url = core.getLoginInfo().get(StorageLoginInfoEnum.syncUrl.getKey()) + URLEnum.SYNC_CHECK_URL.getUrl();
        Map<String, Object> params = new HashMap<>();
        for (RequestParaEnum baseRequest : RequestParaEnum.values()) {
            params.put(baseRequest.getParam().toLowerCase(),
                    core.getLoginInfo().get(baseRequest.getValue()).toString());
        }
        params.put("r", new Date().getTime());
        params.put("synckey", core.getLoginInfo().get("synckey"));
        params.put("_", new Date().getTime());
        try {
            Thread.sleep(7);
        } catch (InterruptedException e) {
            LOGGER.error("{}", e);
        }
        try {
            String result = HttpUtil.doGet(url, params);
            if (result == null) {
                resultMap.put("retcode", "9999");
                resultMap.put("selector", "9999");
                return resultMap;
            }
            Matcher matcher = ToolsUtil.getMatcher(CHECK_MESSAGE_PATTERN, result);
            if (!matcher.find() || matcher.group(1).equals("2")) {
                LOGGER.info("Unexpected sync check result: {}", result);
            } else {
                resultMap.put("retcode", matcher.group(1));
                resultMap.put("selector", matcher.group(2));
            }
        } catch (Exception e) {
            LOGGER.error("{}", e);
        }
        return resultMap;
    }
}
