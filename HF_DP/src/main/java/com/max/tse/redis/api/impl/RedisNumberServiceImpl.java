package com.max.tse.redis.api.impl;

import com.max.tse.redis.utils.ArgumentValidateUtils;
import com.max.tse.redis.api.RedisNumberService;
import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-16
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class RedisNumberServiceImpl implements RedisNumberService {

    private Jedis jedis;

    public RedisNumberServiceImpl(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public long increase(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.incr(key);
    }

    @Override
    public long increaseBy(String key, long num) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.incrBy(key, num);
    }

    @Override
    public long decrease(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.decr(key);
    }

    @Override
    public long decreaseBy(String key, long num) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.decrBy(key, num);
    }

    @Override
    public double increaseFloat(String key, double num) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.incrByFloat(key, num);
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
