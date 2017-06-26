package com.tools.redis.service.impl;

import com.tools.redis.exception.ConnectionException;
import com.tools.redis.exception.RedisException;
import com.tools.redis.service.AbstractRedisService;
import com.tools.redis.service.IRedisService;
import com.tools.redis.exception.SerializerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/22
 * @description :redis集群
 */
@Service("shardRedisService")
public class ShardRedisServiceImpl extends AbstractRedisService implements IRedisService {

    private static Logger logger = LoggerFactory.getLogger(ShardRedisServiceImpl.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    private ShardedJedis redis;

    @Override
    protected ShardedJedis getRedis() throws RedisException {
        if (redis == null) {
            logger.info("重新获取链接...........");
            redis = shardedJedisPool.getResource();
        }
        if (redis == null) {
            throw new ConnectionException("获取链接失败");
        }

        return redis;
    }

    @Override
    protected String setObjectImpl(String key, Object obj) throws RedisException {
        try {
            return getRedis().set(key.getBytes(), objectToByte(obj));
        } catch (SerializerException e) {
            logger.error(e.getMessage());
        }
        return null;
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
        return null;
    }
}
