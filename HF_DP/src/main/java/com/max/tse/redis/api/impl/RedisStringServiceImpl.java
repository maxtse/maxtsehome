package com.max.tse.redis.api.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.max.tse.redis.utils.ArgumentValidateUtils;
import com.max.tse.redis.utils.ResultUtils;
import com.max.tse.redis.api.RedisStringService;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-16
 * Time: 下午2:27
 * To change this template use File | Settings | File Templates.
 */
public class RedisStringServiceImpl implements RedisStringService {

    private Jedis jedis;

    public RedisStringServiceImpl(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public boolean setKey(String key, String value) {
        ArgumentValidateUtils.validateKeyAndValue(key, value);

        String result = jedis.set(key, value);
        return ResultUtils.analyseResult(result);
    }

    @Override
    public boolean setEx(String key, String value, int time) {
        ArgumentValidateUtils.validateKeyValueAndSecond(key, value, time);
        String result = jedis.setex(key, time, value);
        return ResultUtils.analyseResult(result);

    }

    public String getSet(String key, String value) {
        ArgumentValidateUtils.validateKeyAndValue(key, value);
        return jedis.getSet(key, value);
    }

    @Override
    public String get(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.get(key);
    }

    @Override
    public boolean setnx(String key, String value) {
        ArgumentValidateUtils.validateKeyAndValue(key, value);
        Long result = jedis.setnx(key, value);
        return result == 1;
    }

    @Override
    public boolean append(String key, String value) {
        ArgumentValidateUtils.validateKeyAndValue(key, value);
        Long result = jedis.append(key, value);
        return result == 1;
    }

    @Override
    public long strlen(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.strlen(key);
    }

    @Override
    public String getRange(String key, long startOffset, long endOffset) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.getrange(key, startOffset, endOffset);
    }

    @Override
    public boolean mset(Map<String, String> keyValueMap) {
        Preconditions.checkArgument(keyValueMap != null && !keyValueMap.isEmpty());

        String[] keyValues = mapToList(keyValueMap);
        String result = jedis.mset(keyValues);
        return ResultUtils.analyseResult(result);
    }

    @Override
    public Map<String, String> mget(String... keys) {
        Preconditions.checkArgument(keys != null && keys.length > 0);

        List<String> result = jedis.mget(keys);
        return transformMgetValue(result, keys);
    }

    public boolean getBit(String key, int offset) {
        ArgumentValidateUtils.validateKey(key);
        Preconditions.checkArgument(offset >= 0);
        return jedis.getbit(key, offset);
    }

    public boolean setbit(String key, long offset, boolean value) {
        ArgumentValidateUtils.validateKey(key);
        Preconditions.checkArgument(offset >= 0);
        return jedis.setbit(key, offset, value);
    }

    public long bitCount(String key) {
        ArgumentValidateUtils.validateKey(key);
        return jedis.bitcount(key);
    }

    public long bitCount(String key, long startOffset, long endOffset) {
        ArgumentValidateUtils.validateKey(key);

        return jedis.bitcount(key, startOffset, endOffset);
    }


    private String[] mapToList(Map<String, String> keyValueMap) {
        List<String> result = Lists.newArrayList();

        for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            result.add(entry.getKey());
            result.add(entry.getValue());
        }
        return result.toArray(new String[]{});
    }

    private Map<String, String> transformMgetValue(List<String> jedisResult, String... keys) {
        Map<String, String> result = Maps.newHashMap();
        for (int i = 0; i < keys.length; i++) {
            result.put(keys[i], jedisResult.get(i));
        }
        return result;
    }



    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    public static void main(String[] args) {
        Jedis jedis1 = new Jedis("127.0.0.1", 6379);
        jedis1.auth("maxtse2133");
        RedisStringService redisStringService = new RedisStringServiceImpl(jedis1);
        System.out.println(redisStringService.getSet("stringTest", "mytest"));
    }
}
