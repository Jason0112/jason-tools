package com.tools.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.tools.wechat.bean.Core;
import com.tools.wechat.enums.StorageLoginInfoEnum;
import com.tools.wechat.enums.URLEnum;
import com.tools.wechat.service.SendService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public class WechatTools {

    private static final Logger logger = LoggerFactory.getLogger(WechatTools.class);

    private static Core core = Core.getInstance();

    @Autowired
    private SendService sendService;

    /**
     * 返回好友昵称列表
     *
     * @return
     */
    public List<String> getContactList() {
        return core.getContactList().stream().map(o -> o.getString("NickName")).collect(Collectors.toList());
    }

    /**
     * 返回群列表
     *
     * @return
     */
    public List<String> getGroupList() {
        return core.getGroupList().stream().map(o -> o.getString("Name")).collect(Collectors.toList());
    }

    public List<String> getGroupIdList() {
        return core.getGroupIdList();
    }


    public static void setUserInfo() {
        Set<String> only = read();
        for (JSONObject o : core.getContactList()) {
            core.getUserInfoMap().put(o.getString("NickName"), o);
            core.getUserInfoMap().put(o.getString("UserName"), o);
            only.add(o.getString("uin") + o.getString("keyword"));
        }
        write(only);
    }

    private static void write(Set<String> only) {
        WriterFile writer = new WriterFile("/Users/chengzhencai/new.txt");
        for (String content:only){
            try {
                writer.writer(content);
            } catch (IOException e) {
                logger.error("{}",e);
            }
        }
    }

    private static Set<String> read() {
        Set<String> key = new HashSet<>();
        FileReader reader = null;
        try {
            reader = new FileReader("/Users/chengzhencai/key.txt");
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                if (StringUtils.isNotBlank(line)) {
                    key.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception ignored) {

            }
        }
        return key;
    }

    /**
     * 备注名
     *
     * @param nickName 昵称
     * @param remName  备注名
     */
    public void remarkNameByNickName(String nickName, String remName) {
        String url = String.format(URLEnum.WEB_WX_REMARKNAME.getUrl(), core.getLoginInfo().get("url"),
                core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
        Map<String, Object> msgMap = new HashMap<>();
        Map<String, Object> msgMap_BaseRequest = new HashMap<>();
        msgMap.put("CmdId", 2);
        msgMap.put("RemarkName", remName);
        msgMap.put("UserName", core.getUserInfoMap().get(nickName).get("UserName"));
        msgMap_BaseRequest.put("Uin", core.getLoginInfo().get(StorageLoginInfoEnum.wxuin.getKey()));
        msgMap_BaseRequest.put("Sid", core.getLoginInfo().get(StorageLoginInfoEnum.wxsid.getKey()));
        msgMap_BaseRequest.put("Skey", core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey()));
        msgMap_BaseRequest.put("DeviceID", core.getLoginInfo().get(StorageLoginInfoEnum.deviceid.getKey()));
        msgMap.put("BaseRequest", msgMap_BaseRequest);
        try {
            HttpUtil.doPost(url, msgMap);
            logger.info("修改备注" + remName);
        } catch (Exception e) {
            logger.error("remarkNameByUserName", e);
        }
    }
}
