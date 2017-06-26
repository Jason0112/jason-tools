package com.tools.parser.file.xml;

import com.alibaba.fastjson.JSONObject;
import com.tools.common.util.ResourceUtil;
import com.tools.parser.exception.ParserException;
import org.junit.Test;

import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :
 */
public class XmlParserTest {

    @Test
    public void testParser() throws Exception {
        List<Object> list = new XmlParser().parser(ResourceUtil.getResourceAsStream("test.xml"));
        for (Object o : list) {
            System.out.println(JSONObject.toJSONString(o));
        }
    }

    @Test
    public void testParserAttribute() throws ParserException {
        List<Object> list = new XmlParser().parser(ResourceUtil.getResourceAsStream("attr.xml"), "attr");
        for (Object o : list) {
            System.out.println(JSONObject.toJSONString(o));
        }
    }
}