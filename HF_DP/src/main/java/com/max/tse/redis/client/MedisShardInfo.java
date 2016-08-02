package com.max.tse.redis.client;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisShardInfo;

import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-2-22
 * Time: 下午12:58
 * To change this template use File | Settings | File Templates.
 * Note:添加带密码的构造方法
 */
public class MedisShardInfo extends JedisShardInfo{

    public MedisShardInfo(String host) {
        super(host);
    }

    public MedisShardInfo(String host, String name) {
        super(host, name);
    }

    public MedisShardInfo(String host, int port) {
        super(host, port);
    }

    public MedisShardInfo(String host, int port, String name) {
        super(host, port, name);
    }

    public MedisShardInfo(String host, int port, int timeout) {
        super(host, port, timeout);
    }

    public MedisShardInfo(String host, int port, int timeout, String name) {
        super(host, port, timeout, name);
    }

    public MedisShardInfo(String host, int port, int timeout, int weight) {
        super(host, port, timeout, weight);
    }

    public MedisShardInfo(URI uri) {
        super(uri);
    }
    public MedisShardInfo(String host, String password, int port) {
        super(host, port);
        this.setPassword(password);
    }

}
