package com.max.tse.reflect;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.max.tse.po.Student;
import org.apache.commons.lang.ClassUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 15-11-3
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public class FieldTest {

    private static List<Field> getAllFields(Class clazz) {
        Preconditions.checkNotNull(clazz);
        List<Field> tempFieldList = Lists.newArrayList(clazz.getDeclaredFields());//获取这个类定义的属性 包括private，但是不可以获取父类的
        System.out.println(JSON.toJSONString(tempFieldList));
        return tempFieldList;
    }

    private static List<Field> getFieldsTest(Class clazz) {
        Preconditions.checkNotNull(clazz);
        List<Field> result = Lists.newArrayList(clazz.getFields());//获取所有的属性， 包括父类 但是只是获取public
        System.out.println(JSON.toJSONString(result));
        return result;

    }
    public static void main(String[] args) {
        List<Field> studentFields = getAllFields(Student.class);
        for (Field field : studentFields) {
            System.out.println("field type= " + field.getType() + " and name= " + field.getName());
        }
        getAllFields(int.class);
        System.out.println(ClassUtils.getShortClassName(Student.class));
        System.out.println(ClassUtils.getAllSuperclasses(Student.class));

        getAllFields(com.max.tse.reflect.po.Student.class);
        getFieldsTest(com.max.tse.reflect.po.Student.class);
    }
}
