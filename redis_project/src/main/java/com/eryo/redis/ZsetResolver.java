package com.eryo.redis;

import redis.clients.jedis.Jedis;

public class ZsetResolver implements TypeResolver{

    private CounterSpec counterSpec;
    private Jedis jedis;

    @Override
    public String resolve() {
        return null;
    }

    @Override
    public void setData(CounterSpec counterSpec, Jedis jedis) {
        this.counterSpec = counterSpec;
        this.jedis = jedis;
    }
}