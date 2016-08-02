package com.max.tse.redis.lock.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.max.tse.redis.lock.IRedisLock;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-17
 * Time: 下午4:42
 * To change this template use File | Settings | File Templates.
 */
public class RedisLockImpl extends AbstractRedisKeyGenerator implements IRedisLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockImpl.class);

    private static final String LOCK_KEY = "LOCK";

    private static final int MAX_KEY_LENGTH = 100;

    private static final Joiner JOINER = Joiner.on(".").skipNulls();

    private Jedis jedis;

    public RedisLockImpl (Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public boolean tryLock(String prefix, String lockKey, int time, TimeUnit timeUnit) {
        LOGGER.info("tryLock prefix={}, lockKey={} time={} timeUnit={}", prefix, lockKey, time, timeUnit);
        Preconditions.checkNotNull(prefix);
        Preconditions.checkArgument(StringUtils.isNotBlank(lockKey));
        Preconditions.checkArgument(time > 0);
        Preconditions.checkNotNull(timeUnit);

        String storeKey = generator(prefix, lockKey, MAX_KEY_LENGTH, new MD5RedisKeyLengthBeyondHandler());
        boolean hasSet = false;
        try {
            Long setResult = jedis.setnx(storeKey, "1");
            LOGGER.info("tryLock setnx prefix={}, lockKey={}, result = {}", prefix, lockKey, setResult);
            if (setResult == null || setResult == 0) {
                return false;
            }
            hasSet = true;

            for (int i =0; i < 3; i++) {
                Long expireResult = jedis.expire(storeKey, (int) timeUnit.toSeconds(time));
                LOGGER.info("tryLock expireResult prefix={}, lockKey={}, result = {}", prefix, lockKey, expireResult);
                if (expireResult != null && expireResult > 0) {
                   return true;
                }
                Thread.sleep(new Random().nextInt(15));
            }
            return false;

        } catch (Exception e) {
            LOGGER.error("tryLock error", e);
            if (hasSet) {
                Long delResult = jedis.del(storeKey);
                LOGGER.info("tryLock del prefix={}, lockKey={}, result = {}", prefix, lockKey, delResult);
            }
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean unLock(String prefix, String lockKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(prefix));
        Preconditions.checkArgument(StringUtils.isNotBlank(lockKey));

        String storeKey = generator(prefix, lockKey, MAX_KEY_LENGTH, new MD5RedisKeyLengthBeyondHandler());
        try {
            Long delResult = jedis.del(storeKey);
            LOGGER.info("unLock del prefix={}, lockKey={}, result = {}", prefix, lockKey, delResult);
            return delResult != null && delResult == 1;
        } catch (Exception e) {
            LOGGER.error("unLock error", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String append(String prefix, String key) {
        prefix = StringUtils.isBlank(prefix) ? "null" : prefix;
        String storeKeyString = JOINER.join(prefix, key, LOCK_KEY);
        return storeKeyString;
    }
}
