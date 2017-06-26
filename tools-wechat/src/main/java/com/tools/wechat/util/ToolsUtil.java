package com.tools.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tools.wechat.enums.OSEnum;
import com.vdurmont.emoji.EmojiParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public class ToolsUtil {

    private static final Pattern EMOJ_PATTERN = Pattern.compile("<span class=\"emoji emoji(.{1,10})\"></span>");

    public static boolean printQr(String qrPath) {
        Runtime runtime = null;
        String command = null;
        switch (OSEnum.getLocalOS()) {
            case WINDOWS:
                runtime = Runtime.getRuntime();
                command = "cmd /c start ";
                break;
            case MAC:
                runtime = Runtime.getRuntime();
                command = "open ";
                break;
            default:
        }
        if (runtime != null) {
            try {
                runtime.exec(command + qrPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean clearScreen() {
        if (OSEnum.getLocalOS().equals(OSEnum.WINDOWS)) {
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("cmd /c " + "cls");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 正则表达式处理工具
     *
     * @return
     */
    public static Matcher getMatcher(Pattern pattern, String text) {
        return pattern.matcher(text);
    }

    /**
     * xml解析器
     *
     * @param text
     * @return
     */
    public static Document xmlParser(String text) {
        Document doc = null;
        StringReader sr = new StringReader(text);
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static JSONObject structFriendInfo(JSONObject userObj) {
        Map<String, Object> friendInfoTemplate = new HashMap<String, Object>();
        friendInfoTemplate.put("UserName", "");
        friendInfoTemplate.put("City", "");
        friendInfoTemplate.put("DisplayName", "");
        friendInfoTemplate.put("PYQuanPin", "");
        friendInfoTemplate.put("RemarkPYInitial", "");
        friendInfoTemplate.put("Province", "");
        friendInfoTemplate.put("KeyWord", "");
        friendInfoTemplate.put("RemarkName", "");
        friendInfoTemplate.put("PYInitial", "");
        friendInfoTemplate.put("EncryChatRoomId", "");
        friendInfoTemplate.put("Alias", "");
        friendInfoTemplate.put("Signature", "");
        friendInfoTemplate.put("NickName", "");
        friendInfoTemplate.put("RemarkPYQuanPin", "");
        friendInfoTemplate.put("HeadImgUrl", "");

        friendInfoTemplate.put("UniFriend", 0);
        friendInfoTemplate.put("Sex", 0);
        friendInfoTemplate.put("AppAccountFlag", 0);
        friendInfoTemplate.put("VerifyFlag", 0);
        friendInfoTemplate.put("ChatRoomId", 0);
        friendInfoTemplate.put("HideInputBarFlag", 0);
        friendInfoTemplate.put("AttrStatus", 0);
        friendInfoTemplate.put("SnsFlag", 0);
        friendInfoTemplate.put("MemberCount", 0);
        friendInfoTemplate.put("OwnerUin", 0);
        friendInfoTemplate.put("ContactFlag", 0);
        friendInfoTemplate.put("Uin", 0);
        friendInfoTemplate.put("StarFriend", 0);
        friendInfoTemplate.put("Statues", 0);

        friendInfoTemplate.put("MemberList", new ArrayList<Object>());

        JSONObject r = new JSONObject();
        Set<String> keySet = friendInfoTemplate.keySet();
        for (String key : keySet) {
            if (userObj.containsKey(key)) {
                r.put(key, userObj.get(key));
            } else {
                r.put(key, friendInfoTemplate.get(key));
            }
        }

        return r;
    }

    public static String getSynckey(JSONObject obj) {
        JSONArray obj2 = obj.getJSONArray("List");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < obj2.size(); i++) {
            JSONObject obj3 = (JSONObject) JSON.toJSON(obj2.get(i));
            sb.append(obj3.get("Val") + "|");
        }
        return sb.substring(0, sb.length() - 1); // 656159784|656159911|656159873|1491905341

    }

    public static JSONObject searchDictList(List<JSONObject> list, String key, String value) {
        JSONObject r = null;
        for (JSONObject i : list) {
            if (i.getString(key).equals(value)) {
                r = i;
                break;
            }
        }
        return r;
    }

    /**
     * 处理emoji表情
     *
     * @param d
     * @param k
     */
    public static void emojiFormatter(JSONObject d, String k) {
        Matcher matcher = getMatcher(EMOJ_PATTERN, d.getString(k));
        StringBuilder sb = new StringBuilder();
        String content = d.getString(k);
        int lastStart = 0;
        while (matcher.find()) {
            String str = matcher.group(1);
            if (str.length() != 6 && str.length() != 10) {
                str = "&#x" + str + ";";
                String tmp = content.substring(lastStart, matcher.start());
                sb.append(tmp + str);
                lastStart = matcher.end();
            }
        }
        if (lastStart < content.length()) {
            sb.append(content.substring(lastStart));
        }
        if (sb.length() != 0) {
            d.put(k, EmojiParser.parseToUnicode(sb.toString()));
        } else {
            d.put(k, content);
        }

    }

    /**
     * 消息格式化
     *
     * @param d
     * @param k
     */
    public static void msgFormatter(JSONObject d, String k) {
        d.put(k, d.getString(k).replace("<br/>", "\n"));
        emojiFormatter(d, k);
        // TODO 与emoji表情有部分兼容问题，目前暂未处理解码处理 d.put(k,
        // StringEscapeUtils.unescapeHtml4(d.getString(k)));

    }
}