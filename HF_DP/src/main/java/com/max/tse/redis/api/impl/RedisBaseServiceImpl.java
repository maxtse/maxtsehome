package com.max.tse.redis.api.impl;

import com.max.tse.redis.utils.ArgumentValidateUtils;
import com.max.tse.redis.api.RedisBaseService;
import redis.clients.jedis.Jedis;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-16
 * Time: 下午2:22
 * To change this template use File | Settings | File Templates.
 */
public class RedisBaseServiceImpl implements RedisBaseService {

    private Jedis jedis;

    public RedisBaseServiceImpl(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public Long expire(String key, int seconds) {
        ArgumentValidateUtils.validateKeyAndSecond(key, seconds);
        return jedis.expire(key, seconds);
    }

    @Override
    public Long expireAt(String key, long time) {
        ArgumentValidateUtils.validateKeyAndTime(key, time);
        return jedis.expireAt(key, time);
    }

    @Override
    public boolean exists(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.exists(key);
    }

    @Override
    public long ttl(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.ttl(key);
    }

    @Override
    public boolean del(String key) {
        ArgumentValidateUtils.validateKey(key);
        Long result =  jedis.del(key);

        return result == 1;
    }

    @Override
    public String type(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.type(key);
    }

    @Override
    public Long persist(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.persist(key);
    }

    @Override
    public Long pexpire(String key, long millisecond) {
        ArgumentValidateUtils.validateKeyAndMillSecond(key, millisecond);
        return jedis.pexpire(key, millisecond);
    }

    @Override
    public Long pexpireAt(String key, long time) {
        ArgumentValidateUtils.validateKeyAndTime(key, time);
        return jedis.pexpireAt(key, time);
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }
}
