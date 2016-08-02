package com.max.tse.spring.reflect;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-29
 * Time: 下午4:01
 * To change this template use File | Settings | File Templates.
 */
public class ReflectTest {
    public static Car initByDefaultConst() throws Throwable {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class clazz = classLoader.loadClass("com.max.tse.spring.reflect.Car");//全限定名字来加载类

        Constructor constructor = clazz.getConstructor((Class[])null);
        Car car = (Car) constructor.newInstance();

        Method setBrand = clazz.getMethod("setBrand", String.class);
        setBrand.invoke(car, "红旗CA72");

        Method setColor = clazz.getMethod("setColor", String.class);
        setColor.invoke(car, "red");

        Method setSpeed = clazz.getMethod("setMaxSpeed", int.class);
        setSpeed.invoke(car, 12);


        return car;
    }

    public static void testMethod() throws Throwable{
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class clazz = classLoader.loadClass("com.max.tse.spring.reflect.Car");

        Method setBrand = clazz.getMethod("setBrand", String.class);
        System.out.println(JSONObject.toJSONString(setBrand.getParameterTypes()));
        System.out.println(JSONObject.toJSONString(setBrand.getTypeParameters()));
        System.out.println(setBrand.getReturnType());

    }

    public static void main(String[] args) throws Throwable{
        Car car = initByDefaultConst();
        car.introduce();
        testMethod();

    }
}
