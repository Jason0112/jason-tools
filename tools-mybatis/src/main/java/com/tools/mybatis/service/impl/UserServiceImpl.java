package com.tools.mybatis.service.impl;

import com.tools.mybatis.dao.IUserDao;
import com.tools.mybatis.interceptor.PageHelper;
import com.tools.mybatis.interceptor.PageInfo;
import com.tools.mybatis.model.User;
import com.tools.mybatis.service.UserService;
import com.tools.mybatis.interceptor.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    IUserDao userDao;

    @Override
    public PageInfo<User> findUserByPage(Page page) {
        PageHelper.startPage(page);
        List<User> users = userDao.findUserByPage();
        return new PageInfo<>(users);
    }
}
