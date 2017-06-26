package com.tools.activemq.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :得到spring注入Bean对象
 */
public class SpringUtil {

    /**
     * 配置文件路径
     */
    private static final String BEAN_REF_LOCATION = "classpath:spring/applicationContext.xml";
    private static ApplicationContext ac = null;

    /**
     * 根据bean名称获得实体
     *
     * @param beanName 例如:coreCompanyServiceImpl
     * @return bean
     */
    public static Object getBeanObj(String beanName) {
        if (ac == null) {
            ac = new FileSystemXmlApplicationContext(BEAN_REF_LOCATION);
        }
        return ac.getBean(beanName);
    }

    public static ApplicationContext getApplicationContext() {
        if (ac == null) {
            ac = new FileSystemXmlApplicationContext(BEAN_REF_LOCATION);
        }
        return ac;
    }


}
