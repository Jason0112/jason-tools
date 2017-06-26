package com.tools.mybatis.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.tools.mybatis.common.CommonTest;
import com.tools.mybatis.dao.IUserDao;
import com.tools.mybatis.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :
 */
public class IUserDaoImplTest extends CommonTest {

    @Autowired
    IUserDao userDao;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
        user.setPassword("1");
        user.setPasswordSalt("1");
        user.setCreateTime(new Date());
        user.setEmail("test@163.com");
        user.setPhone("18201299161");
        user.setStatus(1);
        user.setUsername("测试");
    }

    @Test
    public void testDeleteByPrimaryKey() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < 25; i++) {
            user.setUsername("测试" + i);
            userDao.insert(user);
        }
    }

    @Test
    public void testInsertSelective() throws Exception {

    }

    @Test
    public void testSelectByPrimaryKey() throws Exception {
        User u = userDao.selectByPrimaryKey(39530);
        System.out.println(JSONObject.toJSONString(u));
    }

    @Test
    public void testUpdateByPrimaryKeySelective() throws Exception {
        user.setId(39530);
        user.setUsername("更新");
        userDao.updateByPrimaryKeySelective(user);
    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {
        user.setId(39530);
        user.setUsername("更新11");
        userDao.updateByPrimaryKeySelective(user);
        testSelectByPrimaryKey();
    }
}