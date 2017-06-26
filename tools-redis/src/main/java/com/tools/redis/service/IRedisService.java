package com.tools.redis.service;

import com.tools.redis.exception.RedisException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/26
 * @description : redis接口
 */
public interface IRedisService {


    /**
     * 存对象
     **/
    String setObject(String key, String obj) throws Exception;

    /**
     * 存储序列化对象
     *
     * @param key   key
     * @param value 序列化对象
     * @return 返回
     */
    String setObject(String key, Serializable value) throws Exception;

    /**
     * 存储list集合
     *
     * @param key   key
     * @param value list集合
     * @return 返回
     */
    String setObject(String key, List<? extends Serializable> value) throws Exception;

    /**
     * 存储map集合
     *
     * @param key   key
     * @param value map集合
     * @return
     */
    String setObject(String key, Map<?, ? extends Serializable> value) throws Exception;

    /**
     * 存储set集合
     *
     * @param key   key
     * @param value set集合
     * @return 返回
     */
    String setObject(String key, Set<? extends Serializable> value) throws Exception;


    /**
     * 获取对象
     *
     * @param key key
     * @return 对象
     */
    Object getObject(String key) throws Exception;

    List getObjectList(String key) throws Exception;

    /****************************************************list操作***************************/
    /**
     * 队列头添加元素
     *
     * @param key    key
     * @param values 元素组
     * @return 返回集合长度
     */
    Long addFirst(String key, String... values) throws RedisException;

    /**
     * 队列尾添加元素
     *
     * @param key    key
     * @param values 元素组
     * @return 返回集合长度
     */
    Long addLast(String key, String... values) throws RedisException;

    /**
     * 获取队列头第一个元素,并删除此元素
     *
     * @param key key
     * @return 元素
     */
    String pollFirst(String key) throws RedisException;

    /**
     * 获取队列头最后一个元素,并删除此元素
     *
     * @param key key
     * @return 元素
     */
    String pollLast(String key) throws RedisException;

    /**
     * 获取list大小
     *
     * @param key key
     * @return 返回大小
     */
    Long size(String key) throws RedisException;

    /**
     * 获取key对应的list集合
     *
     * @param key key
     * @return 返回所有list集合
     */
    List<String> getList(String key) throws RedisException;

    /**
     * 移除list集合中所有值为value的元素
     *
     * @param key   key
     * @param value 移除元素值
     * @return 移除多少个
     */
    Long remove(String key, String value) throws RedisException;

    /**
     * list排序
     *
     * @param key key
     */
    List<String> sort(String key) throws RedisException;

    /****************************************************公共方法***************************/
    /**
     * 删除相关key
     *
     * @param key key
     * @return 返回删除数量
     */
    Long del(String key) throws RedisException;

    /**
     * 设计过期时间
     *
     * @param key     key
     * @param seconds 时间(秒)
     * @return 返回时间
     */
    Long expire(String key, int seconds) throws RedisException;

    /**
     * 查询key对应的失效时间
     *
     * @param key 查询key
     * @return 返回时间
     */
    Long ttl(String key) throws RedisException;
}
