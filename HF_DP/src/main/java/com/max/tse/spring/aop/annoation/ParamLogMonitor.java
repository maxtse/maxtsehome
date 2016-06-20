package com.max.tse.spring.aop.annoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-14
 * Time: 下午2:34
 * To change this template use File | Settings | File Templates.
 * Note:方法级别 参数日志
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamLogMonitor {

    public String value() default "param";
}
