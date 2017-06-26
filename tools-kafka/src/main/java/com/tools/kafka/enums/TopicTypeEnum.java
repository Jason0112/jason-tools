package com.tools.kafka.enums;

import com.tools.kafka.util.ConfigProperties;
import org.apache.commons.lang3.StringUtils;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/18
 * @description :
 */
public enum TopicTypeEnum {

    USER("用户信息", ConfigProperties.USER_TOPIC_NAME, ConfigProperties.USER_TOPIC_FLAG, "userID"),
    COMPANY_FROM_DC("客户入库",ConfigProperties.COMPANY_FROM_DC_TOPIC,ConfigProperties.COMPANY_FROM_DC_TOPIC_FLAG,null),
    OTHER("其它", null, false, null);

    /**
     * 业务名称
     */
    private String name;
    /**
     * 主题名称
     */
    private String topicName;
    /**
     * 是否开启发送
     */
    private boolean flag;
    /**
     * 参数
     */
    private String parameter;

    TopicTypeEnum(String name, String topicName, boolean flag, String parameter) {
        this.name = name;
        this.topicName = topicName;
        this.flag = flag;
        this.parameter = parameter;
    }

    public static TopicTypeEnum getTopicTypeEnumByTopic(String topic) {
        if (StringUtils.isEmpty(topic)) {
            return TopicTypeEnum.OTHER;
        }
        for (TopicTypeEnum e : values()) {
            if (e.getTopicName().equals(topic)) {
                return e;
            }
        }
        return TopicTypeEnum.OTHER;
    }

    public boolean isFlag() {
        return flag;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getParameter() {
        return parameter;
    }

    public String getName() {
        return name;
    }
}
