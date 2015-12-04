package com.max.tse.reflect;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.max.tse.po.Student;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-3
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class MethodTest {

    public static List<Method> getAllMethod(Class clazz) {
        Preconditions.checkNotNull(clazz);
        System.out.println("class.isPrimitive()=" + clazz.isPrimitive());//判断是不是基本类型 primitive：原始的
        System.out.println("class.getName()= " + clazz.getName());//包路径的类名
        System.out.println("class.getSimpleName()= " + clazz.getSimpleName());//单纯类名
        List<Method> result = Lists.newArrayList();

        if (clazz.getName().contains("java.util") || clazz.getName().contains("java.lang")) {
            return result;
        }
        System.out.println(JSON.toJSONString(clazz.getDeclaredMethods()));
        System.out.println(JSON.toJSONString(clazz.getMethods()));
        return result;
    }

    public static void main(String[] args) {
        getAllMethod(Student.class);
        getAllMethod(int.class);
    }
}
