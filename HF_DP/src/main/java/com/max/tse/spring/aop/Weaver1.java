package com.max.tse.spring.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-15
 * Time: 上午9:09
 * To change this template use File | Settings | File Templates.
 */
public class Weaver1 {

    private static final Logger logger = LoggerFactory.getLogger(Weaver1.class);

    public void afterReturning(JoinPoint point) {
        MethodSignature signature = (MethodSignature) point.getSignature();
        logger.info("joinPoint={}", point);
        logger.info("target Class = {}", point.getTarget().getClass());
        logger.info("getThis={}", point.getThis());
        Method method = AopUtils.getMostSpecificMethod(signature.getMethod(), point.getTarget().getClass());
        logger.info("method = {}", JSON.toJSONString(method));

    }
}
