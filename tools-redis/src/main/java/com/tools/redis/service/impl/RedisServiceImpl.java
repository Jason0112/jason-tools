package com.tools.redis.service.impl;

import com.tools.redis.exception.RedisException;
import com.tools.redis.service.AbstractRedisService;
import com.tools.redis.service.IRedisService;
import com.tools.redis.exception.ConnectionException;
import com.tools.redis.exception.SerializerException;
import com.tools.redis.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/26
 * @description :
 */
@Service("redisService")
public class RedisServiceImpl extends AbstractRedisService implements IRedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    @Autowired
    private JedisPool jedisPool;

    private Jedis redis;

    protected Jedis getRedis() throws RedisException {
        if (redis == null) {
            redis = jedisPool.getResource();
        }
        if (redis == null) {
            throw new ConnectionException("获取链接失败.....");
        }
        return redis;
    }

    @Override
    protected String setObjectImpl(String key, Object obj) throws RedisException {
        try {
            SerializeUtil.serialize(obj);
            return getRedis().set(key.getBytes(), objectToByte(obj));
        } catch (SerializerException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String setObject(String key, List list) throws RedisException {
        byte[] bytes = SerializeUtil.serializeList(list);
        return getRedis().set(key.getBytes(), bytes);
    }

    @Override
    public Object getObject(String key) throws RedisException {
        byte[] values = this.getRedis().get(key.getBytes());
        try {
            return byteToObject(values);
        } catch (SerializerException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List getObjectList(String key) throws Exception {
        byte[] bytes = getRedis().get(key.getBytes());
        return SerializeUtil.deserializeList(bytes, String.class);
    }

}
