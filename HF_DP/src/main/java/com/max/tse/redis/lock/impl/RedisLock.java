package com.max.tse.redis.lock.impl;


import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import com.max.tse.redis.lock.IRedisLock;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;

import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-1-10
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 * Note:redis实现分布式锁
 */
public class RedisLock implements IRedisLock{

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private JedisCommands jedisCommands;

    public RedisLock (JedisCommands jedisCommands) {
        Preconditions.checkNotNull(jedisCommands);
        this.jedisCommands = jedisCommands;
    }


    @Override
    public boolean tryLock(String prefix, String lockKey, int time, TimeUnit timeUnit){
        Preconditions.checkArgument(StringUtils.isNotBlank(prefix));
        Preconditions.checkArgument(StringUtils.isNotBlank(lockKey));
        Preconditions.checkArgument(time >= 0);
        Preconditions.checkNotNull(timeUnit);

        String key = generateKeyInRedis(prefix, lockKey);
        boolean setSuccess = false;
        try {
            Long setRet = jedisCommands.setnx(key, "1");
            if (setRet == null || setRet == 0) {//已经有了
                return false;
            }
            setSuccess = true;//值设置了
            Long expireRet = jedisCommands.expire(key, time);
            if (expireRet != null && expireRet > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.error("tryLock error", e);
            if (setSuccess) {
                Long delKey = jedisCommands.del(key);
                logger.info("key={}, delKeyResult={}", key, delKey);
            }
            if (e instanceof RuntimeException) {
                throw e;
            }
            throw new RuntimeException("tryLock exception", e);

        }
        return false;
    }

    @Override
    public boolean unLock(String prefix, String lockKey){
        Preconditions.checkArgument(StringUtils.isNotBlank(prefix));
        Preconditions.checkArgument(StringUtils.isNotBlank(lockKey));
        String key = generateKeyInRedis(prefix, lockKey);
        try {
            Long unLockRet = jedisCommands.del(key);
            logger.info("prefix={}, lockKey={}, unLockRet={}", prefix, lockKey, unLockRet);
            return unLockRet != null && unLockRet > 0;
        } catch (Exception e) {
            logger.error("unLock error", e);
            throw new RuntimeException(e);
        }

    }
    /**
     * 构造redis里存储的key
     * 长度超过100 则hash
     * 这么做的好处是：redis性能考虑 key不要过长
     * */
    private String generateKeyInRedis(String prefix, String lockKey) {
        prefix = StringUtils.isBlank(prefix) ? "null" : prefix;
        String key = prefix + lockKey;
        if (StringUtils.length(key) > 100) {
            return Hashing.md5().newHasher().putString(key, Charsets.UTF_8).hash().toString();
        }
        return key;
    }
}
