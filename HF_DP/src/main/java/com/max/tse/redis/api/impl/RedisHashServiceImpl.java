package com.max.tse.redis.api.impl;

import com.google.common.base.Preconditions;
import com.max.tse.redis.utils.ArgumentValidateUtils;
import com.max.tse.redis.utils.ResultUtils;
import com.max.tse.redis.api.RedisHashService;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午6:17
 * To change this template use File | Settings | File Templates.
 */
public class RedisHashServiceImpl implements RedisHashService {

    private Jedis jedis;

    public RedisHashServiceImpl(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public boolean hset(String key, String field, String value) {
        ArgumentValidateUtils.validateKeyFieldValue(key, field, value);
        return jedis.hset(key, field, value) == 1;

    }

    @Override
    public String hget(String key, String field) {
        ArgumentValidateUtils.validateKeyField(key, field);
        return jedis.hget(key, field);
    }

    public Map<String, String> hgetAll(String key) {
        return jedis.hgetAll(key);
    }

    public Set<String> hkeys(String key) {
        return jedis.hkeys(key);
    }

    public long hlen(String key) {
        return jedis.hlen(key);
    }

    public List<String> hvalues(String key) {
        return jedis.hvals(key);
    }


    public boolean hdel(String key, String... fields) {
        return jedis.hdel(key, fields) > 0;

    }

    public boolean hexists(String key, String field) {
        return jedis.hexists(key, field);
    }

    public boolean hsetnx(String key, String field, String value) {
        return jedis.hsetnx(key, field, value) == 1;
    }

    public boolean hmset(String key, Map<String, String> keyValue) {
        String hmsetResult =  jedis.hmset(key, keyValue);
        return ResultUtils.analyseResult(hmsetResult);

    }

    public List<String> hmget(String key, List<String> fields) {
        return jedis.hmget(key, fields.toArray(new String[] {}));
    }

    public long hincrBy(String key, String field, long value) {
        return jedis.hincrBy(key, field, value);
    }


}
