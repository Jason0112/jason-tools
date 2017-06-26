package com.tools.parser.file.txt;

import com.tools.parser.IParser;
import com.tools.parser.exception.ParserException;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：cheng.zhencai  
 * @date ：2017/04/30
 * @description : 文件解析类（txt,csv）
 */
public class TXTFileParser implements IParser {
    /**
     * 文件编码
     */
    private String CODE = System.getProperty("file.encoding");
    /**
     * 默认分割符
     */
    private final String DEFAULT__SPLIT = "\t";

    /**
     * 默认开始解析行
     */
    private final int DEFAULT_HEAD = 0;
    private String splitStr = DEFAULT__SPLIT;
    private int head = DEFAULT_HEAD;

    /**
     * @param inputStream 流
     * @param parameter   参数(1:null,则默认设置,
     *                    2:一个参数字符串则是分割符,数字字符串则是head设置
     *                    3:两个参数,则第一个是分割符,第二个从head行解析)
     * @return 返回解析数据
     * @throws ParserException
     */
    @Override
    public List<Object> parser(InputStream inputStream, String... parameter) throws ParserException {
        this.setParameter(parameter);
        BufferedReader reader = null;
        int i = 1;
        String line;
        List<Object> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, CODE));
            while ((line = reader.readLine()) != null) {
                i++;
                if (head > i || StringUtils.isBlank(line)) {
                    continue;
                }
                list.add(Arrays.asList(line.split(splitStr)));
            }
        } catch (IOException e) {
            throw new ParserException("没找到文件", e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 判断传递是参数是
     *
     * @param parameter 参数
     */
    private void setParameter(String... parameter) {
        if (parameter != null && parameter.length > 0) {
            if (parameter.length == 1) {
                if (isNumber(parameter[0])) {
                    head = Integer.valueOf(parameter[0]);
                } else {
                    splitStr = parameter[0];
                }
            } else if (parameter.length == 2) {
                splitStr = parameter[0];
                head = Integer.valueOf(parameter[1]);
            }
        }
    }

    /**
     * 是否是数字
     *
     * @param s 字符
     * @return 是否是数字
     */
    private boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {

            if (!Character.isDigit(s.charAt(i))) {

                return false;
            }
        }
        return true;
    }
}
