package com.tools.redis.dataSource.impl;

import com.tools.redis.dataSource.JedisDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/22
 * @description :jedis数据源
 */
@Repository
public class JedisDataSourceImpl implements JedisDataSource {

    private static final Logger LOG = LoggerFactory.getLogger(JedisDataSourceImpl.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public ShardedJedis getRedisClient() {
        ShardedJedis shardJedis = null;
        try {
            return shardedJedisPool.getResource();
        } catch (Exception e) {
            LOG.error("[JedisDS] getRedisClent error:" + e.getMessage());
            if (null != shardJedis)
                shardJedis.close();
        }
        return null;
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedis.close();
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        shardedJedis.close();
    }
}
