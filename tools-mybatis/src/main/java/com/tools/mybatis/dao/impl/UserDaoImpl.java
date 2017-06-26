//package com.tools.mybatis.dao.impl;
//
//import IUserDao;
//import UserMapper;
//import User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @author : zhencai.cheng
// * @date : 2017/4/30
// * @description :
// */
//@Component
//public class UserDaoImpl implements IUserDao {
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Override
//    public int deleteByPrimaryKey(Integer id) {
//        return userMapper.deleteByPrimaryKey(id);
//    }
//
//    @Override
//    public int insert(User record) {
//        return userMapper.insert(record);
//    }
//
//    @Override
//    public int insertSelective(User record) {
//        return userMapper.insert(record);
//    }
//
//    @Override
//    public User selectByPrimaryKey(Integer id) {
//        return userMapper.selectByPrimaryKey(id);
//    }
//
//    @Override
//    public int updateByPrimaryKeySelective(User record) {
//        return userMapper.updateByPrimaryKeySelective(record);
//    }
//
//    @Override
//    public int updateByPrimaryKey(User record) {
//        return userMapper.updateByPrimaryKey(record);
//    }
//
//    @Override
//    public List<User> findUserByPage() {
//        return userMapper.findUserByPage();
//    }
//}
