package com.max.tse.redis.client;

import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

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
    Jedis jedis;

    public void set(String key, String value) {
        Preconditions.checkNotNull(key);
        jedis.set(key, value);
    }

    public String get(String key) {
        Preconditions.checkNotNull(key);
        return jedis.get(key);
    }

    public String del(String key) {
        long result = jedis.del(key);
        return result + "";
    }


}
