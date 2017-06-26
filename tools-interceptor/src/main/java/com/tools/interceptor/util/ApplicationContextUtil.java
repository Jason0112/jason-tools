package com.tools.interceptor.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * date: 2017/6/8
 * description :
 *
 * @author : zhencai.cheng
 */
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }
}
