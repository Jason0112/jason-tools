package com.tools.redis.dataSource;

import redis.clients.jedis.ShardedJedis;

/**
 * @author : zhencai.cheng
 * @date : 2017/4/22
 * @description :jedis数据源
 */
public interface JedisDataSource {

    ShardedJedis getRedisClient();

    void returnResource(ShardedJedis shardedJedis);

    void returnResource(ShardedJedis shardedJedis, boolean broken);

}
