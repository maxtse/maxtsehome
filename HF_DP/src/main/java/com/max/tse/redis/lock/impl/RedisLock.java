package com.max.tse.redis.lock.impl;


import com.google.common.base.Preconditions;
import com.max.tse.redis.lock.IRedisLock;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RedisLock extends AbstractRedisKeyGenerator implements IRedisLock{

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

        String key = generator(prefix, lockKey, 100, new MD5RedisKeyLengthBeyondHandler());
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
        String key = generator(prefix, lockKey, 100, new MD5RedisKeyLengthBeyondHandler());
        try {
            Long unLockRet = jedisCommands.del(key);
            logger.info("prefix={}, lockKey={}, unLockRet={}", prefix, lockKey, unLockRet);
            return unLockRet != null && unLockRet > 0;
        } catch (Exception e) {
            logger.error("unLock error", e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public String append(String prefix, String key) {
        prefix = StringUtils.isBlank(prefix) ? "null" : prefix;
        return prefix + key;
    }

}
