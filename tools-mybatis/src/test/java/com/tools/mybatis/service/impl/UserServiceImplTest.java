package com.tools.mybatis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tools.mybatis.common.CommonTest;
import com.tools.mybatis.interceptor.Page;
import com.tools.mybatis.interceptor.PageInfo;
import com.tools.mybatis.model.User;
import com.tools.mybatis.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :
 */
public class UserServiceImplTest extends CommonTest {

    @Autowired
    UserService userService;

    @Test
    public void testFindUserByPage() throws Exception {
        Page page = new Page();
        page.setPageSize(10);
        page.setPageNum(1);
        PageInfo<User> users = userService.findUserByPage(page);
        System.out.println(users.getTotal());
        List<User> userList = users.getList();
        for (User user : userList) {
            System.out.println(JSONObject.toJSONString(user));
        }
    }
}