package com.vkholod.wizzair.tickets_bot.dao;

import com.vkholod.wizzair.tickets_bot.model.Timetable;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;

public class RedisStorage {

    private JedisPool pool;

    public RedisStorage(URI redisUri, JedisPoolConfig poolConfig) {
        this.pool = new JedisPool(poolConfig, redisUri);
    }

    public Timetable findTimetableInRedis(String key) {
        // TODO: to be implemented
        return new Timetable();
    }

    public void saveTimetableInRedis(String key, Timetable timetable) {
        // TODO: to be implemented
    }
}
