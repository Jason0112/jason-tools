package com.tools.mybatis.dao;

import com.tools.mybatis.model.User;

import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/30
 * @description :
 */
public interface IUserDao {

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> findUserByPage();
}
