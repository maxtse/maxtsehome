package com.max.tse.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * User: liuzz Date: 12-8-7 Time: 下午6:09
 * 由于目前大部分的bean没有在spring中管理,导致无法注入，所以先使用一个wrapper适配一下
 */
public class SpringWrapper implements ApplicationContextAware {

    private static volatile ApplicationContext context;

    private static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        setContext(applicationContext);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
}
