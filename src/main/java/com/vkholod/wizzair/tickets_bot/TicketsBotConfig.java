package com.vkholod.wizzair.tickets_bot;

import io.dropwizard.Configuration;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;

public class TicketsBotConfig extends Configuration {

    private JedisPoolConfig redisPoolConfig;
    private URI redisUri;
    private int okHttpClientConnectTimeout;
    private int okHttpClientReadTimeout;

    public JedisPoolConfig getRedisPoolConfig() {
        return redisPoolConfig;
    }

    public void setRedisPoolConfig(JedisPoolConfig redisPoolConfig) {
        this.redisPoolConfig = redisPoolConfig;
    }

    public URI getRedisUri() {
        return redisUri;
    }

    public void setRedisUri(URI redisUri) {
        this.redisUri = System.getenv("REDIS_URL") != null ? URI.create(System.getenv("REDIS_URL")) : redisUri;
    }

    public int getOkHttpClientConnectTimeout() {
        return okHttpClientConnectTimeout;
    }

    public void setOkHttpClientConnectTimeout(int okHttpClientConnectTimeout) {
        this.okHttpClientConnectTimeout = okHttpClientConnectTimeout;
    }

    public int getOkHttpClientReadTimeout() {
        return okHttpClientReadTimeout;
    }

    public void setOkHttpClientReadTimeout(int okHttpClientReadTimeout) {
        this.okHttpClientReadTimeout = okHttpClientReadTimeout;
    }
}
