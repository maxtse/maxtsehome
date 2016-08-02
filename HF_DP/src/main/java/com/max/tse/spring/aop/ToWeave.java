package com.max.tse.spring.aop;

import com.max.tse.spring.aop.annoation.ParamLogMonitor;
import com.max.tse.spring.aop.annoation.ResultLogMonitor;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-6-14
 * Time: 下午2:43
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ToWeave {

    @ParamLogMonitor("testAround")
    @ResultLogMonitor("testAround")
    public Object testAround(String name, String password) {
        return name + " _ " + password;

    }

    public Object testBefore(String name, String password) {
        return name + " _ before " + password;
    }

    public Object testAfterReturning(String name, String password) {
        return name + " _ afterReturning" + password;
    }

    public Object testAfterReturning1(String name, int age) {
        return name + " _ afterReturning" + age;
    }
}
