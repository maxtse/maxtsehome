package com.max.tse.reflect;

import com.alibaba.fastjson.JSON;
import com.max.tse.reflect.po.Student;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-11
 * Time: 上午10:53
 * To change this template use File | Settings | File Templates.
 */
public class ReflectUtil {

    private ReflectUtil () {}

    public static void makeAccess(Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }

    }

    public static Field getDeclaredField (Object object, String fieldName) {
        for (Class<?> supperClass = object.getClass(); supperClass != Object.class; supperClass = supperClass.getSuperclass()) {
            try {
                return supperClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                //Field不在当前类定义，继续向上转型
            }
        }
        return null;
    }

    public static Object getDeclaredFieldValue(Object object, String fieldName) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("当前字段不存在");
        }

        makeAccess(field);

        try {
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void setDeclaredFieldValue(Object object, String fieldName, Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("字段不存在");
        }

        makeAccess(field);

        try {
            field.set(object, value);

        } catch (IllegalAccessException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) throws Exception{

        Field field = getDeclaredField(new Student(), "username");
        makeAccess(field);
        Student student = new Student();
        student.setUsername("fd");
        student.setStuNo("233");
        student.setAge(1);
        field.set(student, "343");
        System.out.print(field.get(student));
        System.out.println(field.getClass());
        System.out.println(field.getDeclaringClass());
    }
}
