package com.vkholod.wizzair.tickets_bot.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vkholod.wizzair.tickets_bot.model.Timetable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class RedisStorage {

    private JedisPool pool;
    private ObjectReader timetableReader;
    private ObjectWriter timetableWriter;

    public RedisStorage(URI redisUri, JedisPoolConfig poolConfig, ObjectMapper mapper) {
        this.pool = new JedisPool(poolConfig, redisUri);
        this.timetableReader = mapper.readerFor(Timetable.class);
        this.timetableWriter= mapper.writerFor(Timetable.class);
    }

    public Optional<Timetable> findTimetableInRedis(String key) {
        try(Jedis resource = pool.getResource()) {
            if (resource.exists(key)) {
                String value = resource.get(key);

                return Optional.of(timetableReader.readValue(value));
            }
        } catch (IOException e) {
            System.err.println("Could not deserialize timetable " + e.getMessage());
        }

        return Optional.empty();
    }

    public void saveTimetableInRedis(String key, Timetable timetable) {
        try(Jedis resource = pool.getResource()) {
            String value = timetableWriter.writeValueAsString(timetable);
            resource.set(key, value);
        } catch (JsonProcessingException e) {
            System.err.println("Could not serialize timetable " + e.getMessage());
        }
    }
}
