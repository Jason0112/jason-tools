package com.tools.redis.service;

import com.tools.redis.exception.RedisException;
import com.tools.redis.exception.SerializerException;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.SortingParams;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/26
 * @description :redis抽象实现类
 */
public abstract class AbstractRedisService implements IRedisService {

    protected abstract JedisCommands getRedis() throws RedisException;

    @Override
    public String setObject(String key, String obj) throws Exception {
        return setObjectImpl(key, obj);
    }

    @Override
    public String setObject(String key, Serializable value) throws Exception {
        return setObjectImpl(key, value);
    }

    @Override
    public String setObject(String key, List<? extends Serializable> value) throws Exception {
//        byte[] bytes = SerializeUtil.serializeList(value);
//        return getRedis().set(key.getBytes().toString(), bytes.toString());
        return setObjectImpl(key, value);
    }

    @Override
    public String setObject(String key, Map<?, ? extends Serializable> value) throws Exception {
        return setObjectImpl(key, value);
    }

    @Override
    public String setObject(String key, Set<? extends Serializable> value) throws Exception {
        return setObjectImpl(key, value);
    }

    protected abstract String setObjectImpl(String key, Object obj) throws RedisException;

    /****list 操作***/
    /**
     * 在list集合中头部插入数据
     *
     * @param key    key
     * @param values 数据值
     * @return 返回长度
     */
    @Override
    public Long addFirst(String key, String... values) throws RedisException {
        return getRedis().lpush(key, values);
    }

    /**
     * 在List集合中尾部插入数据
     *
     * @param key    key
     * @param values 添加值
     * @return 返回集合长度
     */
    @Override
    public Long addLast(String key, String... values) throws RedisException {
        return getRedis().rpush(key, values);
    }

    /**
     * 获取第一个元素,并删除
     *
     * @param key key
     * @return 返回集合第一个元素
     */
    @Override
    public String pollFirst(String key) throws RedisException {
        return getRedis().lpop(key);
    }

    /**
     * 获取最后一个元素,并删除
     *
     * @param key key
     * @return 返回集合最后一个长度
     */
    @Override
    public String pollLast(String key) throws RedisException {
        return getRedis().rpop(key);
    }

    @Override
    public Long size(String key) throws RedisException {
        return getRedis().llen(key);
    }

    @Override
    public List<String> getList(String key) throws RedisException {
        return getRedis().lrange(key, 0, getRedis().llen(key));
    }

    @Override
    public Long remove(String key, String value) throws RedisException {
        return getRedis().lrem(key, 0, value);
    }

    @Override
    public List<String> sort(String key) throws RedisException {
        return getRedis().sort(key, new SortingParams().alpha());
    }

    @Override
    public Long del(String key) throws RedisException {
        return getRedis().del(key);
    }

    @Override
    public Long expire(String key, int seconds) throws RedisException {
        return getRedis().expire(key, seconds);
    }

    @Override
    public Long ttl(String key) throws RedisException {
        return getRedis().ttl(key);
    }


    /**
     * 序列化对象
     *
     * @param obj 对象
     * @return 字节数组
     * @throws SerializerException
     */
    protected byte[] objectToByte(Object obj) throws SerializerException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            byte[] bs = bos.toByteArray();
            oos.close();
            bos.close();
            return bs;
        } catch (IOException e) {
            throw new SerializerException("序列化对象失败", e);
        }
    }

    /**
     * 获取序列化对象
     *
     * @param bs 字节数组
     * @return 对象
     * @throws SerializerException
     */
    protected Object byteToObject(byte[] bs) throws SerializerException {
        Object obj = null;
        try {
            if (bs != null) {
                ByteArrayInputStream bis = new ByteArrayInputStream(bs);
                ObjectInputStream inputStream = new ObjectInputStream(bis);
                obj = inputStream.readObject();
                inputStream.close();
                bis.close();
            }
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializerException("获取序列化对象失败.", e);
        }
    }
}
