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

    private static final String TELEGRAM_OFFSET_KEY = "TELEGRAM_OFFSET_KEY";

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

    public Optional<Integer> findTelegramOffsetInRedis() {
        try(Jedis resource = pool.getResource()) {
            if (resource.exists(TELEGRAM_OFFSET_KEY)) {
                return Optional.of(Integer.parseInt(resource.get(TELEGRAM_OFFSET_KEY)));
            }
        }

        return Optional.empty();
    }

    public void saveTelegramOffsetInRedis(int offset) {
        try(Jedis resource = pool.getResource()) {
            resource.set(TELEGRAM_OFFSET_KEY, String.valueOf(offset));
        }
    }
}
