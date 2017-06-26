package com.tools.mybatis.service;

import com.tools.mybatis.model.User;
import com.tools.mybatis.interceptor.Page;
import com.tools.mybatis.interceptor.PageInfo;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :用户业务
 */
public interface UserService {

    PageInfo<User> findUserByPage(Page page);
}
