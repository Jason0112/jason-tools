package com.tools.message.service;

import com.tools.message.model.SignRecord;

/**
 * @author : zhencai.cheng
 * @date : 2017/5/7
 * @description :
 */
public interface ISignRecordService {
    SignRecord getSignRecordByName(String sign);
}
