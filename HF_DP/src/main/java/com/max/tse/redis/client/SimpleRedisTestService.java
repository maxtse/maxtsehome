package com.max.tse.redis.client;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.max.tse.po.Result;
import com.max.tse.redis.api.RedisBaseService;
import com.max.tse.redis.api.RedisNumberService;
import com.max.tse.redis.api.RedisStringService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-22
 * Time: 下午1:27
 * To change this template use File | Settings | File Templates.
 * Note:redis java simple 测试
 */
@Service
public class SimpleRedisTestService {

    @Resource
    RedisNumberService redisNumberService;

    @Resource
    RedisStringService redisStringService;

    @Resource
    RedisBaseService redisBaseService;

    public void set(String key, String value) {
        redisStringService.setKey(key, value);
    }

    public String get(String key) {
        return redisStringService.get(key);
    }

    public boolean del(String key) {
        return redisBaseService.del(key);
    }

    public Result testString() throws Exception{
        Result result = Result.emptyResult();
        result.addData("redisTest", "testString");
        //set key
        boolean setKeyResult = redisStringService.setKey("stringTest", "stringTest");
        result.addData("setKeyResult", setKeyResult);

        //getKey
        String getKeyResult = redisStringService.get("stringTest");
        result.addData("getKeyResult", getKeyResult);

        //getSet
        String getSetResult = redisStringService.getSet("stringTest", "stringTest1");
        result.addData("getSetResult", getSetResult);
        result.addData("getKeyResultNew", redisStringService.get("stringTest"));

        //setnx
        boolean setnxResult = redisStringService.setnx("stringTest", "stringTest");
        result.addData("setnxResult ex", setnxResult);
        boolean setnxResultN = redisStringService.setnx("stringTestNx", "stringTest");
        result.addData("setnxResult nx", setnxResultN);

        //setEx
        boolean setExResult = redisStringService.setEx("stringTestEx", "stringTest", 5);
        result.addData("setExResult", setExResult);
        result.addData("stringTestEx first get", redisStringService.get("stringTestEx"));
        long ttlResult = redisBaseService.ttl("stringTestEx");
        result.addData("ttlResult", ttlResult);
        Thread.sleep(ttlResult * 1000);
        result.addData("stringTestEx second get", redisStringService.get("stringTestEx"));

        //setEx high
        result.addData("setExResultHigh", redisStringService.setEx("stringExHigh", "stringTest", 5));
        result.addData("getExResultHigh", redisStringService.get("stringExHigh"));
        result.addData("ttlResultFirst", redisBaseService.ttl("stringExHigh"));
        redisStringService.setEx("stringExHigh", "stringTest", 10);
        result.addData("getExResultHighSecond", redisStringService.get("stringExHigh"));
        result.addData("ttlResultSecond", redisBaseService.ttl("stringExHigh"));

        //strlen
        result.addData("strlenResult", redisStringService.strlen("stringTest"));

        //getRange
        result.addData("getrangeResult", redisStringService.getRange("stringTest", 0, 2));
        result.addData("getrangeResult1", redisStringService.getRange("stringTest", 0, -1));

        //mget
        result.addData("mgetResult", redisStringService.mget("stringTestEx", "stringTest"));

        //mset
        Map<String, String> testKeyValueMap = Maps.newHashMap();
        testKeyValueMap.put("msetKey1", "msetValue1");
        testKeyValueMap.put("msetKey2", "msetValue2");
        result.addData("msetValue", redisStringService.mset(testKeyValueMap));
        result.addData("msetValueget", redisStringService.mget(testKeyValueMap.keySet().toArray(new String[]{})));
        return result;
    }

    public void testHash() {

    }




}
