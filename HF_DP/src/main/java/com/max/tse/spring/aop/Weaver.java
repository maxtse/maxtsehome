package com.max.tse.spring.aop;


import com.alibaba.fastjson.JSON;
import com.max.tse.spring.aop.annoation.ParamLogMonitor;
import com.max.tse.spring.aop.annoation.ResultLogMonitor;
import com.max.tse.spring.aop.annoation.ThrowableLogMonitor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-14
 * Time: 下午2:44
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class Weaver {

    private static final Logger logger = LoggerFactory.getLogger(Weaver.class);

    @Around("@annotation(com.max.tse.spring.aop.annoation.ParamLogMonitor)"
            + " || @annotation(com.max.tse.spring.aop.annoation.ResultLogMonitor)")
    public void weave(ProceedingJoinPoint point) {
        Signature signature = point.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return;
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = AopUtils.getMostSpecificMethod(methodSignature.getMethod(), point.getTarget().getClass());
        try {


            ParamLogMonitor paramLogMonitor = method.getAnnotation(ParamLogMonitor.class);
            if (paramLogMonitor != null) {
                logger.info(paramLogMonitor.value() + " = {}", JSON.toJSONString(point.getArgs()));
            }
            Object result = point.proceed();
            ResultLogMonitor resultLogMonitor = method.getAnnotation(ResultLogMonitor.class);
            if (resultLogMonitor != null) {
                logger.info(resultLogMonitor.value() + " = {}", JSON.toJSONString(result));
            }

        } catch (Throwable t) {
            logger.error(method.getDeclaringClass().getSimpleName() + "." + method.getName() + "error", t);
            ThrowableLogMonitor throwableLogMonitor = method.getAnnotation(ThrowableLogMonitor.class);
            if (throwableLogMonitor != null) {
                throw new RuntimeException(t);
            }
        }
    }

    @Before("execution(* com.max.tse.spring.aop.ToWeave.*Before(String,..))")
    public void testBefore(JoinPoint point) {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = AopUtils.getMostSpecificMethod(methodSignature.getMethod(), point.getTarget().getClass());
            logger.info("param={},{},{}",point.getArgs(), point.getKind(), point.getTarget());
        }

    }
}
