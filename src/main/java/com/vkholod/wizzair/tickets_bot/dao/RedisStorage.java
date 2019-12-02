package com.vkholod.wizzair.tickets_bot.dao;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;

@Singleton
public class RedisStorage {

    private JedisPool pool;

    public static JedisPool getPool() throws URISyntaxException {
        System.getenv("REDIS_URL");


//        URI redisURI = new URI();
        URI redisURI = new URI("redis://h:p51a15a9697437861d8d80fc9ef32c916b14a3e9cd6d479cd35278336e9edfa34@ec2-3-221-86-85.compute-1.amazonaws.com:20019");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        JedisPool pool = new JedisPool(poolConfig, redisURI);
        return pool;
    }

}
