package com.max.tse.redis.lock.impl;

import com.max.tse.redis.lock.RedisKeyLengthBeyondHandler;
import com.max.tse.redis.lock.RedisKeyGenerator;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午5:40
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractRedisKeyGenerator implements RedisKeyGenerator {

    @Override
    public String generator(String key) {
        return key;
    }

    @Override
    public String generator(String key, int lengthLimit, RedisKeyLengthBeyondHandler handler) {
        if (key.length() > lengthLimit) {
            return handler.handler(key);
        }
        return generator(key);
    }

    @Override
    public String generator(String prefix, String key) {
        return generator(append(prefix, key));
    }

    @Override
    public String generator(String prefix, String key, int lengthLimit, RedisKeyLengthBeyondHandler handler) {
        return generator(append(prefix, key), lengthLimit, handler);
    }

    public abstract String append(String prefix, String key);
}
