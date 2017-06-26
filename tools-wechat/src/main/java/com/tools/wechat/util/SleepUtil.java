package com.tools.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * date: 2017/6/5
 * description :
 *
 * @author : zhencai.cheng
 */
public class SleepUtil {

    private static final Logger logger = LoggerFactory.getLogger(SleepUtil.class);

    public static void sleep(long i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            logger.error("{}", e);
        }
    }
}
