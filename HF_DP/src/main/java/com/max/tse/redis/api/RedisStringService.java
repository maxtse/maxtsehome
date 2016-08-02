package com.max.tse.redis.api;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-5-16
 * Time: 上午11:58
 * To change this template use File | Settings | File Templates.
 * Note:字符串类型操作 值的最大范围为512MB
 *
 */
public interface RedisStringService {

    /**
     * 添加一个key 成功返回ok
     * set stringTest stringTest
     * @param key
     * @param value
     * @return
     * */
    public boolean setKey(String key, String value);

    /**
     * 添加一个key 带失效时间
     * setex stringexTest 50 stringTest
     * @param key
     * @param value
     * @param time
     * @return
     * */
    public boolean setEx(String key, String value, int time);

    /**
     * 更新并返回原来的值
     * 如果不存在，返回null
     * getset stringTest 1 :stringTest
     * @param key
     * @param value
     * @return
     * */
    public String getSet(String key, String value);

    /**
     * 返回key的值
     * get stringTest
     * @param key
     * @return
     * */
    public String get(String key);

    /**
     * 如果不存在key 则设置否则返回0
     * setnx stringTest 1 :0
     * setnx stringnxTest stringTest:1
     * @param key
     * @param value
     * @return
     * */
    public boolean setnx(String key, String value);

    /**
     * 把原值和value拼接
     * append stringTest 1:stringTest1
     * append appendTest stringTest:stringTest
     * @param key
     * @param value
     * @return
     * */
    public boolean append(String key, String value);

    /**
     * 获取value的长度
     * strlen stringTest :10
     * @param key
     * @return
     * */
    public long strlen(String key);

    /**
     *获取区间内的字符串
     * 为负则从末尾开始
     * getrange stringTest 0 10
     * getrange stringTest 0 -1
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     * */
    public String getRange(String key, long startOffset, long endOffset);

    /**
     * set多个值
     * mset stringTest stringTestmset stringTest1 stringTest
     * @param keyValueMap
     * @return
     * */
    public boolean mset(Map<String, String> keyValueMap);

    /**
     * 获取多个值
     * mget stringTest stringTest1
     * @param keys
     * @return
     * */
    public Map<String, String> mget(String... keys);

    /**
     * 获取值当前偏移处的二进制是1还是0 1返回true 0返回false
     * @param key
     * @param offset
     * @return
     * */
    public boolean getBit(String key, int offset);
    /**
     * 值偏移除设置为1或者0
     * @param key
     * @param offset
     * @param value
     * @return
     * */
    public boolean setbit(String key, long offset, boolean value);

    /**
     * 1的个数
     * @param key
     * @return
     * */
    public long bitCount(String key);

    /**
     * 区间内1的个数
     * @param key
     * @param startOffset
     * @param endOffset
     * @return
     * */
    public long bitCount(String key, long startOffset, long endOffset);


}
