package com.max.tse.jvm;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: yuebin.xie
 * Date: 16-4-27
 * Time: 上午9:59
 * To change this template use File | Settings | File Templates.
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws Exception{
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
               try {
                   String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                   InputStream is = getClass().getResourceAsStream(fileName);
                   if (is == null) {
                       return super.loadClass(name);
                   }

                   byte[] b = new byte[is.available()];
                   is.read(b);
                   return defineClass(name, b, 0, b.length);

               } catch (Exception e) {
                   throw new ClassNotFoundException(name);
               }
            }
        };

        Object obj = myLoader.loadClass("com.max.tse.jvm.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());//class com.max.tse.jvm.ClassLoaderTest
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(ClassLoaderTest.class.getClassLoader());
        System.out.println(obj instanceof ClassLoaderTest);
        System.out.println(Object.class.getClassLoader());//null native方法


    }
}
