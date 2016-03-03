package com.max.tse.guava.eventBus;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-12-4
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public class EventBusFactory {

    private static final Logger logger = LoggerFactory.getLogger(EventBusFactory.class);

    public static final EventBus firstEventBus = new EventBus(new EventBusExceptionHandler("firstEventBus"));

    public static class EventBusExceptionHandler implements SubscriberExceptionHandler {

        EventBusExceptionHandler(String eventBusName) {
            this.eventBusName = eventBusName;
        }

        private String eventBusName;

        public String getEventBusName() {
            return eventBusName;
        }

        public void setEventBusName(String eventBusName) {
            this.eventBusName = eventBusName;
        }

        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            logger.error("eventBusName" + eventBusName + "context=" + JSON.toJSONString(context) + " handleException ", exception);
        }
    }
}
