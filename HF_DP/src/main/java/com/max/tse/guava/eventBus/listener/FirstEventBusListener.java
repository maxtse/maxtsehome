package com.max.tse.guava.eventBus.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.Subscribe;
import com.max.tse.guava.eventBus.EventBusFactory;
import com.max.tse.guava.eventBus.EventBusRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-12-4
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FirstEventBusListener {

    private static final Logger logger = LoggerFactory.getLogger(FirstEventBusListener.class);

    @Subscribe
    public void listener(EventBusRequest eventBusRequest) {
        logger.info("firstEventBus listener working");
        logger.info("firstEventBus param={}", JSON.toJSONString(eventBusRequest));

    }



    @PostConstruct
    public void init() {
        EventBusFactory.firstEventBus.register(this);
    }
}
