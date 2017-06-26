package com.tools.parser;

import com.tools.parser.exception.ParserException;

import java.io.InputStream;
import java.util.List;

/**
 * @author ：cheng.zhencai  
 * @date ：2017/04/30
 * @description : 解析接口
 */
public interface IParser {

    /**
     * @param inputStream 流
     * @param parameter   参数
     * @return 数据
     * @throws ParserException
     */
    List<Object> parser(InputStream inputStream, String... parameter) throws ParserException;

}
