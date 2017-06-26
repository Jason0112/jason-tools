package com.tools.message.service;

import com.tools.message.model.ChannelInfo;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/6
 * @description :第三方渠道信息
 */
public interface IChannelService {

    ChannelInfo findChannelInfoByAppKey(String appKey);
}
