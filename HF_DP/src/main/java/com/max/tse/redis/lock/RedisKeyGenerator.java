package com.max.tse.redis.lock;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午5:33
 * To change this template use File | Settings | File Templates.
 * Note:构造redis的key
 */
public interface RedisKeyGenerator {

    String generator(String key);

    String generator(String key, int lengthLimit, RedisKeyLengthBeyondHandler handler);

    String generator(String prefix, String key);

    String generator(String prefix, String key, int lengthLimit, RedisKeyLengthBeyondHandler handler);
}
